# Database

## How To

### Migrate a Local Database

Run the Maven command `mvn flyway:migrate` from the `database` module:
1. `cd database`
2. `mvn flyway:migrate`

Running this command from any other directory will result in an error.

### Migrate a Docker Database

Databases created via the docker-compose file are automatically migrated using the latest schema.