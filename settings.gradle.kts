pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven {
            url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        }
        mavenCentral()
        gradlePluginPortal()
        maven { setUrl("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven {
            url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        }
        maven { setUrl("https://oss.sonatype.org/content/repositories/snapshots/") }
        maven { setUrl("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev") }
        maven { setUrl ("https://jitpack.io") }
    }
}

rootProject.name = "MaterialCCPExample"
include(":app")
include(":xmaterialccp")
