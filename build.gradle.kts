
val ktorVersion:String by project
val exposedVersion:String by project
val sqlserverJdbcVersion:String by project
val javaFakerVersion:String by project
val jmhVersionProject:String by project
val kotlinxBenchmarkVersion:String by project
val kotlinStdlibJdk8Version:String by project
val kotlinxSerializationVersion:String by project

plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.allopen") version "1.9.21"
    id("org.jetbrains.kotlinx.benchmark")  version "0.4.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
    application
}

group = "io.arcotech.bloomfilter"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-features:$ktorVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")


    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("com.microsoft.sqlserver:mssql-jdbc:$sqlserverJdbcVersion")
    implementation("com.github.javafaker:javafaker:$javaFakerVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:$kotlinxBenchmarkVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinStdlibJdk8Version")

    testImplementation(kotlin("test"))
}

benchmark {
    configurations {
        named("main") {
            iterationTime = 5
            iterationTimeUnit = "sec"
        }
    }
    targets {
        register("main") {
            this as kotlinx.benchmark.gradle.JvmBenchmarkTarget
            jmhVersion = jmhVersionProject
        }
    }
}
tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}