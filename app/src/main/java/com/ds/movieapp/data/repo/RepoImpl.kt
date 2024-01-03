package com.ds.movieapp.data.repo

import com.ds.movieapp.data.Resource
import com.ds.movieapp.domain.Repo
import com.ds.movieapp.domain.models.Genre
import com.ds.movieapp.domain.models.Genres
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepoImpl @Inject constructor(private val client: HttpClient) : Repo {
    override suspend fun getGenres(): Flow<Resource<Genres>> {
        val genres: Genres = client.get("https://api.themoviedb.org/3/genre/movie/list?api_key=cd26ba1eaf7b89939587b825641ad1db").body()

        println("dsds" + genres)
        client.close()

        return flow {
            Resource.Success(
                Genres(
                    genres = listOf(
                        Genre(
                            id = 1,
                            name = "adventure"
                        )
                    )
                )
            )
        }
    }
}
