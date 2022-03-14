val ktor_version: String by rootProject
val kotlin_version: String by rootProject
val logback_version: String by rootProject

plugins {
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("io.ktor:ktor-serialization:$ktor_version")
}

tasks{
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "io.ktor.server.netty.EngineMain"))
        }
    }
}