val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
val assertj_version: String by project
val mockk_version: String by project

// TODO: https://github.com/mobiletoly/ktor-hexagonal-multimodule
// TODO: https://github.com/mathias21/KtorEasy
// TODO: https://medium.com/@math21/testing-a-ktor-server-part-i-route-testing-84f8e82454d7
// TODO: https://mockk.io/
plugins {
    base
    kotlin("jvm") version "1.6.10"
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
        testImplementation("io.mockk:mockk:$mockk_version")
    }
}

