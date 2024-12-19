plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.sqldelight)
    application
}

group = "com.eniobiondic.noted"
version = "1.0.0"
application {
    mainClass.set("com.eniobiondic.noted.ApplicationKt")
    applicationDefaultJvmArgs =
        listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

sqldelight {
    // this will be the name of the generated database class
    databases.create("NotedDatabase") {
        // package name used for the database class
        packageName.set("com.eniobiondic.noted.server")

        // generate suspending query methods with asynchronous drivers
        // disabled, requires R2dbc driver
        // generateAsync.set(true)

        // directory where .db schema files should be stored, relative to the project root
        // use ./gradlew data:tasks to list all available tasks for generating schema
        // available task should be run before every migration
        schemaOutputDirectory.set(file("src/main/sqldelight/databases"))

        // migration files will fail during the build process if there are any errors in them
        verifyMigrations.set(true)

        dialect(libs.sqldelight.dialect.postgresql)
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.koin.server)
    implementation(libs.firebase.admin)
    implementation(libs.bundles.server.database)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}