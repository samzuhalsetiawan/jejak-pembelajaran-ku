pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("multiplatform") version "2.2.0" apply false
}

rootProject.name = "hello-kotlin-js"