# Email Verification User Flows

## Happy Flows

### Verify email
__When__ a user uses the verification link to verify its account \
__And__ the Gateway Service receives the request\
__And__ the Gateway calls the User Service\
__And__ the User Service validates the data\
__And__ the username / email and verification token match\
__Then__ a successful verification message is propagated back to the user\
__And__ the verification link is invalidated
__And__ the User Service calls the Notification Service\
__And__ the Notification Service calls the Email Service\
__And__ an welcoming email is sent to the user

### Resend verification email
__When__ a user prompts the resending of its verification email \
__And__ the Gateway Service receives the request\
__And__ the Gateway calls the User Service\
__And__ the User Service validates the data\
__And__ a new verification link is generated for the user\
__And__ the old verification link is invalidated\
__And__ the User Service calls the Notification Service\
__And__ the Notification Service calls the Email Service\
__And__ a new verification email is sent to the email address\
__And__ the verification email contains the verification link for the account\
__And__ the verification link has a limited lifespan

## Negative Flows
