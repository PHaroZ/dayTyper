plugins {
	kotlin("multiplatform") version "1.4.10"
}
group = "net.pharoz"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
	maven(url = "https://kotlin.bintray.com/kotlinx/")
}
kotlin {
	val hostOs = System.getProperty("os.name")
	val isMingwX64 = hostOs.startsWith("Windows")
	val nativeTarget = when {
		hostOs == "Mac OS X" -> macosX64("native")
		hostOs == "Linux" -> linuxX64("native")
		isMingwX64 -> mingwX64("native")
		else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
	}

	nativeTarget.apply {
		binaries {
			executable {
				entryPoint = "main"
			}
		}
	}
	sourceSets {
		val nativeMain by getting
		val nativeTest by getting
		commonMain {
			dependencies {
				implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.0")
			}
		}
		val commonTest by getting {
			dependencies {
				implementation(kotlin("test-common"))
				implementation(kotlin("test-annotations-common"))
			}
		}
	}
}
