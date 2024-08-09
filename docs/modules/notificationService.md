# Notification Service

Maven module name: __notification-service__\
Owner: __to be decided__

## Description

The Notification Service is an internal service of the Budget Tracker application responsible for CRUD operations on
notifications. \
Notifications at the moment are just simple emails. This service receives notification requests from other services,
persists them in the database and sends them further for sending to the Email Service.

It is designed in a three-layered architecture, having a Presentation, Business and Data layer.

## Technologies

- Java 21+
- Maven
- Spring Boot
- Docker
- 3-layer architecture
- RabbitMQ
- Hibernate
