<h4>This project is a simple CRUD (Create, Read, Update, Delete) application built 
with Spring Boot for the backend.</h4>

<h3>API documentation:</h3>
http://localhost:8080/swagger-ui/index.html






![api-documentation](https://github.com/user-attachments/assets/26d04685-f0ac-4624-a570-4668e1ce8693)



Get the source code:

Clone the repository:
git clone https://github.com/romeopuiu/birds-sighting-server.git

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


![get-birds](https://github.com/user-attachments/assets/590ca424-ccf9-409b-9d11-db355c60100d)


![save-bird](https://github.com/user-attachments/assets/76aedaf0-0f75-49a9-b3b3-da6cd58cec20)


![update-bird](https://github.com/user-attachments/assets/b68c11a1-53a5-4e57-82e7-7faed5ad5ccb)




![bird-by-color](https://github.com/user-attachments/assets/e2868ea4-2489-4508-95d2-4caee35c1dc2)



![get-by-name](https://github.com/user-attachments/assets/6eb5bc12-db31-4f86-b4ad-f181e8522038)



![delete-bird](https://github.com/user-attachments/assets/a08aac52-b4cd-4090-b0aa-a22fa5fe03a5)



![save-sigthing-by-bird-postman](https://github.com/user-attachments/assets/4d0dc8b2-f632-4a05-be09-bad33dab5261)



![update-sighting](https://github.com/user-attachments/assets/60e9eba6-e00d-44bf-833c-4ba9e8d6979f)



![get-sightings-by-bird](https://github.com/user-attachments/assets/824272d4-2434-4d50-b81a-a0aa7e2b458e)



![get-sightings](https://github.com/user-attachments/assets/e9b33a60-6e3c-4390-a9ba-ce26d6a19cd5)


![delete-sighting](https://github.com/user-attachments/assets/1637339c-2f7c-4bfd-b571-fe232f4af3b4)



UI React birds-sighting-client:
Can be open here :  https://github.com/romeopuiu/birds-sighting-client 

