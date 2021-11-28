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

**Goal:** I want to generate allure report after my tests are run.

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
Based on the artifacts found in this folder, we can conclude that Allure commandline version is `2.13.6`
* Allure results are collected into `/build/allure-results` folder.
* Allure report is generated into `/build/reports/allure-report` folder
* Allure report is opened without any issues in a browser.

## Experiment 2: Specify Allure report version explicitly
See branches starting with `v2-`.

**Goal:** I want to explicitly control the version of allure report. 

In Experiment 1, allure gradle plugin seems to select 
a default version of allure report, which was `2.13.6`.
At the moment, the latest version of allure report is `2.16.1`.
To use it, let's add the following to `build.gradle`
```
allure {
    version = '2.16.1'
}
```
Now, running command
```
./gradlew clean test allureReport
``` 
leads to the following:
* Allure commandline is downloaded into `/build/allure/commandline` folder.
This time the version is `2.16.1`.

Other results are the same as in Experiment 1.

## Experiment 3. Add SLF4J NOP logger
See branches starting with `v3-`.

**Goal:** I want to get rid of SLF4J errors in console.

When allure dependency is added to the project, and if `testLogging.showStandardStreams = true` added to `build.gradle`
under `test`, there are following errors in console:
```
Gradle Test Executor 5 STANDARD_ERROR
    SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```
To avoid them, let's add `slf4j-nop` dependency explicitly:
```
testImplementation group: 'org.slf4j', name: 'slf4j-nop', version: '1.7.32'
```
After this, executing `./gradlew test allureReport` does not generate any errors in console.

## Experiment 4. Specify allure-testng adaptor version explicitly
See branches starting with `v4-`.

**Goal:** I want to select allure-testng adaptor version explicitly.

When running `./gradlew test --info`, there is the following in console:
```
allure-gradle: added dependency io.qameta.allure:allure-testng:2.13.9 to runtimeElements scope of org.testng:testng:7.4.0
```
which means that allure-gradle automatically adds allure-testng of version `2.13.9`.
At the moment, the latest version of allure-testng is `2.16.1`.
To use it, let's add the following to `build.gradle` under `allure`:
```
allure {
    ...
    adapter {
        allureJavaVersion = "2.16.1"
    }
}
```
Now, running `./gradlew test --info` leads to the following line in console:
```
allure-gradle: added dependency io.qameta.allure:allure-testng:2.16.1 to runtimeElements scope of org.testng:testng:7.4.0
```

## Experiment 5. Process Allure annotations
See branches starting with `v5-`.

**Goal:** I want to see reports with Steps, Attachments, Epics and Stories details.

Allure automatically configures `aspectjweaver`] so that annotations are processed by default.
The version of `aspectjweaver` used by default is `1.9.5`.
Currently, it is defined in 
[AllureAdapterExtension.kt](https://github.com/allure-framework/allure-gradle/blob/master/allure-adapter-plugin/src/main/kotlin/io/qameta/allure/gradle/adapter/AllureAdapterExtension.kt).
As of today, the latest stable version of [`aspectjweaver`](https://mvnrepository.com/artifact/org.aspectj/aspectjweaver) is `1.9.7`.
To explicitly configure it, add the following into `build.gradle`:
```
allure {
    ...
    adapter {
        ...
        aspectjVersion = "1.9.7"
    }
}
```
Some tests are added in `nik.roma.allure.annotations` package to learn how annotations work.
Run `./gradlew clean test allureReport` to run tests and generate the report.

## Experiment 6. Use @Issue, @TmsLink, and @Link annotations
See branches starting with `v6-`.

**Goal:** I want to use @Issue, @TmsLink, and @Link annotations, and provide patterns to them.

Add `allure.properties` file to test resources with the following content:
```
allure.link.issue.pattern=https://example.org/issue/{}
allure.link.tms.pattern=https://example.org/tms/{}
allure.link.mylink.pattern=https://example.org/mylink/{}
allure.link.help.pattern=https://example.org/help/{}
```
The first pattern is for @Issue annotation.
The second pattern is for @TmsLink annotation.
The third and fourth patters are for @Link annotations with type `mylink` and `help` respectively.
For usage, see tests in `nik.roma.allure.links` package.

**References**
- [allure-testng documentation](https://github.com/allure-framework/allure-docs/blob/master/content/frameworks/java/testng.md) in GitHub.

# References

#### Allure gradle plugin
- [Allure Gradle plugin](https://github.com/allure-framework/allure-gradle) GitHub repository
- [Allure Gradle plugin](https://plugins.gradle.org/plugin/io.qameta.allure) in Gradle plugin portal
- Default version for Allure report (commandline) used by Allure gradle plugin can be found in [AllureExtension.kt](https://github.com/allure-framework/allure-gradle/blob/master/allure-base-plugin/src/main/kotlin/io/qameta/allure/gradle/base/AllureExtension.kt)
- Default versions for Allure-java adapters and for `aspectjweaver` can be found in [AllureAdapterExtension.kt](https://github.com/allure-framework/allure-gradle/blob/master/allure-adapter-plugin/src/main/kotlin/io/qameta/allure/gradle/adapter/AllureAdapterExtension.kt)

#### Allure report
- [Allure report](https://github.com/allure-framework/allure2) GitHub repository
- [Allure documentation](https://docs.qameta.io/allure-report/)
- [Allure documentation](https://docs.qameta.io/allure/) earlier version
- [Allure docs](https://github.com/allure-framework/allure-docs) in GitHub

#### Allure testng adapter
- [Allure-java atapters](https://github.com/allure-framework/allure-java) repository
- [Allure testng docs](https://github.com/allure-framework/allure-docs/blob/master/content/frameworks/java/testng.md) in GitHub
- [Allure testng docs](https://docs.qameta.io/allure/#_testng)