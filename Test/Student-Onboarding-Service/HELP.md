# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.student.CBSE Student Onboarding Integration' is invalid and this project uses 'com.student.CBSE.Student.Onboarding.Integration' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.5/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.5/maven-plugin/build-image.html)
* [Coroutines section of the Spring Framework Documentation](https://docs.spring.io/spring-framework/reference/6.2.6/languages/kotlin/coroutines.html)
* [Validation](https://docs.spring.io/spring-boot/3.4.5/reference/io/validation.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/3.4.5/reference/web/reactive.html)
* [Spring Data Reactive MongoDB](https://docs.spring.io/spring-boot/3.4.5/reference/data/nosql.html#data.nosql.mongodb)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/3.4.5/reference/messaging/kafka.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

