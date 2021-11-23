val ktor_version: String by rootProject

plugins {
    kotlin("plugin.serialization") version "1.5.31"
}

dependencies {
    implementation("io.ktor:ktor-serialization:$ktor_version")
}