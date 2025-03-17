plugins {
    id("java")
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    implementation("org.jetbrains:annotations:23.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")
    compileOnly(project(":version"))
    compileOnly(project(":1.19"))
    compileOnly(project(":1.19.1"))
    compileOnly(project(":1.19.2"))
    compileOnly(project(":1.19.3"))
    compileOnly(project(":1.19.4"))
    compileOnly(project(":1.20"))
    compileOnly(project(":1.20.1"))
    compileOnly(project(":1.20.2"))
    compileOnly(project(":1.20.3"))
    compileOnly(project(":1.20.4"))
    compileOnly(project(":1.20.6"))
    compileOnly(project(":1.21"))
    compileOnly(project(":1.21.1"))
    compileOnly(project(":1.21.3"))
    compileOnly(project(":1.21.4"))
    compileOnly(files("libs/Origins-Reborn-2.5.0-all.jar"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    disableAutoTargetJvm()
}

tasks {
    compileJava {
        options.release.set(17)
    }
}

tasks.test {
    useJUnitPlatform()
}