plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14" apply false
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.starshootercity.originsfantasy"
version = "1.1.1"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    implementation("org.jetbrains:annotations:23.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation(project(":core"))
    implementation(project(":version"))
    implementation(project(":1.21.1", "reobf"))
    implementation(project(":1.21.4", "reobf"))
    implementation(project(":1.21.3", "reobf"))
    implementation(project(":1.21", "reobf"))
    implementation(project(":1.20.6", "reobf"))
    implementation(project(":1.20.4", "reobf"))
    implementation(project(":1.20.3", "reobf"))
    implementation(project(":1.20.2", "reobf"))
    implementation(project(":1.20.1", "reobf"))
    implementation(project(":1.20", "reobf"))
    implementation(project(":1.19", "reobf"))
    implementation(project(":1.19.1", "reobf"))
    implementation(project(":1.19.2", "reobf"))
    implementation(project(":1.19.3", "reobf"))
    implementation(project(":1.19.4", "reobf"))
}

tasks {
    compileJava {
        options.release.set(17)
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.test {
    useJUnitPlatform()
}