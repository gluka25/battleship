import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    application
    jacoco
    id("io.qameta.allure") version "2.9.6"
}

group = "me.gluka"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.exposed:exposed:0.17.14")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("com.googlecode.lanterna:lanterna:3.0.1")
    testImplementation(kotlin("test"))
    testImplementation("org.amshove.kluent:kluent:1.68")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.0")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
    finalizedBy("allureServe")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.getByName("jacocoTestReport") {
    dependsOn("test")
}

allure {
    adapter {
        autoconfigure.set(true)
        aspectjWeaver.set(true)
    }
}

application {
    mainClass.set("MainKt")
}