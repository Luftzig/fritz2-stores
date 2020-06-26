plugins {
    id("dev.fritz2.fritz2-gradle") version "0.5"
    kotlin("plugin.serialization") version "1.3.72"
}

repositories {
    jcenter()
}

kotlin {
    kotlin {
        jvm()
        js().browser()

        sourceSets {
            val commonMain by getting {
                dependencies {
                    implementation(kotlin("stdlib"))
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
                }
            }
            val jvmMain by getting {
                dependencies {
                }
            }
            val jsMain by getting {
                dependencies {
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:0.20.0")
                }
            }
        }
    }
}