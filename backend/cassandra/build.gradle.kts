val datastax_version: String by rootProject

plugins {
    kotlin("kapt")
    id("java-test-fixtures")
}

dependencies {
    implementation("com.datastax.oss:java-driver-mapper-runtime:$datastax_version")
    kapt("com.datastax.oss:java-driver-mapper-processor:$datastax_version")
    testFixturesImplementation("com.datastax.oss:java-driver-core:$datastax_version")
}