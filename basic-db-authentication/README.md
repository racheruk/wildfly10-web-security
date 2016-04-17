# Basic Authentication using user/roles in DataBase

This module demonstrates how to achieve authentication in WildFly10 using BASIC auhtentication but with user and role information stoed in DB

## Environment Used

    WildFly 10.0.0.Final
    MySQL 5.6.27

## Steps

 - Create a web module that has protected and unprotected resources
 - Configure security constraint in `web.xml`. Mine looks as follows
```xml
  <login-config>
    <auth-method>BASIC</auth-method>
    <realm-name>Authenticated User</realm-name>
  </login-config>

  <security-role>
    <role-name>valid-user</role-name>
  </security-role>

  <security-constraint>
    <web-resource-collection>
      <web-resource-name>all</web-resource-name>
      <url-pattern>/secured/*</url-pattern>
      <http-method>GET</http-method>
    </web-resource-collection>
    <auth-constraint>
      <role-name>valid-user</role-name>
    </auth-constraint>
  </security-constraint>

```
 - Create a datasource definition in standalone config file. Mine looks as follows
```xml
                <datasource jndi-name="java:jboss/datasources/EmployeesDS" pool-name="EmployeesDS" enabled="true" use-java-context="true">
                    <connection-url>jdbc:mysql://localhost:3306/employees</connection-url>
                    <driver>mysql</driver>
                    <security>
                        <user-name>root</user-name>
                        <password>root</password>
                    </security>
                </datasource>
                <drivers>
                    <driver name="mysql" module="com.mysql">
                        <driver-class>com.mysql.jdbc.Driver</driver-class>
                    </driver>
                </drivers>
```
 - Create two tables and the sample data in the database. Following is the script
```sql
create table app_role (id integer primary key, role varchar(128), role_group varchar(128), app_user integer);
create table app_user (id integer primary key, username varchar(128), password varchar(128));
insert into app_user (id, username, password) values (1, 'admin1', 'admin1');
insert into app_role (id, role, role_group, app_user) values (1, 'valid-user', 'Roles', 1);
```
 - Configure WildFly by adding a `<security-domain>`
    * Make sure WildFly is stopped
    * Open up the WildFly standalone config file you are using
    * Search for `<subsystem xmlns="urn:jboss:domain:security:1.2">` and add following

```xml
                <security-domain name="mySecureDatabaseUsersDomain" cache-type="default">
                    <authentication>
                        <login-module code="Database" flag="required">
                            <module-option name="dsJndiName" value="java:jboss/datasources/EmployeesDS"/>
                            <module-option name="rolesQuery" value="select ar.role, ar.role_group from app_role ar inner join app_user au on au.id = ar.app_user where au.username = ?"/>
                            <module-option name="principalsQuery" value="select password from app_user where username = ?"/>
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

  <security-domain>mySecureDatabaseUsersDomain</security-domain>

</jboss-web>
```
 - Start WildFly and deploy the application
 - Access `http://localhost:8080/basic-db-authentication/secured/secured.html`
 
## Resources
[AuthenticationModules](https://docs.jboss.org/author/display/WFLY8/Authentication+Modules)
