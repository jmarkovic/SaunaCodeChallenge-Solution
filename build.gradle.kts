import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    application
}

group = "dev.jmarkovic"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.assertj:assertj-core:3.19.0")
    testImplementation(kotlin("test-testng"))
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useTestNG()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}