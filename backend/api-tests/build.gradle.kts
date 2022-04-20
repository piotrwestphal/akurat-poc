val ktor_version: String by rootProject
val logback_version: String by rootProject

plugins {
    kotlin("plugin.serialization") version "1.6.20"
}

dependencies {
    testImplementation(project(":domain"))
    testImplementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-client-core:$ktor_version")
    testImplementation("io.ktor:ktor-client-cio:$ktor_version")
    testImplementation("io.ktor:ktor-client-logging:$ktor_version")
    testImplementation("io.ktor:ktor-client-serialization:$ktor_version")
    testImplementation(testFixtures(project(":cassandra")))
}

sourceSets {
    create("integrationTest") {
        kotlin {
            compileClasspath += main.get().output + configurations.testRuntimeClasspath
            runtimeClasspath += output + compileClasspath
        }
    }
}

task<Test>("integrationTest") {
    description = "Runs the integration tests"
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    testLogging {
        outputs.upToDateWhen {false}
        showStandardStreams = true
    }
}