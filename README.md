# 📙Online Book Store
Welcome to the Online Book Store project! This application is designed to provide a seamless experience 
for users to browse, purchase, and manage books, while also offering administrative functionalities for managers. 
This project was developed using Spring Boot and includes various functionalities for both shoppers and managers.

## 📅 Table of Contents

- [Project Description](#project-description)
- [Technologies Used](#technologies-used)
- [Features](#features)
  - [User](#user)
  - [Admin](#admin)
- [Setup Instructions](#setup-instructions)
- [Postman Collection](#postman-collection)
- [Functionality](#functionality)
- [Author](#author)

## ⭐ Project Description

The Online Book Store is a web application that allows users to browse and purchase books online. 
Users can search for books, view book details, add books to their shopping cart, and complete purchases. 
Admin users can manage the inventory, organize bookshelf sections, and update order statuses. 
This project aims to simulate a real-world online book store and demonstrate the capabilities of a Spring Boot application.

## ⚙️ Technologies Used

- **Spring Boot**: Core framework for the application
- **Spring Security**: For authentication and authorization
- **Spring Data JPA**: For database interactions
- **Swagger**: API documentation
- **Lombok**: Reducing boilerplate code
- **Maven**: Dependency management
- **Checkstyle**: Code style enforcement
- **Liquibase**: Database management
- **Mockito**: Mocking framework for tests
- **MySQL**: Database management system
- **JUnit**: Testing framework
- **Docker**: Deployment and scalability
- **Testcontainers**: Integration testing with Docker containers
- **CI/CD**: Continuous Integration setup with GitHub Actions

## ⛓ Features

### 🖥️ User

- **Join and Sign In**: Users can register and log in to the application.
- **Browse Books**: View all books, search by name, and view details of a specific book.
- **Shopping Cart**: Add books to the cart, view the cart, and remove books from the cart.
- **Purchase Books**: Complete the purchase of books in the cart.
- **Order History**: View past orders and their details.

### 🤖 Admin

- **Manage Books**: Add, update, and remove books from the store.
- **Organize Sections**: Create, update, and delete bookshelf sections.
- **Manage Orders**: View and update the status of orders.

## Setup Instructions
### Installation without Docker
1. **Clone the repository**
    ```bash
    git clone https://github.com/Recdt/Books-Store.git
    cd Books-Store
    ```
2. **Set up MySQL**  
Establish a new MySQL database, recording its URL, username, and password for future reference.  

3. **Configure environment variables**
   ```bash
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update
   server.servlet.context-path=/api

   jwt.expiration=token_expiration_time
   jwt.secret=your_secret_key
   ```
4. **Get set up**  
Run the following command to install any required dependencies and build your project:
```bash
  mvn clean install
```
5. **Start the server**
   Once the build is complete, use this command to run your application:
 ```bash
  mvn spring-boot:run
 ```  
Your server accessible at http://localhost:8080.

### Installation with Docker
1. **Clone the repository**
    ```bash
    git clone https://github.com/Recdt/Books-Store.git
    cd Books-Store
    ```
2. **Configure environment variables**
    ```bash
    MYSQLDB_DATABASE=your_db_name
    MYSQLDB_USER=your_db_user
    MYSQLDB_PASSWORD=your_dbpassword
    JWT_SECRET= your_secret_key
    JWT_EXPIRATION_TIME=your_expiration_time
    
    MYSQLDB_LOCAL_PORT=3306
    MYSQLDB_DOCKER_PORT=3306
    
    SPRING_LOCAL_PORT=8080
    SPRING_DOCKER_PORT=8080
    DEBUG_PORT=5005
    ```
3. **Build and run the Docker containers**
   ```bash
   docker-compose up
   ```
## **🔧 Testing**  
Using the following command you can run tests:  
```bash
mvn test
```
Page Glide ensures the reliability and maintainability of its code by employing Mockito for mocking dependencies and JUnit for unit testing the application logic.

## ⌨️ Postman collection  
[![Run in Postman](https://run.pstmn.io/button.svg)]([https://www.postman.com/collections/your-collection-link](https://www.postman.com/spaceflight-engineer-94981780/workspace/bookapi/collection/34453495-4e04225c-5379-4cde-b44b-e6a5f137f5b3?action=share&creator=34453495))
## Functionality
To access endpoints with required ADMIN role, you can use the next credentials:
```bash
{
  "email": "bob@example.com",
  "password": "12345678"
}
```
### Authentication Controller
- **Registering a new user:**  
  `POST: /auth/register`
  
- **Login user:**  
  `POST: /auth/login`

### Book Controller
- **Get all books:**  
  `GET: /books`
  
- **Get a book by ID:**  
  `GET: /books/{id}`
  
- **Get books by search parameters:**  
  `GET: /books/search`
  
- **Create a new book:**  
  `POST: /books` `{ADMIN}`
  
- **Update a book by ID:**  
  `PUT: /books/{id}` `{ADMIN}`
  
- **Delete a book by ID:**  
  `DELETE: /api/books/{id}` `{ADMIN}`

### Category Controller
- **Get all categories:**  
  `GET: /categories`
  
- **Get a category by ID:**  
  `GET: /categories/{id}`
  
- **Get books by category ID:**  
  `GET: /categories/{id}/books`
  
- **Create a new category:**  
  `POST: /categories` `{ADMIN}`
  
- **Update a category by ID:**  
  `PUT: /categories/{id}` `{ADMIN}`
  
- **Delete a category by ID:**  
  `DELETE: /api/categories/{id}` `{ADMIN}`

### Shopping Cart Controller
- **Get a shopping cart of a user:**  
  `GET: /cart` `{USER}`
  
- **Add a book to the shopping cart:**  
  `POST: /cart` `{USER}`
  
- **Update a book by cart item ID:**  
  `PUT: /cart/cart-items/{cartItemId}` `{USER}`
  
- **Delete a book by cart item ID:**  
  `DELETE: /cart/cart-items/{cartItemId}` `{USER}`

### Order Controller
- **Get all orders of the user:**  
  `GET: /orders` `{USER}`
  
- **Place an order:**  
  `POST: /orders` `{USER}`
  
- **Get all books for an order:**  
  `GET: /orders/{orderId}/items` `{USER}`
  
- **Get a book from the order by ID:**  
  `GET: /orders/{orderId}/items/{itemId}` `{USER}`
  
- **Update order status:**  
  `PATCH: /orders/{id}` `{ADMIN}`
## 👨‍💻 Author
- **[LinkedIn](www.linkedin.com/in/chopyk-mykola)**
- **[GitHub](https://github.com/Recdt)**
  
