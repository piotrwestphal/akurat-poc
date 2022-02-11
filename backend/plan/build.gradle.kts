val faker_version: String by rootProject

dependencies {
    implementation(project(":domain"))
    implementation("io.github.serpro69:kotlin-faker:$faker_version")
}