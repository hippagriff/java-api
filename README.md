To execute this application locally using tomcat:

##With a local MariaDB connection: 
**mvn clean -U tomcat7:run -Dspring.profiles.active=local**

##With an in-memory HSQL implementation: 

**mvn clean -U tomcat7:run -Dspring.profiles.active=test**
