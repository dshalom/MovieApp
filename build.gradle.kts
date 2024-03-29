import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.jlleitschuh.gradle:ktlint-gradle:11.5.1")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.4")
    }
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")
// to do add more dependencies here

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        buildUponDefaultConfig = true // preconfigure defaults
        allRules = false // activate all available (even unstable) rules.
        config.setFrom("$projectDir/../config/detekt/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
        baseline =
            file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt_run.yaml
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            html.required.set(true) // observe findings in your browser with structure and code snippets
        }
    }

// Kotlin DSL
    tasks.withType<Detekt>().configureEach {
        jvmTarget = "17"
    }
    tasks.withType<DetektCreateBaselineTask>().configureEach {
        jvmTarget = "17"
    }
}
