# Income User Flows

## Happy Flows

### Creation

__When__ the user creates an income in the application
__And__ the Gateway Service receives the request\
__And__ the Gateway calls the Budget Service\
__And__ the Budget Service validates the existence of the user\
__And__ the Budget Service validates the received payload\
__Then__ the Budget Service inserts a new entry in its income table\
__And__ a successful message is propagated back to the user

## Negative Flows
