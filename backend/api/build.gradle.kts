val ktor_version: String by rootProject
val kotlin_version: String by rootProject
val logback_version: String by rootProject

plugins {
    kotlin("plugin.serialization") version "1.5.31"
}

dependencies {
    implementation(project(":domain"))
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
}