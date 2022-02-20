plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("maven-publish")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 29
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(Deps.kotlin)
    implementation(Deps.materialDesign)
    implementation(Deps.constraintLayout)
    implementation(Deps.appCompat)
}
afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "release".
            val release by publications.registering(MavenPublication::class) {
                // Applies the component for the release build variant.
                from(components["release"])
                // You can then customize attributes of the publication as shown below.
                groupId = "com.github.huynn109"
                artifactId = "increase-decrease-button"
                version = "v0.0.1"
            }
        }
    }
}
