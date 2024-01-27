package com.ds.movieapp.data.repo.watchListFavoritesRepo

import com.ds.movieapp.domain.repo.MovieFavourite
import com.ds.movieapp.domain.repo.WatchListFavoritesRepo
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class WatchListFavoritesRepoImpl @Inject constructor(
    private val db: FirebaseFirestore
) : WatchListFavoritesRepo {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun addToFavorites(movieId: String) {
        db.collection("favourites")
            .add(MovieFavourite(movieId))
    }

    override fun removeFromFavorites(movieId: String) {
        db.collection("favourites")
            .whereEqualTo("movieId", movieId)
            .addSnapshotListener { result, _ ->
                result?.documents?.forEach { doc ->
                    doc?.reference?.delete()
                }
            }
    }

    override fun observeFavorites(): Flow<List<MovieFavourite>?> {
        return callbackFlow {
            val eventListener = EventListener<QuerySnapshot> { value, _ ->

                val data = value?.documents?.mapNotNull {
                    it?.data?.get("movieId").let { movieId ->
                        MovieFavourite(movieId as String)
                    }
                }
                trySend(data)
            }
            val ref = db.collection("favourites")
                .addSnapshotListener(
                    eventListener
                )

            awaitClose {
                ref.remove()
            }
        }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), emptyList())
    }
}
