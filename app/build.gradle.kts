plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.notebookmobile"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.notebookmobile"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    /*sourceSets {
        getByName("main") {
            java.srcDir("src/main/java/com/example/notebookmobile")
        }
    }*/
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Implementations for the lexer and parser
    //implementation(files("src/main/java/com/example/notebookmobile/libs/jflex-full-1.9.1.jar"))
    //implementation(files("src/main/java/com/example/notebookmobile/libs/java-cup-11b.jar"))
    implementation(files("src/main/java/com/example/notebookmobile/libs/java-cup-11b-runtime.jar"))
    // Implementation for math expression evaluator
    //implementation(libs.exp4j)
    // Implementation for math view
    //implementation(libs.mathview)
}

// TASKS TO GENERATE LEXERS AND PARSERS
tasks.register("generateAnalyzers") {
    group = "code generation"
    description = "Generates the lexer and parser using JFlex and CUP"

    doFirst {
        println("Generating lexers from TextLexer.flex and CodeLexer.flex...")
        javaexec {
            classpath = files("/home/carloslopez/AndroidStudioProjects/NotebookMobile/app/src/main/java/com/example/notebookmobile/libs/jflex-full-1.9.1.jar")
            mainClass.set("jflex.Main")
            args = listOf(
                "/home/carloslopez/AndroidStudioProjects/NotebookMobile/app/src/main/java/com/example/notebookmobile/text_analysis/TextLexer.flex",
                "/home/carloslopez/AndroidStudioProjects/NotebookMobile/app/src/main/java/com/example/notebookmobile/code_analysis/CodeLexer.flex"
            )
        }

        println("Generating parsers from TextParser.cup and CodeParser.cup...")
        javaexec {
            classpath = files("/home/carloslopez/AndroidStudioProjects/NotebookMobile/app/src/main/java/com/example/notebookmobile/libs/java-cup-11b.jar")
            mainClass.set("java_cup.Main")
            args = listOf(
                "-destdir", "/home/carloslopez/AndroidStudioProjects/NotebookMobile/app/src/main/java/com/example/notebookmobile/text_analysis",
                "-parser", "TextParser",
                "-symbols", "sym",
                "/home/carloslopez/AndroidStudioProjects/NotebookMobile/app/src/main/java/com/example/notebookmobile/text_analysis/TextParser.cup"
            )
        }

        javaexec {
            classpath = files("/home/carloslopez/AndroidStudioProjects/NotebookMobile/app/src/main/java/com/example/notebookmobile/libs/java-cup-11b.jar")
            mainClass.set("java_cup.Main")
            args = listOf(
                "-destdir", "/home/carloslopez/AndroidStudioProjects/NotebookMobile/app/src/main/java/com/example/notebookmobile/code_analysis",
                "-parser", "CodeParser",
                "-symbols", "sym",
                "/home/carloslopez/AndroidStudioProjects/NotebookMobile/app/src/main/java/com/example/notebookmobile/code_analysis/CodeParser.cup"
            )
        }
    }
}


