# UA Tutorial Code Examples

This is a project containing code examples for the UA tutorial made for ICANN.

There are currently two projects in this solution:

1. eai-validation which contains the UA tutorial full example of the correct way to validate and send an email with ICU & Jakarta Mail in Java
2. obsolete-validation which contains the wrong and obsolete way (validation with a regex) shown at the beginning of the tutorial

## Testing the Examples With Docker

If you just want to do some tests with the commandline, docker is your friend. To start 
a fake smtp server and examples ready to run: 

    $ docker-compose up -d

Then open your browser here: http://localhost:8025/# to see emails coming in.

### Testing the eai-validation Project

Then, you can fire some internationalized emails by typing commands like:

    $ docker container exec ua-tuto-java ./eai -e test@test.世界

You should see an email enter labelled "X.com subscription" (the fake company used in the tutorial).

### Testing the obsolete-validation Project

You can lookup what kind of errors internationalized emails will generate by typing commands like:

    $ docker container exec ua-tuto-java ./obsolete -e test@test.世界

You should see something like:

    java.lang.Exception: Invalid email address, please review it and submit again

If you want to know what would be the error on the old JavaMail 1.5.6, you can add the flag -n or --no-validation to try to send the email without the regex validation:

    $ docker container exec ua-tuto-java ./obsolete --email test@test.世界 --no-validation

You should get an error from javax.mail then:
    
    javax.mail.internet.AddressException: Domain contains control or whitespace in string ``test@test.世界''

## Testing on your host machine

To test on your machine, you need to install first java 11+: 

    https://openjdk.java.net/install/

and Gradle:     

    https://gradle.org/install/

Then go in each project and enter

    $ ./gradlew build
    $ ./gradlew install

to build and create the executables here:

    ./eai-validation/build/install/eai-validation/bin/eai-validation
    ./obsolete-validation/build/install/obsolete-validation/bin/obsolete-validation
