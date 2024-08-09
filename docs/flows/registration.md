# Registration User Flows

## Happy Flows

__When__ a user registers into the application\
__And__ the Gateway Service receives the request\
__And__ the Gateway calls the User Service\
__And__ the User Service validates the data\
__And__ the username and email are unique\
__Then__ the user account is created in the database\
__And__ the account is marked as not verified\
__And__ the password is stored as a hash within the database\
__And__ a verification link is generated for the user\
__And__ the User Service calls the Notification Service\
__And__ the Notification Service calls the Email Service\
__And__ a verification email is sent to the email address\
__And__ the verification email contains the verification link for the account\
__And__ the verification link has a limited lifespan

## Negative Flows

### Unique constraint violation
__Given__ a user registers into the application\
__And__ the Gateway Service receives the request\
__And__ the Gateway calls the User Service\
__When__ the User Service validates the data\
__And__ the username and email are not unique\
__Then__ an error is propagated back to the user
__And__ the error message clearly explains the encountered issue

### Null or blank fields
__Given__ a user registers into the application\
__And__ the Gateway Service receives the request\
__And__ the Gateway calls the User Service\
__When__ the User Service validates the data\
__And__ one or more of the mandatory fields are not populated\
__Then__ an error is propagated back to the user
__And__ the error message clearly explains the encountered issue

### Any other internal error
__Given__ a user registers into the application\
__And__ the Gateway Service receives the request\
__And__ the Gateway calls the User Service\
__When__ the User Service validates the data\
__And__ an internal error is encountered\
__Then__ an error is propagated back to the user
__And__ the error message is a generic message (i.e. "Something went wrong")