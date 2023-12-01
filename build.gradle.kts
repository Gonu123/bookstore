plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.flywaydb.flyway") version "9.22.3"
	id("jacoco")
	id("jacoco-report-aggregation")
}

group = "com.project"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0-M1")
	implementation("org.bitbucket.b_c:jose4j:0.9.3")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.flywaydb:flyway-core:9.22.3")
	implementation("org.postgresql:postgresql:42.7.0")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.0.6")
	// https://mvnrepository.com/artifact/com.opencsv/opencsv
	implementation("com.opencsv:opencsv:5.9")
	implementation("org.glassfish.jersey.media:jersey-media-multipart:3.1.3")
	testImplementation(platform("org.junit:junit-bom:5.9.1"))
	//implementation("org.glassfish.jersey.media:jersey-media-multipart:3.1.3")
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.assertj:assertj-core:3.24.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jacocoTestCoverageVerification {
	dependsOn(tasks.test)
	violationRules {
		rule {
			limit {
				counter = "LINE"
				value = "COVEREDRATIO"
				minimum = "0.7".toBigDecimal()
			}
		}
	}
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required = true
		csv.required = true
		html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
	}
}
