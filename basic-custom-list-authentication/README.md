# Basic Authentication using application provided user base

This module demonstrates how to achieve authentication in WildFly10 using BASIC auhtentication but with application provided users

## Steps

 - Create a web module that has protected and unprotected resources
 - Configure security constraint in `web.xml`
 - Create a file `users.properties` in the module
    * in `src/main/resources` folder
    * Edit the file and Add users in `username=password` format
 - Create a file `roles.properties` in the module
    * in `src/main/resources` folder
    * Edit the file and Add users in `username=role1,role2` format
 - Configure WildFly by adding a `<security-domain>`
    * Make sure WildFly is stopped
    * Open up the WildFly standalone config file you are using
    * Search for `<subsystem xmlns="urn:jboss:domain:security:1.2">` and add following

```xml

                <security-domain name="mySecureUsersDomain" cache-type="default">
                    <authentication>
                        <login-module code="UsersRoles" flag="required">
                            <module-option name="usersProperties" value="users.properties"/>
                            <module-option name="rolesProperties" value="roles.properties"/>
                        </login-module>
                    </authentication>
                </security-domain>
```

 - Configure the module to use the newly added `<security-domain>`
    * Create a new file `jboss-web.xml` along side `web.xml` in the module
    * Edit the file and add following content

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jboss-web xmlns="http://www.jboss.com/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.jboss.com/xml/ns/javaee http://www.jboss.org/j2ee/schema/jboss-web_10_0.xsd"
           version="10.0">

  <security-domain>mySecureUsersDomain</security-domain>

</jboss-web>
```
 - Start WildFly and deploy the application
