plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("checkstyle")

}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

checkstyle {
	toolVersion = "10.12.5"  // Актуальная версия Checkstyle
	configFile = file("config/checkstyle/my-custom-checkstyle.xml")  // Путь к вашему файлу
	isIgnoreFailures = false  // Остановка сборки при ошибках
	maxErrors = 0             // Максимальное количество ошибок (0 = любая ошибка запрещена)
	maxWarnings = 0           // Максимальное количество предупреждений
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("jakarta.persistence:jakarta.persistence-api")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework:spring-core")
	implementation("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")


}

tasks.withType<Test> {
	useJUnitPlatform()
}
