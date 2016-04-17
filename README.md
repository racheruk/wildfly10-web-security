# wildfly10-web-security

From my experience, it was never easy to work with WildFly in a little bit involved configuration areas. This applies to authentication too.

Each module stands complete in itself. All the modules are tested using `WildFly 10.0.0.Final` but they should be good enough in some of the older versions too i.e. WildFly 8 and 9.

## Modules
Brief introduction to the modules
### basic-authentication
 - Holds both secured and unsecured resources
 - Relies on jboss users i.e. added using <wildlfy>/bin/add-users.sh

This is the simplest way and mostly for demo purposes. It is very unlikely to have an application rely on users added via wildfly utilities.

### basic-custom-list-authentication
 - Holds both secured and unsecured resources
 - Relies on the users and roles provided as part of the application itself
 - Requires WildFly configuration (included in module's README.md

This is kind of a starting point where the application itself can provide the list of users and roles that it want to use.
