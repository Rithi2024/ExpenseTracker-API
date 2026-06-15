# ExpenseTracker API

**ExpenseTracker API** is a modern RESTful API for tracking personal and business expenses. Built with Spring Boot and SQL Server, it provides comprehensive expense management with categorization, filtering, and summary statistics.

## 🎯 Features

- 📊 **Expense Tracking** - Create, read, update, and delete expenses
- 💰 **Amount Tracking** - Track expense amounts with decimal precision
- 🏷️ **Categories** - Organize expenses by category (Food, Transport, Entertainment, etc.)
- 🔍 **Filtering** - Filter expenses by category or date range
- 📈 **Statistics** - Get expense summaries and totals by category
- 📅 **Date Tracking** - Track when expenses were made
- ✅ **Validation** - Input validation with helpful error messages
- 🔒 **SQL Server** - Enterprise-grade SQL Server database
- 📝 **RESTful Design** - Proper HTTP methods and status codes
- 🧪 **Tested** - Unit tests with JUnit 5 and Mockito

## 📋 Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Framework | Spring Boot | 2.7.15 |
| Language | Java | 11+ |
| Database | SQL Server | 2016+ |
| ORM | Hibernate/JPA | - |
| Testing | JUnit 5 | - |
| Build Tool | Maven | 3.6+ |

## 🚀 Quick Start

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- SQL Server 2016 or later (or SQL Server Express)

### Installation

#### 1. Database Setup

**Option A: Using SQL Server Management Studio (SSMS)**
- Open SSMS and connect to your SQL Server
- Open a new query window
- Copy and paste contents of `database_sqlserver.sql`
- Execute the script

**Option B: Using Command Line**
```bash
sqlcmd -S localhost -U sa -P YourPassword123 -i database_sqlserver.sql
```

#### 2. Configure Database Connection

Set environment variables or create `.env` file:

**Windows (Command Prompt)**
```cmd
set EXPENSE_DB_URL=jdbc:sqlserver://localhost:1433;databaseName=ExpenseDB
set EXPENSE_DB_USERNAME=sa
set EXPENSE_DB_PASSWORD=YourPassword123
```

**Windows (PowerShell)**
```powershell
$env:EXPENSE_DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=ExpenseDB"
$env:EXPENSE_DB_USERNAME = "sa"
$env:EXPENSE_DB_PASSWORD = "YourPassword123"
```

**Linux/Mac**
```bash
export EXPENSE_DB_URL=jdbc:sqlserver://localhost:1433;databaseName=ExpenseDB
export EXPENSE_DB_USERNAME=sa
export EXPENSE_DB_PASSWORD=YourPassword123
```

#### 3. Build and Run

```bash
# Build the project
mvn clean build

# Run tests
mvn test

# Run the application
mvn spring-boot:run
```

The API will start at [http://localhost:8080](http://localhost:8080)

## 📖 API Endpoints

### Get All Expenses
```http
GET /api/expenses
```

Response:
```json
[
  {
    "id": 1,
    "category": "Food",
    "amount": 25.50,
    "description": "Lunch at restaurant",
    "date": "2024-01-15T12:30:00",
    "createdAt": "2024-01-15T12:30:00"
  }
]
```

### Get Expense by ID
```http
GET /api/expenses/{id}
```

### Create Expense
```http
POST /api/expenses
Content-Type: application/json

{
  "category": "Food",
  "amount": 25.50,
  "description": "Lunch at restaurant",
  "date": "2024-01-15T12:30:00"
}
```

### Update Expense
```http
PUT /api/expenses/{id}
Content-Type: application/json

{
  "category": "Food",
  "amount": 30.00,
  "description": "Dinner at restaurant",
  "date": "2024-01-15T19:00:00"
}
```

### Delete Expense
```http
DELETE /api/expenses/{id}
```

### Get Expenses by Category
```http
GET /api/expenses/category/{category}
```

Example:
```http
GET /api/expenses/category/Food
```

### Get Total by Category
```http
GET /api/expenses/total/{category}
```

### Get Statistics Summary
```http
GET /api/expenses/stats/summary
```

Response:
```json
{
  "totalExpenses": 1250.00,
  "averageExpense": 156.25,
  "categorySummary": {
    "Food": 250.00,
    "Transport": 150.00,
    "Entertainment": 200.00,
    "Utilities": 650.00
  }
}
```

## 📁 Project Structure

```
ExpenseTracker-API/
├── src/main/java/com/example/
│   ├── controller/       # REST controllers
│   ├── model/           # JPA entities
│   ├── repository/      # Data access layer
│   ├── service/         # Business logic
│   ├── dto/             # Data transfer objects
│   ├── exception/       # Custom exceptions
│   └── ExpenseTrackerApplication.java
├── src/main/resources/
│   └── application.properties
├── pom.xml              # Maven configuration
├── database_sqlserver.sql  # SQL Server schema
└── README.md
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test Coverage
The project includes unit tests for:
- Expense model validation
- Service layer logic
- Repository queries
- Controller endpoints

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| **Cannot connect to database** | Verify SQL Server is running and credentials are correct |
| **Port 8080 already in use** | Change port: `mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"` |
| **Build fails with JDBC driver** | Run `mvn clean install` to download dependencies |
| **Database table not created** | Ensure `spring.jpa.hibernate.ddl-auto=update` is set |
| **Tests failing** | Check if SQL Server is running and test database is accessible |

## 📝 Environment Variables

```
EXPENSE_DB_URL           # SQL Server connection URL
EXPENSE_DB_USERNAME      # Database user (default: sa)
EXPENSE_DB_PASSWORD      # Database password
PORT                     # Server port (default: 8080)
```

## 🔐 Security Considerations

- Use HTTPS in production
- Use strong database passwords
- Don't commit credentials to version control
- Use environment variables for sensitive data
- Implement authentication/authorization for production
- Add rate limiting for API endpoints
- Validate all input data
- Use SQL prepared statements (automatic with JPA)

## 📊 Database Schema

### expenses table
- `id` - Primary key (auto-incrementing)
- `category` - Expense category
- `amount` - Expense amount (decimal)
- `description` - Expense description
- `date` - When the expense occurred
- `created_at` - Record creation timestamp

## 🚀 Production Deployment

### Docker Deployment

Create a `Dockerfile`:
```dockerfile
FROM openjdk:11-slim
COPY target/expense-tracker-api-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

Build and run:
```bash
mvn clean package
docker build -t expense-tracker-api .
docker run -e EXPENSE_DB_URL=... -p 8080:8080 expense-tracker-api
```

### Cloud Deployment (Azure, AWS, etc.)

1. Build application: `mvn clean package`
2. Deploy JAR to cloud platform
3. Configure environment variables for SQL Server connection
4. Set up SSL/TLS certificates
5. Configure firewall rules

## 📄 License

MIT - Feel free to use this project for personal or commercial purposes.

## 📞 Support

For issues and questions, please refer to the troubleshooting section above.

---

**Track Your Expenses Efficiently! 💰**
```

Create request example:

```json
{
  "category": "Food",
  "amount": 25.50,
  "description": "Lunch",
  "date": "2026-05-25T12:00:00"
}
```

## Project Files

```text
3-expense-api-springboot/
├── src/main/java/com/example/
├── src/main/resources/application.properties
├── src/test/java/com/example/service/ExpenseServiceTest.java
├── database.sql
├── Expense-Tracker-API.postman_collection.json
├── pom.xml
└── README.md
```

## Quality Checks

```bash
mvn clean test
```

## Notes

- Pagination now uses `PageRequest` instead of returning the full table.
- The controller delegates business logic to `ExpenseService`.
- Validation errors return field-level HTTP 400 responses.
- Runtime database settings live in `src/main/resources/application.properties`.
