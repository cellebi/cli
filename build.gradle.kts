plugins {
    `java-library`
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("pub.cellebi", "option", "0.0.1-SNAPSHOT")
    testImplementation("junit:junit:4.12")
}
