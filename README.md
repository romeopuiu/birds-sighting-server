<h4>CRUD Application using React and Spring Boot</h4>

<h3>This project is a simple CRUD (Create, Read, Update, Delete) application built 
with React for the frontend and Spring Boot for the backend.</h3>

Service supports CRUD operations for birds and sightings
<h3>API documentation:</h3>
http://localhost:8080/swagger-ui/index.html

Get the source code:

Clone the repository:
git clone https://github.com/romeopuiu/crud-birds-sighting

<b>Prerequisites:</b>
* Spring Boot 2.7.17
* Java 11.0.13
* Maven
* PostgreSQL Database
* Lombok to generate boilerplate code

Spring Boot birds-sighting-server:

1. Navigate to the birds-sighting-server directory:
   cd birds-sighting-server

2. Build the app:
   mvn clean install
3. Run Spring Boot birds-sighting-server:
   mvn spring-boot:run


<h4>PostgreSQL Database</h4>
1. Modify application.yml:
spring:
   application:
   name: birds-sighting-server
   datasource:
   driver-class-name: org.postgresql.Driver
   url: jdbc:postgresql://localhost:5432/YOUR_DATABASE_NAME
   username: YOUR_DATABASE_USERNAME
   password: YOUR_DATABASE_PASSWORD

jpa:
properties:
hibernate:
dialect: org.hibernate.dialect.PostgreSQLDialect
show-sql: true
generate-ddl: true


<h5>Using Docker compose to run Spring Boot birds-sighting-server:</h5>
Before using docker compose have to modify file docker-compose
at line :  SPRING_DATASOURCE_PASSWORD: YOUR_DATABASE_PASSWORD

 And then run the command :

  docker compose up

<h5>Stop the Spring Boot birds-sighting-server application:</h5>
  docker compose down

<h5>Testing Spring Boot birds-sighting-server application  using Postman Tool:</h5>
After starting the application open Postman:

UI React birds-sighting-client:
Can be open here :  https://github.com/romeopuiu/birds-sighting-client 
