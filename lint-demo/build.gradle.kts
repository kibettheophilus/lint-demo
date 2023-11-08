@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)

    id("maven-publish")
    id("signing")
}

android {
    namespace = "com.theophiluskibet.lintdemo"
    compileSdk = 33

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":lint-rules"))
    lintPublish(project(":lint-rules"))
}
val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")

publishing {
    repositories {
        maven {
            name = "Sonatype"
            url = if (version.toString().endsWith("SNAPSHOT")) {
                snapshotsRepoUrl
            } else {
                releasesRepoUrl
            }

            credentials {
                username = "kibettheo"
                password = "e4.GASwNdM6X_3F"
            }
        }

        val javadocJar = tasks.register<Jar>("javadocJar") {
            archiveClassifier.set("javadocJar")
        }
        publications.withType<MavenPublication> {
            artifact(javadocJar)

            pom {
                groupId = "io.github.kibettheophilus"
                version = "0.0.1"

                name.set("Lint Demo")
                description.set("Test")
                url.set("")

                licenses {
                    license {
                        name.set("MIT")
                        url.set("")
                    }
                }

                developers {
                    developer {
                        id.set("kibettheo")
                        name.set("Theo")
                        email.set("kibettheophilus@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:github.com/kibettheophilus/lint-demo.git")
                    developerConnection.set("scm:git:ssh://github.com/kibettheophilus/lint-demo.git")
                    url.set("https://github.com/kibettheophilus/lint-demo")
                }
            }

            signing {
                useInMemoryPgpKeys(
                    "DD466385",
                    "kimosh@98",
                )
                sign(publishing.publications)
            }
        }
    }
}
