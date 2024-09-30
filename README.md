# java-starter


## Getting started

This wiki is used as a guide on how to setup this project.
In case of any problems feel free to contact [dominik.pivka@tietoevry.com]

# Welcome new one. This is your new training project assignment.

Tutorial version: `1.3.0`

- Do not start this project until you have been asked to do it.
- Please read project information below and start with the BLOCKS.
    - Blocks are separated to the BE (backend) and FE (frontend) blocks.
    - Each block is estimated with time. We stress this is just an estimation.
- Do not hesitate to google your issue and after that ask other internship students to help you
- Do not use old or running projects from this GitLab. A code can be corrupted, deprecated, containing bad practices etc.
- Do not stress, this is training, not a test.

# Project info
We need a web application for users to capture an inspiration when it strikes! The application should simply provide the quickest way to offload ideas, thoughts, and to-dos without losing focus. An application consists of two parts: backend in Java/Kotlin (Kotlin only if accepted to be an Android developer) and frontend in React.

## User stories
1. As User, I want to create, view, edit and delete my own notes.
2. As User, I want to create, view, edit and delete my own note groups.
3. As User, I want to assign or remove note to/from note group.
4. As User, I can assign a tag to note.
5. As User, I can assign a tag to note group.
6. As Admin, I want to create, view, edit and delete Users

## Non-functional requirements
1. Notes should have an expiration date or duration.
2. Expired notes should be removed from the application.

## Reporting
After each block, report your progress to `dominik.pivka@tietoevry.com`, `pavol.comorek@tietoevry.com`:

- What did I achieve/finalize this week
- Spent time on what blocks

Push to the repository your daily changes. Do not use the `main` branch for development. Instead, create the new branch `development`.

NOTE: Time in the parentheses in the block title is just an estimation.

# BE: Block 0 (8h)
- You will start with the backend. The aim is to build Java micro-service with REST API
- Be sure that you have an elementary knowledge about GIT and you already did the GIT tutorial in [Guide](https://danielkummer.github.io/git-flow-cheatsheet/)
- **!**Use HTTP for cloning of the project, SSH is not available at the moment**!**
- Setup your environment (IDE)
- Install database [PostgreSQL](https://www.postgresql.org/)
- You should use project [Lombok](https://projectlombok.org/) (Java library) to never write another getter, setter or equals method again.
    - Include it as dependency in pom.xml
- In the GitLab create a new group with name matching this pattern: `NCP_your5+3`, set visibility level to `private`
    - Grant access to all users who participate in YOUR project (if any)
    - Create a new project in this group for the backend (after I burned many, many times, please do NOT name it backend)
    - Install git for Windows (https://git-scm.com/download/win) under C:\Program Files\Git
        - Make sure you have git in PATH
- Read about [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)

TIP: use command: git config --global http.sslVerify false
if you are having ssl problem

# BE: Block 1 (24h)
- Analyze requirements
- Use [Spring Boot Initializer] (https://start.spring.io/) to init your [Spring Boot] (https://spring.io/guides/gs/spring-boot/) project and setup project metadata for Initializer as follow:
    - Group id: `cz.tieto.{your5+3}`
    - Artifact id: `notes`
- Create basic packages structure in the project: service, controller, repository, config, model
- Study:
    - [Spring Boot] (https://spring.io/guides/gs/spring-boot/)
    - [Maven building tool] (https://spring.io/guides/gs/maven/) and dependencies
    - Database [PostgreSQL](https://www.postgresql.org/)
    - Accessing data with [JPA](https://spring.io/guides/gs/accessing-data-jpa/)
    - [REST backend api] (https://spring.io/guides/gs/rest-service/)
- HINT: Use [.gitignore](https://git-scm.com/docs/gitignore) file. This file specifies intentionally untracked files that Git should ignore in your project. You can use [gitignore generator](https://www.gitignore.io/) and key words like: Java, Spring Boot, IntelliJ (or your IDE), Maven (or Gradle) and OS.

# BE: Code Review
- Ask for the code review

# BE: Block 2 (24h)
- Define basic model (use entity [annotations](https://spring.io/guides/gs/accessing-data-jpa/) and define relations)
    - Your basic objects should be: `User`, `Note`, `Tag`, `Group`
    - Check the [Lombok](http://www.baeldung.com/intro-to-project-lombok) annotations
    - These will represent your DB data
- Define DTO model to communicate through API ([introduction](https://www.baeldung.com/java-dto-pattern))
    - TIP: Not all data must be returned and sometimes more DTOs per data source can be useful.
    - TIP: Watch this [explanation](https://www.youtube.com/watch?v=5yquJa2x3Ko) of DTO.
- Create a new database in PostgreSQL (GUI or `create database newdbnameorwhatever`)
- Configure [application.properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) (alternatively [yaml](http://www.baeldung.com/spring-yaml) configuration file) and [Spring JPA](http://www.baeldung.com/the-persistence-layer-with-spring-and-jpa#boot) connection to the database via this properties file
- Read about [relationships](http://www.baeldung.com/spring-data-rest-relationships) and consider bi-directional vs unidirectional mapping
- HINT: You can enable the [JPA auditing](http://www.baeldung.com/database-auditing-jpa) and use auditing of changes in entities
- Define base CRUD operations and all needed endpoints
    - Consider the [best practicies](https://www.freecodecamp.org/news/rest-api-best-practices-rest-endpoint-design-examples/) for [REST api] (https://spring.io/guides/gs/rest-service/)
    - Read about [HTTP methods and REST](http://www.restapitutorial.com/lessons/httpmethods.html)
    - Do not implement logic and data access yet. Just define an API.
    - HINT: You will need more than a few endpoints, try to think about the future. For instance:
        - `/api/users` (POST, GET)
        - `/api/users/{id}` (PUT, GET, DELETE)
    - HINT: There is an excellent application [Postman](https://www.getpostman.com/) to test your API

# BE: Code Review
- Ask for the code review

# BE: Block 3 (24h)
- Implement services and repositories
    - Use annotations and [dependency injection](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-spring-beans-and-dependency-injection.html)
- Implement a business logic
- Additional behaviour specification:
    1. The `tag` is visible only to user who created it.
    1. The `user` is able to update and delete only his `tags`
    1. Removal of the `user` should also remove all user's `notes`, `groups`, `tags`
    1. Removal of the `group` should also remove all groups' `notes` and `tags` in case that tags are not used in another `notes` or `groups`
    1. Removal of the `note` should also remove all note's `tags` in case that tags are not used in another `notes` or `groups`
    1. Removal of the `tag` should remove all tag occurrence in `tags`, `groups`, `notes`

# BE: Block 4 (2h)

- Implement Swagger UI.
- Create documentation for all controllers using annotations.
- HINT: [Swagger UI - Spring boot](https://www.baeldung.com/spring-rest-openapi-documentation)


# BE: Code Review
- Ask for the code review

# BE: Block 5 (24h)
- Write unit-tests ([JUnit 5] (http://www.baeldung.com/junit-5)) (internal business logic/object mappings if any)
- Read about [JUnit best practicies](https://examples.javacodegeeks.com/core-java/junit/junit-best-practices)
- If you are familiar with JUnit4 read about the best [new features](https://www.netcentric.biz/insights/2020/07/junit5-new-features.html) in JUnit5
- Read about [Mockito](http://www.baeldung.com/mockito-annotations)
- Write integration [tests] (https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html) for crucial functionality (use as test enter point the endpoint call)
- Consider internal logging of error, warning and info events and read about [Java logging](http://www.baeldung.com/java-logging-intro)
- HINT: Use at least the SLF4J and log error events like token fails, user extraction fails from Principal, business logic fails etc.
- TIP: This is great [tutorial](https://www.youtube.com/watch?v=Geq60OVyBPg) for unit tests.
- TIP: Read this [tutorial](https://spring.io/guides/gs/testing-web/) abou testing and consider using MockMVC or Mockito or TestRestTemplate
- TIP: Here are some links that could help you.
    - [Mockito](https://site.mockito.org/)
    - [AssertJ](https://assertj.github.io/doc/)
    - [Reflectoring](https://reflectoring.io/unit-testing-spring-boot/?utm_source=pocket_saves)

# BE: Code Review
- Ask for the code review

# BE: Block 6 (24h)
- Configure authorization & authentification
    - In the very beginning, you skipped the authorization and authentication in your application. However, this is a crucial part of application security
- You will use keycloak as your Auth provider. We have created realm for newcomers that you can use. For access write a message to `dominik.pivka@tietoevry.com`
- **The admin user is able to access all endpoints and all actions**
- **User must be able to register**
- Spring boot security had new release and they removed specific adapter which was used in Keycloak library. After that we will use another way, in our case is best way to use oauth client.
- First of all, you have to setup some configurations, create beans for that.
- In first bean configuration it should look like this:
```java
    ClientRegistration registration = ClientRegistration
        .withRegistrationId("my-client")
        .clientId(...)
        .clientSecret(...)
        .authorizationGrantType(...)
        .redirectUri(...)
        .authorizationUri(...)
        .tokenUri(...)
        .jwkSetUri(...)
        .build();
    return new InMemoryClientRegistrationRepository(registration);
```
- HINT: Hide your client secret to configuration file, [external configuration](https://stackoverflow.com/questions/30528255/how-to-access-a-value-defined-in-the-application-properties-file-in-spring-boot)
- Also you have to create @Bean for JwtDecoder

```java
    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(...);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(...);
        OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(withIssuer);
        jwtDecoder.setJwtValidator(validator);
        return jwtDecoder;
    }
```

- And last @Bean you will create is RestTemplate

---

- You can find Client secret in Clients -> newcomer -> Credentials
- HINT: [Keycloak info](https://staging.int.tieto.com/newcomer/keycloak/auth/realms/newcomer-realm-dev/.well-known/openid-configuration)
- After configuration, just create class SecurityConfig and enable web security in that, add some beens to your code:
    1. SessionAuthenticationStrategy
    1. SecurityFilterChain
        - Here you will specify "request handling"
        - HINT: Add this fragment of code:
            - http.oauth2Login();
            - http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        - HINT: disable CSRF
    1. AuthenticationManager
- After that you will need to add [certification](https://git.internship.int.tieto.com/intern/root/-/wikis/Secured-LDAP-connection-(SSL)) to your JDK.


# BE: Code Review
- Ask for the code review

# That's it! Congratulations
