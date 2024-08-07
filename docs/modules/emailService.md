# Email Service

Maven module name: __email-service__\
Owner: __to be decided__

## Description

The Email Service is an internal service of the Budget Tracker application responsible for
generating emails and sending them to users.\
It receives the email payload from the Notification Service, creates an email based on that payload and sends it to 
the user's email through SMTP.
