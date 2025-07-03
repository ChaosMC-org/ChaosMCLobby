import org.sayandev.plugin.StickyNoteModules

plugins {
    java
    kotlin("jvm") version "2.1.20"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("de.eldoria.plugin-yml.bukkit") version "0.7.1"
    id("org.sayandev.stickynote.project")
}

val slug = rootProject.name.lowercase()
group = "xyz.lncvrt"
version = "1.0.0"

stickynote {
    modules(StickyNoteModules.BUKKIT)
}

repositories {
    mavenCentral()
    mavenLocal()
    
    maven("https://repo.sayandev.org/snapshots")
    maven("https://repo.sayandev.org/releases")
    maven("https://repo.sayandev.org/private")
    maven("https://repo.lncvrt.xyz/releases")
    maven("https://repo.lncvrt.xyz/snapshots")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")
    compileOnly("xyz.lncvrt:chaosmcapi:1.0.0")
}

tasks {
    jar {
        manifest {
            attributes["paperweight-mappings-namespace"] = "mojang"
        }
    }

    shadowJar {
        archiveFileName.set("${rootProject.name}-${version}.jar")
        archiveClassifier.set(null as String?)
        destinationDirectory.set(file(rootProject.projectDir.path + "/bin"))
        manifest {
            attributes["paperweight-mappings-namespace"] = "mojang"
        }
    }

    runServer {
        minecraftVersion("1.21.7")
        
        javaLauncher = project.javaToolchains.launcherFor {
            vendor = JvmVendorSpec.JETBRAINS
            languageVersion = JavaLanguageVersion.of("21")
        }
        
        jvmArgs("-XX:+AllowEnhancedClassRedefinition")
    }
    
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }
    
    configurations {
        create("compileOnlyApiResolved") {
            isCanBeResolved = true
            extendsFrom(configurations.getByName("compileOnlyApi"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
    
    processResources {
        filesMatching(listOf("**plugin.yml", "**plugin.json")) {
            expand(
                "version" to project.version as String,
                "slug" to slug,
                "name" to rootProject.name,
                "description" to project.description
            )
        }
    }
}
    
bukkit {
    main = "$group.${slug}.${rootProject.name}Plugin"
    version = rootProject.version.toString()
    website = "lncvrt.xyz"
    foliaSupported = true
    apiVersion = "1.21"
    depend = listOf()
    authors = listOf("Lncvrt")
    prefix = rootProject.name
}
    
java {
    disableAutoTargetJvm()
    if (gradle.startParameter.getTaskNames().any { it.startsWith("runServer") || it.startsWith("runFolia") || it.startsWith("runVelocity") }) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    } else {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }
}
