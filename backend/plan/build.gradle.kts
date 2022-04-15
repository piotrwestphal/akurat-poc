val lucene_spatial_version: String by rootProject

dependencies {
    implementation(project(":domain"))

    // TODO: is it needed?
    implementation("org.apache.lucene:lucene-spatial:${lucene_spatial_version}")
}