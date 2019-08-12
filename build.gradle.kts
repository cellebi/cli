import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `maven-publish`
    signing
    kotlin("jvm") version "1.3.41"
}

group = "pub.cellebi"
version = "0.0.1-SNAPSHOT"

val developer: String by project
val emailAddress: String by project
val team: String by project
val mavenDevUrl: String by project
val jiraPassword: String by project

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("pub.cellebi", "option", "0.0.1-SNAPSHOT")
    testImplementation("junit:junit:4.12")
    implementation(kotlin("stdlib-jdk8"))
}
configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.named<Jar>("jar") {
    configManifest()
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.named<Javadoc>("javadoc") {
    options.encoding("UTF-8")
}

tasks.register<Jar>("sourcesJar") {
    configManifest()
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava)
}

tasks.register<Jar>("javadocJar") {
    configManifest()
    archiveClassifier.set("javadoc")
    from(tasks.javadoc.get().destinationDir)
    dependsOn("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("option") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
            pom {
                name.set("option")
                description.set("A Java api to resolve cli options")
                url.set("https://github.com/cellebi/option")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("cheung")
                        name.set(developer)
                        email.set(emailAddress)
                    }
                }
                scm {
                    url.set("https://github.com/cellebi/option")
                    connection.set("scm:git:git@github.com:cellebi/option.git")
                    developerConnection.set("scm:git:ssh:git@github.com:cellebi/option.git")
                }
                repositories {
                    maven {
                        url = uri(mavenDevUrl)
                        credentials {
                            username = developer
                            password = jiraPassword
                        }
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["option"])
}

tasks.register("cellebiPublish") {
    logger.quiet("[gradle-cellebi] publish")
    group = "publishing"
    dependsOn("test")
    dependsOn("publish")
}

tasks.register("cellebiDevPublish") {
    logger.quiet("[gradle-cellebi] Dev publish")
    group = "publishing"
    dependsOn("test")
    dependsOn("publishToMavenLocal")
}

fun Jar.configManifest() {
    manifest {
        attributes("Specification-Team" to team)
        attributes("Specification-Developer" to developer)
        attributes("Specification-Version" to project.version)
    }
}