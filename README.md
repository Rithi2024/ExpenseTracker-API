# 💰 Expense Tracker API - Spring Boot

![Java](https://img.shields.io/badge/Java-11+-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.15-green?style=flat-square&logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-5.7+-blue?style=flat-square&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.6+-red?style=flat-square&logo=apache-maven)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

A professional-grade RESTful API for managing personal and business expenses. Built with Spring Boot 2.7, it provides comprehensive expense tracking with real-time analytics and category-based organization.

## ✨ Features

### Core Functionality
- ✅ **CRUD Operations** - Create, read, update, delete expenses
- 📊 **Expense Analytics** - Get statistics and summaries
- 🏷️ **Category Management** - Organize expenses by category
- 📈 **Total Calculations** - Calculate totals by category
- 🔍 **Filtering** - Filter expenses by date range and category
- 📄 **Pagination** - Efficient data retrieval for large datasets

### Technical Features
- 🛡️ **Global Exception Handling** - Comprehensive error responses
- ✔️ **Input Validation** - Automatic validation with Bean Validation
- 📝 **Comprehensive Logging** - Track all operations
- 🔀 **CORS Enabled** - Cross-origin requests supported
- 📄 **Postman Collection** - Pre-configured API testing
- 💾 **JPA/Hibernate ORM** - Database abstraction layer

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| **Framework** | Spring Boot 2.7.15 |
| **Language** | Java 11+ |
| **Database** | MySQL 5.7+ |
| **ORM** | JPA/Hibernate |
| **Build Tool** | Maven 3.6+ |
| **Testing** | JUnit 5 (ready to implement) |
| **API Documentation** | Postman Collection |

## 📋 Prerequisites

- **Java 11** or higher
- **Maven 3.6** or higher
- **MySQL 5.7** or higher
- **Postman** (for API testing)

## 🚀 Quick Start

### 1. Clone Repository
```bash
git clone https://github.com/Rithi2024/expense-api-springboot.git
cd expense-api-springboot
```

### 2. Database Setup

Create database and tables:
```bash
mysql -u root -p < database.sql
```

When prompted, enter your MySQL password.

### 3. Configure Application

Edit `application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.url=jdbc:mysql://localhost:3306/expense_db?useSSL=false&serverTimezone=UTC
```

### 4. Build Application
```bash
mvn clean build
```

### 5. Run Application
```bash
mvn spring-boot:run
```

Server starts at `http://localhost:8080`

## 📁 Project Structure

```
expense-api-springboot/
├── src/main/java/com/example/
│   ├── ExpenseTrackerApplication.java      # Application entry point
│   ├── controller/
│   │   └── ExpenseController.java          # REST endpoints
│   ├── model/
│   │   └── Expense.java                    # JPA entity
│   ├── repository/
│   │   └── ExpenseRepository.java          # Data access layer
│   ├── service/
│   │   └── ExpenseService.java             # Business logic
│   ├── dto/
│   │   └── ExpenseDTO.java                 # Data transfer object
│   └── exception/
│       ├── ResourceNotFoundException.java   # Custom exception
│       └── GlobalExceptionHandler.java     # Exception handler
├── src/main/resources/
│   └── application.properties              # Configuration
├── pom.xml                                 # Maven dependencies
├── Expense-Tracker-API.postman_collection.json
└── database.sql
```

## 📡 API Endpoints

### Get All Expenses
```http
GET /api/expenses
```

**Response:**
```json
{
  "totalExpenses": 5,
  "page": 0,
  "size": 10,
  "totalAmount": 150.50,
  "data": [...]
}
```

### Get Expense by ID
```http
GET /api/expenses/{id}
```

**Response:**
```json
{
  "id": 1,
  "category": "Food",
  "amount": 25.50,
  "description": "Lunch",
  "date": "2026-05-25T12:00:00",
  "createdAt": "2026-05-25T10:00:00"
}
```

### Create Expense
```http
POST /api/expenses
Content-Type: application/json

{
  "category": "Food",
  "amount": 25.50,
  "description": "Lunch at restaurant",
  "date": "2026-05-25T12:00:00"
}
```

**Response:** 201 Created
```json
{
  "message": "Expense created successfully",
  "data": {...},
  "timestamp": "2026-05-25T10:00:00"
}
```

### Update Expense
```http
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
```http
DELETE /api/expenses/{id}
```

**Response:**
```json
{
  "message": "Expense deleted successfully"
}
```

### Get Expenses by Category
```http
GET /api/expenses/category/{category}
```

**Response:**
```json
{
  "category": "Food",
  "count": 5,
  "total": 125.50,
  "data": [...]
}
```

### Get Total by Category
```http
GET /api/expenses/total/{category}
```

**Response:**
```json
{
  "category": "Food",
  "total": 125.50,
  "expenseCount": 5
}
```

### Get Expense Statistics
```http
GET /api/expenses/stats/summary
```

**Response:**
```json
{
  "totalExpenses": 15,
  "totalAmount": 500.00,
  "averageExpense": 33.33,
  "highestExpense": 100.00
}
```

## 🧪 Testing with Postman

### Import Collection
1. Open Postman
2. Click "Import"
3. Select `Expense-Tracker-API.postman_collection.json`
4. All endpoints are ready to test!

### Manual Testing with cURL

```bash
# Create expense
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "category":"Food",
    "amount":25.50,
    "description":"Lunch",
    "date":"2026-05-25T12:00:00"
  }'

# Get all expenses
curl http://localhost:8080/api/expenses

# Get by ID
curl http://localhost:8080/api/expenses/1

# Get by category
curl http://localhost:8080/api/expenses/category/Food

# Get statistics
curl http://localhost:8080/api/expenses/stats/summary
```

## 🗄️ Database Schema

### Expenses Table
```sql
CREATE TABLE expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255),
    date DATETIME NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX (category),
    INDEX (date)
);
```

## 🔧 Configuration

### Application Properties
```properties
# Server
server.port=8080
spring.application.name=Expense Tracker API

# Database
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.root=INFO
logging.level.com.example=DEBUG
```

## 📝 Entity Model

```java
@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private Double amount;
    
    private String description;
    
    @Column(nullable = false)
    private LocalDateTime date;
    
    private LocalDateTime createdAt;
}
```

## 🚀 Deployment

### Deploy to Heroku
```bash
# Create Heroku app
heroku create your-app-name

# Deploy
git push heroku main

# Check logs
heroku logs --tail
```

### Deploy to AWS
1. Package as JAR: `mvn clean package`
2. Upload to AWS Elastic Beanstalk
3. Configure RDS MySQL database
4. Deploy application

### Deploy with Docker
```dockerfile
FROM openjdk:11-jre-slim
COPY target/expense-api.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
docker build -t expense-api .
docker run -p 8080:8080 expense-api
```

## 🔐 Security Considerations

### Production Checklist
- ✅ Use environment variables for sensitive config
- ✅ Enable HTTPS/SSL
- ✅ Implement authentication (Spring Security)
- ✅ Add rate limiting
- ✅ Use prepared statements (default with JPA)
- ✅ Input validation on all endpoints
- ✅ CORS restrictions

### Add Spring Security
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

## 🧩 Future Enhancements

- [ ] User authentication
- [ ] User-specific expenses
- [ ] Budget alerts
- [ ] CSV export functionality
- [ ] Monthly/yearly reports
- [ ] Recurring expenses
- [ ] Multiple currencies
- [ ] Email notifications
- [ ] Swagger/OpenAPI documentation
- [ ] Unit tests with JUnit
- [ ] Integration tests
- [ ] Performance metrics

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| MySQL connection failed | Verify database credentials in `application.properties` |
| Port 8080 already in use | Change `server.port` or kill process |
| Build fails | Run `mvn clean install` |
| Table not found | Ensure `database.sql` was imported |
| API returns 500 error | Check application logs for details |

## 📊 Performance Optimization

- ✅ Database indexing on category and date
- ✅ Pagination for large datasets
- ✅ Query optimization with JPA
- ✅ Connection pooling
- ✅ Lazy loading for relationships

## 🔗 Useful Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [JPA/Hibernate Guide](https://hibernate.org/orm/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [RESTful API Best Practices](https://restfulapi.net/)
- [Java Tutorials](https://docs.oracle.com/en/java/)

## 📝 Git Workflow

```bash
# Create feature branch
git checkout -b feature/add-reports

# Make changes
git add .
git commit -m "Add expense reports"

# Push to GitHub
git push origin feature/add-reports
```

## 🤝 Contributing

Contributions welcome!

1. Fork repository
2. Create feature branch
3. Make changes
4. Write tests
5. Submit Pull Request

## 📄 License

MIT License - See LICENSE file

## 🙏 Acknowledgments

- Built with Spring Boot
- Powered by MySQL
- Inspired by financial tracking applications

## 📞 Support

For questions or issues:
- Open GitHub issue
- Check documentation
- Review sample data

---

**Start tracking expenses efficiently! 💰**

Last Updated: May 2026

