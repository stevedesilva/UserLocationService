# User Location Service


This services is a Java 8 microservice that exposes the User Location data for user in and around the London area (50 mile radius).

This application has been built using SpringBoot, and follows common coding conventions.

## Tools used:

Wiremock  - to simulate and test the external user location service.
Openfeign - choosen to simplify access to the gateway user service.
Spring Boot - using an embedded tomcat service exposed on port 8080
Java 8 


Run the UserLocationApplication is the java class which starts the tomcat on 8080.



## Building the service

mvn package

## Running the service
The executable jar is located in the target directory:

java -jar target/user-location-1.0.0.jar

## User Location Service endpoint

Run using client tool (e.g. Postman) 

Header
Content-Type application/json

GET http://localhost:8080/users

## Documentation

The swagger documents can be found here:

http://localhost:8080/swagger-ui.html
