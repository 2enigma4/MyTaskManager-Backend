### Tools and Technologies

1. IntelliJ IDEA
2. MySQL
3. Java 17
4. Spring Boot
5. Junit and Mockito
6. JPA
7. Swagger

### Clone Project

git clone https://github.com/2enigma4/MyTaskManager-Backend.git

### Steps to connect with MySQL Locally

1. Open Application.properties under resource folder
   
3. Update following properties with your mysql username and password:
   
    spring.datasource.username=<your_username>
    
    spring.datasource.password=<your_password>
    
5. Run the below Script in your mysql database:
   
    CREATE TABLE tasks (
      id BIGINT PRIMARY KEY,
      title VARCHAR(255) NOT NULL,
      description VARCHAR(255) NOT NULL
    );

### Port

Project will by default run on local port 8080. 
http://localhost:8080/

### Swagger URL 

To test the API's:
http://localhost:8080/swagger-ui/index.html#/task-controller



