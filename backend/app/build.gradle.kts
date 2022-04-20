val logback_version: String by rootProject
val konform_version: String by rootProject
val kotlin_version: String by rootProject
val ktor_version: String by rootProject

plugins {
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
    kotlin("plugin.serialization") version "1.6.20"
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":pdf"))
    implementation(project(":plan"))
    implementation(project(":profile"))
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.konform:konform:$konform_version")

    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
}

tasks{
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "io.ktor.server.netty.EngineMain"))
        }
    }
}