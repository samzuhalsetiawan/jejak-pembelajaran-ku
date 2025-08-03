import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackRule

plugins {
    kotlin("multiplatform")
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled = true
                }
            }
        }
//        useEsModules()
        nodejs()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.12.0")
                implementation(npm("tailwindcss", "4.1.11"))
                implementation(npm("daisyui", "5.0.50"))
                implementation(npm("style-loader", "4.0.0"))
                implementation(npm("postcss", "8.5.6"))
                implementation(npm("postcss-loader", "8.1.1"))
                implementation(npm("@tailwindcss/postcss", "4.1.11"))
                implementation(npm("autoprefixer", "10.4.21"))
                implementation("org.jetbrains.kotlin-wrappers:kotlin-node:2025.8.2-22.13.10")
            }
        }
    }
}


//tasks.withType<KotlinJsCompile>().configureEach {
//    compilerOptions {
//        target = "es2015"
//    }
//}

val copyTailwindConfig = tasks.register<Copy>("copyTailwindConfig") {
    from("./tailwind.config.js")
    into("./build/js/packages/${rootProject.name}")

    dependsOn(":kotlinNpmInstall")
}

val copyPostcssConfig = tasks.register<Copy>("copyPostcssConfig") {
    from("./postcss.config.js")
    into("./build/js/packages/${rootProject.name}")

    dependsOn(":kotlinNpmInstall")
}

tasks.named("jsProcessResources") {
    dependsOn(copyTailwindConfig)
    dependsOn(copyPostcssConfig)
}