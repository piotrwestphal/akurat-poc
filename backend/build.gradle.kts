val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
val assertj_version: String by project
// TODO: https://github.com/mobiletoly/ktor-hexagonal-multimodule
// TODO: https://medium.com/@math21/testing-a-ktor-server-part-i-route-testing-84f8e82454d7
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
        testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
        testImplementation("org.assertj:assertj-core:$assertj_version")
        testImplementation("io.ktor:ktor-serialization:$ktor_version")
    }
}

