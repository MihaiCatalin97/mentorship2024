# Calculation Service

Maven module name: __calculation-service__\
Owner: __to be decided__

## Description

The Calculation Service is an internal service of the Budget Tracker application responsible for 
the application's more complex logic such as generating reports, summaries and running cron-jobs 
(procedures that execute periodically).

Interacts with the Budget Service, Notification Service and Gateway Service.

It is designed in a two-layered architecture, having a Presentation and a Business layer.
