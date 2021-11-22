# Project integrating Gradle - Allure - TestNG

This project is created to try out integrating Gradle with Allure reporter and with TestNG.
I'm going to move from the simplest possible configuration to more complex as more features will be required.

As of November 22, 2021, the latest versions of tools are following:
- [Gradle](https://gradle.org/): 7.3
- Allure gradle plugin: 2.9.6

[Allure-gradle GitHub](https://github.com/allure-framework/allure-gradle)

[Allure Gradle plugin](https://plugins.gradle.org/plugin/io.qameta.allure)


- TestNG: 7.4.0
[TestNG web site](https://testng.org/doc/)

[TestNG GitHub](https://github.com/cbeust/testng)

## Initial configuration
Initially `build.gradle` file looked like following
```
plugins {
    id 'java'
}

group 'nik.roma'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.testng:testng:7.4.0'
}

test {
    useTestNG()
}
```
## Experiment 1: add only Allure gradle plugin
See branches starting with `v1-`.

Let's add Allure gradle plugin
```
plugins {
    id 'java'
    id 'io.qameta.allure' version '2.9.6'
}
```
After this modification, `allureReport` task becomes available.
Running command 
```
./gradlew clean test allureReport
``` 
leads to the following
* Allure commandline is downloaded into `/build/allure/commandline` folder. 
Based on the artifacts found in this folder, we can conclude that Allure commandline version is 2.13.6
* Allure results are collected into `/build/allure-results` folder.
* Allure report is generated into `/build/reports/allure-report` folder
* Allure report is opened without any issues in a browser.
