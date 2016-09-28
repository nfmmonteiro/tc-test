Notes
-----
 I've built this solution with the goal of allowing for more discount business rules to be added without having to change much (just implementing the required rules and adding them to the DiscountsEngine). 
 I've also took the decision of allowing one rule to give 0..N discounts (not just one) for more flexibility.
 
 I've used Spock test framework because of its flexibility to do data driven testing (with data tables).
 
 In a real-world situation I wouldn't go and implement a rules engine as I did in the exercise (I would go and choose an existent rules engine that would allow for the business rules not to be hard-coded in the application, but to be specified on a rules file or on a database - e.g. Drools).
 
 Also, in a real-world situation I would have used a Dependency Injection library (e.g. Google guice or Spring DI) instead of injecting dependencies via constructor (e.g. on Basket or DiscountRule) - and would have used the @Inject/@Autowire annotation.
 
 No comments in the classes were written because I trust my tests to document my implementation. On very rare occasions I write comments in the code.
 
 The main point of reference for the exercise is the BasketIntegrationSpec (on it you can find all the acceptance criteria being met). All other specs are unit tests to verify the behaviour of a component in isolation.


Development
-----------
- JDK "1.8.0_91" (but it should work with JDK 1.7)
- Maven 3.3.9 (but it should work with Maven 2.x)

Building with Gradle
--------------------
Type:
    ./gradlew clean test

Building with Maven
-------------------
Type:
    mvn clean test

Creating an IDEA project
---------------------------
Type:
    ./gradlew cleanIdea idea



