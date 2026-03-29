fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.13.1"
    id("org.jetbrains.changelog") version "1.3.1"
    id("org.jetbrains.qodana") version "0.1.13"
    kotlin("jvm") version "2.3.0"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    intellijPlatform {
        create(properties("platformType"), properties("platformVersion"))

        properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty).forEach { plugin ->
            if (plugin.contains(':')) {
                val parts = plugin.split(':')
                plugin(parts[0], parts[1])
            } else {
                bundledPlugin(plugin)
            }
        }

        jetbrainsRuntime()
    }
    implementation(kotlin("stdlib-jdk8"))
}

intellijPlatform {
    pluginConfiguration {
        name.set(properties("pluginName"))

        ideaVersion {
            sinceBuild.set(properties("pluginSinceBuild"))
            untilBuild.set(properties("pluginUntilBuild"))
        }
    }

    signing {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishing {
        token.set(System.getenv("PUBLISH_TOKEN"))
        val channel = properties("pluginVersion").split('-').getOrElse(1) { "default" }.split('.')[0]
        channels.set(listOf(channel))
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}

tasks {
    wrapper {
        gradleVersion = properties("gradleVersion")
    }

    publishPlugin {
        dependsOn("patchChangelog")
    }
}
