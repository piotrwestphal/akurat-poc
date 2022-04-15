val datastax_version: String by rootProject

plugins {
    id("java-test-fixtures")
}

dependencies {
    implementation("com.datastax.oss:java-driver-core:$datastax_version")
    testFixturesImplementation("com.datastax.oss:java-driver-mapper-runtime:$datastax_version")
}