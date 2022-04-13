Requirements: JDK 11+, Kotlin 1.6.10+

### How to run tests
* Use gradlew with `./gradlew clean build` command
* or use IDEA junit run configuration
* or use docker to run tests (will fail on Apple Silicon, because it requires another image):
    * open console in root project directory
    * `docker build --tag api-tests . `
    * `docker run api-tests`

### Tech stack:
* RestAssured as REST client testing library
* Kotest as a test framework (with junit runner)


### TODOs:
* Move endpoints to enum
* Extend endpoints tests
* Move token generation to external function
* Add linter exception for RestAssured statements indents
* Move all data to generators (could be extended with faker)
* Extend data validation test
* Add swagger schema check