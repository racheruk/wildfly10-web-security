# Basic Authentication

This module deonstrates how to achieve authentication in WildFly10 using BASIC auhtentication.

## Steps

 - Create a web module that has protected and unprotected resources
 - Configure security constraint in `web.xml`
 - Create users and roles who will be accessing the protected resources
    * Add User in `Application Realm` using `<wildlfy10-intsall-dir>/add-user.sh`
    * Edit `<wildlfy10-intsall-dir>/standalone/configuration/application-roles.properties` and Add roles for the created users in 
 - Deploy the application
 
 ## Internals
 1. WildFly adds users in `<wildlfy10-intsall-dir>/standalone/configuration/application-users.properties` file. 
 2. It will save passwords in a one-way hash. More details can be found in the mentioend file itself
 3. Java code to convert the given username and password (useful if you want to hand edit the file)

```java
    String str = String.join("", userName, ":ApplicationRealm:", password);
    MessageDigest digest = MessageDigest.getInstance("MD5");
    String hash = String.format("%x", new BigInteger(1, digest.digest(str.getBytes())));
```
