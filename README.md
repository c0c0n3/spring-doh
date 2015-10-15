Spring...D'oh!
==============

Brain-dead apps to get a feel of developing with [Spring 4][spring].


Idea
----
Explore Spring 4 features through coding; start with a just a few POJO's that do
whatever they do (the so called business illogic?), then bean-ify them and build
fully functional Spring apps to host them.


Hall of Shame
-------------
Featuring:

1. *Tripsters*. Standalone POJO's to encode the core functionality *without* any 
dependencies on Spring. A tripster goes on a trip away from home and then comes
back; each app will have a bunch of these happy dudes along with a tripster
spotter who can show you a tripster's trip. Find the code in `app.core`.
2. *Bare Bones App*. Simple CLI app to let you play with tripsters; it has no
dependency on Spring. Look for it in `app.run`.
3. *Spring CLI App*. Same as the bare bones app, but hosts beanified POJO's (see
`app.beans` and `app.config.Wiring`) sprinkled with a few aspects (`app.aspects`)
and configuration (`app.config`) in a CLI Spring container. Configuration is
quite saucy with Java-based config, profiles and priorities. So bake in the
container until crispy; take out of the oven and eat in `app.run`.
4. *Spring Boot Web App*. Uses Spring MVC & REST controllers to make the core
functionality available through an embedded [Undertow][undertow] Web server,
courtesy of Spring Booty auto-configuration. Available in `app.run`.
5. *Spring Boot WebQ*. Extends the Spring Boot Web App with an embedded
[HornetQ][hornetq] message queue for a taste of Spring JMS. You know where to
find the Java class. (`app.run`? D'oh!)
6. *Testing*. From unit to integration to end to end testing, we've got quite
a spread of ingredients for the ultimate tech-cocktail: [JUnit][junit] (with
theories), [Hamcrest][hamcrest], [Mockito][mockito], and all the Spring testing
goodies such as Mock MVC. Shake well and drink irresponsibly; recommended for
sundowners.

The code in `app.util` is some general-purpose stuff I ended up writing myself
as I went along because my Google searches for better options failed me. Grab
if you think it could be any useful to you too.

All the code in this repository is Java 8 with heavy use of Java's very own
flavour of streams and dysfunctional programming. 


Build & Run...for the loo
-------------------------
Build and test everything:

    ./gradlew build

Use `gradlew <task>` (Unix; `gradlew.bat` for Windows) for finer control over
building, testing, etc. This lists all available build tasks:

    ./gradlew tasks

To run any of the apps in the `app.run` package just follow the instructions
in the app's Java Doc. 


Tricksy Eclipsie
----------------
Using Eclipse? You can generate all project files with:

    ./gradlew eclipse

then just import the project into Eclipse; alternatively if you have the Gradle 
Eclipse plugin (Buildship), you could instead run: 

    ./gradlew build
 
then import into Eclipse as a Gradle project.


Beer-time Reading
-----------------
As I banged my forehead against the monitor, I found chapters 1 through 4
and chapter 21 of [Spring in Action][spring-in-action] a decent pain killer.
I then had to dig deeper in the Spring and Spring Boot manuals, but the best
pain relief came from reading the Spring guys' source code on GitHub.




[hamcrest]: http://hamcrest.org/
    "Hamcrest home"

[hornetq]: http://hornetq.jboss.org/
    "HornetQ home"

[junit]: http://junit.org/
    "JUnit home"

[mockito]: http://mockito.org/
    "Mockito home"

[spring]: https://spring.io/
    "Spring home"

[spring-in-action]: http://www.manning.com/walls5/
    "Spring in Action, Fourth Edition"

[undertow]: http://undertow.io/
    "Undertow home"
