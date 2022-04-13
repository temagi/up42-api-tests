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

### Comments
Just a few statements about solution. In my opinion, api-tests should be also implemented on a different leves, for example, kind of e2e high-level tests and component tests on a service / app level, that run not for a real environment, but with isolated dependency to other services (if we talk about microservices architecture). Tests in solution looks exatly like an high-level tests, because it checks flow similar to real user.

But component test should be closer integrated with the app / service, so, it's better to implement it in the app repository, becaues it require closer integration (mocking / stubbing, abitlity to run together in containers, etc.)
