# Login User Flows

## Happy Flows

__When__ a user logs into the application\
__And__ the Gateway Service receives the request\
__And__ the Gateway calls the User Service\
__And__ the User Service validates the data\
__And__ the username / email and password match\
__And__ the account is verified and not disabled\
__Then__ a successful login message is propagated back to the user

## Negative Flows

### Account not verified

__Given__ a user logs into the application\
__And__ the Gateway Service receives the request\
__And__ the Gateway calls the User Service\
__When__ the User Service validates the data\
__And__ the account is not verified\
__Then__ an error is propagated back to the user\
__And__ the error message clearly explains the encountered issue

### Credentials do not match

__Given__ a user logs into the application\
__And__ the Gateway Service receives the request\
__And__ the Gateway calls the User Service\
__When__ the User Service validates the data\
__And__ the username / email and password do not match\
__Then__ an error is propagated back to the user\
__And__ the error message clearly explains the encountered issue\
__And__ the error message does not leak any information about the account's existence

### Any other internal error

__Given__ a user logs into the application\
__And__ the Gateway Service receives the request\
__And__ the Gateway calls the User Service\
__When__ the User Service validates the data\
__And__ an internal error is encountered\
__Then__ an error is propagated back to the user
__And__ the error message is a generic message (i.e. "Something went wrong")
