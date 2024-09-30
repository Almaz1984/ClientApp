plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.clientapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.clientapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    flavorDimensions.add( "client")

    productFlavors {
        create("OperatorA") {
            resValue("string", "clientName", "OperatorA")
            resValue("color", "clientColor", "#FF5733")
        }
        create("OperatorB") {
            resValue("string", "clientName", "OperatorB")
            resValue("color", "clientColor", "#33FF57")
        }
        create("OperatorC") {
            resValue("string", "clientName", "OperatorC")
            resValue("color", "clientColor", "#3357FF")
        }
        create("OperatorD") {
            resValue("string", "clientName", "OperatorD")
            resValue("color", "clientColor", "#FF33A1")
        }
        create("OperatorE") {
            resValue("string", "clientName", "OperatorE")
            resValue("color", "clientColor", "#A133FF")
        }
        create("OperatorF") {
            resValue("string", "clientName", "OperatorF")
            resValue("color", "clientColor", "#FF8C33")
        }
        create("OperatorG") {
            resValue("string", "clientName", "OperatorG")
            resValue("color", "clientColor", "#33FF8C")
        }
        create("OperatorH") {
            resValue("string", "clientName", "OperatorH")
            resValue("color", "clientColor", "#8C33FF")
        }
        create("OperatorI") {
            resValue("string", "clientName", "OperatorI")
            resValue("color", "clientColor", "#FF3333")
        }
        create("OperatorJ") {
            resValue("string", "clientName", "OperatorJ")
            resValue("color", "clientColor", "#33FFA1")
        }
    }

    buildFeatures {
        viewBinding = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}