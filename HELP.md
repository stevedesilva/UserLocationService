# User Location Service

This services is a microservice that exposes the User Location data for user in and around the London area (50 mile radius).

This application has been built using SpringBoot, and follows common coding conventions.

## Tools used:

Wiremock  - to simulate and test the external user location service.
Openfeign - choosen to simplify access to the gateway user service.
Spring Boot - using an embedded tomcat service exposed on port 8080


Run the UserLocationApplication is tjava class which starts the tomcat on 8080.

Running the app

http://localhost:8080/users
