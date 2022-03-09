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
    implementation("com.googlecode.lanterna:lanterna:3.1.1")
    //implementation("io.qameta.allure:allure-cucumber4-jvm:2.17.2")
    testImplementation(kotlin("test"))
    testImplementation("org.amshove.kluent:kluent:1.68")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.junit.platform:junit-platform-suite:1.8.2")
    testImplementation ("io.cucumber:cucumber-java8:7.2.3")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.2.3")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.1.0")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.17")
    testImplementation("org.spekframework.spek2:spek-runner-junit5:2.0.17")
}

tasks.test {
    useJUnitPlatform()
//    {
//        includeEngines = setOf("spek2")
//    }
    //finalizedBy("jacocoTestReport")
    //finalizedBy("allureServe")
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