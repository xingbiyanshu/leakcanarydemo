plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.leakcanarydemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.leakcanarydemo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    /*
     * 只需要这一句，不需要添加修改任何代码，leakcanary就开始工作了。
     * 默认leakcanary会监控：
     * destroyed `Activity` instances
     * destroyed `Fragment` instances
     * destroyed fragment `View` instances
     * cleared `ViewModel` instances
     * destroyed `Service` instance
     *
     * 其它的对象可以通过AppWatcher.objectWatcher.watch手动监控。
     */
    // 使用debugImplementation模式引入，LeakCanary应该仅用于debug阶段。
    debugImplementation("com.squareup.leakcanary:leakcanary-android:3.0-alpha-8")
//    implementation("com.squareup.leakcanary:leakcanary-android:3.0-alpha-8")
}