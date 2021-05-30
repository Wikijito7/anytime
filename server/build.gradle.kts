import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar


val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposedVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.4.32"
    java
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "es.wokis"
version = "0.0.1"

application {
    mainClassName = "es.wokis.MainKt"
//    mainClass.set("io.ktor.server.netty.EngineMain")
//    mainClass.set("es.wokis.MainKt")
}

repositories {
    mavenCentral()
    jcenter()
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("anytime-server")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "es.wokis.Main"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

dependencies {
    // ktor
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-gson:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    // exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    // jbcrypt
    implementation("org.mindrot:jbcrypt:0.4")
    // sql
    implementation("mysql:mysql-connector-java:8.0.19")
    implementation("com.zaxxer:HikariCP:3.4.2")
    // JavaMail
    implementation("javax.mail:javax.mail-api:1.6.2")
    implementation("com.sun.mail:javax.mail:1.6.2")
    // Kodein
    implementation("org.kodein.di:kodein-di:7.5.0")
}