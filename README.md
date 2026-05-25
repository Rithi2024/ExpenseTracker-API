# Expense Tracker API - Spring Boot

A powerful RESTful API for managing personal expenses built with Spring Boot and MySQL.

## Features
- 💰 Create, read, update, and delete expenses
- 📊 Track expenses by category
- 📈 Get expense summaries
- 🔍 Filter expenses by date range
- 📱 RESTful API design
- 🛠️ JPA/Hibernate for database

## Requirements
- Java 11+
- Maven 3.6+
- MySQL 5.7+

## Installation

### 1. Clone and Setup
```bash
cd 3-expense-api-springboot
```

### 2. Database Setup
```bash
mysql -u root < database.sql
```

### 3. Configure Database
Edit `application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.url=jdbc:mysql://localhost:3306/expense_db
```

### 4. Build and Run
```bash
mvn clean build
mvn spring-boot:run
```

Server runs at: `http://localhost:8080`

## API Endpoints

### Get All Expenses
```
GET /api/expenses
```

### Create Expense
```
POST /api/expenses
Content-Type: application/json

{
  "category": "Food",
  "amount": 25.50,
  "description": "Lunch",
  "date": "2026-05-25T12:00:00"
}
```

### Get Expense by ID
```
GET /api/expenses/{id}
```

### Update Expense
```
PUT /api/expenses/{id}
Content-Type: application/json

{
  "category": "Food",
  "amount": 30.00,
  "description": "Updated lunch",
  "date": "2026-05-25T12:00:00"
}
```

### Delete Expense
```
DELETE /api/expenses/{id}
```

### Get Expenses by Category
```
GET /api/expenses/category/{category}
```

### Get Total by Category
```
GET /api/expenses/total/{category}
```

## Using Postman

1. Import `Expense-Tracker-API.postman_collection.json` into Postman
2. Test all endpoints
3. Monitor request/response data

## Project Structure
```
src/main/java/com/example/
├── ExpenseTrackerApplication.java
├── controller/
│   └── ExpenseController.java
├── model/
│   └── Expense.java
└── repository/
    └── ExpenseRepository.java
```

## Tech Stack
- **Backend**: Spring Boot 2.7.15
- **Database**: MySQL
- **ORM**: JPA/Hibernate
- **Build**: Maven
- **Language**: Java 11

## Git Setup
```bash
git init
git add .
git commit -m "Initial Spring Boot expense API"
git push origin main
```

## Testing with cURL
```bash
# Create expense
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{"category":"Food","amount":25.50,"description":"Lunch","date":"2026-05-25T12:00:00"}'

# Get all expenses
curl http://localhost:8080/api/expenses

# Get by category
curl http://localhost:8080/api/expenses/category/Food
```

## Production Deployment
- Use proper database credentials
- Enable Spring Security
- Add request validation
- Implement pagination
- Add API documentation with Swagger
