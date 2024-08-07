# Gateway Service

Maven module name: __gateway-service__\
Owner: __common ownership__

## Description

Single entrypoint to the back-end of the Budget Tracker application.\
This is the only externally exposed service by the application.\
All requests from the client (UI) go through this service, thus this service acts as a proxy between the client and the 
internal services of the application.

The Gateway authenticates and authorizes users and also sets the communication protocol (HTTP / HTTPS).
It is designed in a two-layer architecture, having a Presentation and Business layer. 

## Technologies
- Java 21+
- Maven
- Spring Boot
- Spring Authentication
- Docker
- 2-layer architecture
- HTTPS (Optional)
