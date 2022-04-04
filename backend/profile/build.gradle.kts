val datastax_version: String by rootProject

plugins {
    kotlin("kapt")
}

dependencies {
    implementation(project(":cassandra"))
    implementation(project(":domain"))

    kapt("com.datastax.oss:java-driver-mapper-processor:$datastax_version")
    implementation("com.datastax.oss:java-driver-mapper-runtime:$datastax_version")
}