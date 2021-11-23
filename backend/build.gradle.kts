val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
// TODO: https://github.com/mobiletoly/ktor-hexagonal-multimodule
plugins {
    base
    kotlin("jvm") version "1.5.31"
}

allprojects {
    group = "com.akurat"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }

    apply(plugin = "kotlin")

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

subprojects {
    dependencies {
        implementation("io.insert-koin:koin-ktor:$koin_version")
        implementation ("io.insert-koin:koin-logger-slf4j:$koin_version")

        testImplementation("io.insert-koin:koin-test:$koin_version")
        testImplementation("io.insert-koin:koin-test-junit5:$koin_version")
        testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
        testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    }
}

