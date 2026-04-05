# Zorvyn Internship Project

## Project Overview
This project is part of the Zorvyn internship program. It includes a financial management system with user authentication and financial record tracking.

## Project Structure
```
Zorvyn Internship/
├── README.md              # This file - project documentation
├── Mysql_Database.sql     # MySQL database schema
└── finance-dashboard/     # Spring Boot application
    ├── pom.xml           # Maven configuration
    ├── src/              # Source code
    │   ├── main/         # Main application code
    │   │   └── java/     # Java source files
    │   │       └── com/zorvyn/finance_dashboard/
    │   │           ├── FinanceDashboardApplication.java  # Main Spring Boot application
    │   │           ├── Entity/                          # JPA Entity classes
    │   │           │   ├── Users.java                   # User entity
    │   │           │   └── FinancialRecord.java         # Financial record entity
    │   │           ├── Enums/                           # Enum classes
    │   │           │   ├── Role.java                   # User role enumeration
    │   │           │   └── TransactionType.java         # Transaction type enumeration
    │   │           ├── Repository/                      # JPA Repository interfaces
    │   │           │   ├── UserRepository.java          # User data access layer
    │   │           │   │   └── Methods: findByEmail(String email)
    │   │           │   └── FinancialRecordRepository.java # Financial record data access layer
    │   │           │       └── Methods: 
    │   │           │           - findByCategory(String category)
    │   │           │           - findByType(TransactionType type) 
    │   │           │           - calculateTotalAmountByType(TransactionType type)
    │   │           ├── Service/                          # Business logic layer
    │   │           │   └── FinancialRecordService.java   # Financial record business logic
    │   │           │       └── Methods:
    │   │           │           - createRecord(FinancialRecord record, String userEmail)
    │   │           │           - getAllRecords()
    │   │           │           - calculateNetBalance()
    │   │           ├── Controller/                       # REST API endpoints
    │   │           │   └── FinancialRecordController.java # Financial record API endpoints
    │   │           │       └── Methods:
    │   │           │           - getAllRecords() [GET /api/records]
    │   │           │           - createRecord() [POST /api/records]
    │   │           │           - getNetBalance() [GET /api/summary/net-balance]
    │   │           ├── Security/                        # Spring Security configuration
    │   │           │   ├── CustomUserDetailsService.java # Custom user authentication service
    │   │           │   │   └── Methods: loadUserByUsername(String email)
    │   │           │   └── SecurityConfig.java           # Spring Security configuration
    │   │           │       └── Methods: securityFilterChain(HttpSecurity http), passwordEncoder()
    │   │           └── Config/                          # Configuration classes
    │   │               └── DataInitializer.java         # Database bootstrap data
    │   │                   └── Methods: run(String... args)
    │   └── test/         # Test code
    └── .gitignore        # Git ignore file
```

## Database Configuration

### Application Properties Setup
```properties
# ===============================
# 1. DATABASE CONNECTION (MySQL)
# ===============================
spring.datasource.url=jdbc:mysql://localhost:3306/zorvyn
spring.datasource.username=root
spring.datasource.password=root

# ===============================
# 2. HIBERNATE & JPA SETTINGS
# ===============================
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# ===============================
# 3. SERVER SETTINGS
# ===============================
server.port=8080
```

### Database Schema


### Users Table
- **id**: BIGINT AUTO_INCREMENT PRIMARY KEY
- **email**: VARCHAR(255) NOT NULL UNIQUE
- **password_hash**: VARCHAR(255) NOT NULL
- **role**: VARCHAR(50) NOT NULL (ROLE_VIEWER, ROLE_ANALYST, ROLE_ADMIN)
- **is_active**: BOOLEAN DEFAULT TRUE
- **created_at**: TIMESTAMP DEFAULT CURRENT_TIMESTAMP
- **updated_at**: TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

### Financial Records Table
- **id**: BIGINT AUTO_INCREMENT PRIMARY KEY
- **amount**: DECIMAL(15,2) NOT NULL
- **type**: VARCHAR(50) NOT NULL (INCOME or EXPENSE)
- **category**: VARCHAR(100) NOT NULL
- **record_data**: DATE NOT NULL
- **notes**: TEXT
- **created_by**: BIGINT (Foreign key to users.id)
- **created_at**: TIMESTAMP DEFAULT CURRENT_TIMESTAMP
- **updated_at**: TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

## Technologies & Tools
- **Backend Framework**: Spring Boot 4.0.5
- **Java Version**: Java 21
- **Database**: MySQL 8.0.43
- **ORM**: Spring Data JPA with Hibernate 7.2.7.Final
- **Security**: Spring Security
- **Validation**: Spring Boot Validation
- **API Documentation**: SpringDoc OpenAPI 3.0.2
- **Build Tool**: Maven
- **Code Generation**: Lombok (for getters/setters)
- **Connection Pool**: HikariCP
- **Schema**: Financial management system with user roles and record tracking

## Development Log
### April 2, 2026
- **10:00 AM UTC+05:30**: Project initialized with README.md file
- Initial project structure created
- **10:11 AM UTC+05:30**: Added MySQL database schema (Mysql_Database.sql)
- Database includes users table with role-based access and financial_records table for tracking income/expenses
- **10:17 AM UTC+05:30**: Enhanced README with technical notes section
- Formatted database design decisions with clear reasoning and limitations
- **10:28 AM UTC+05:30**: Added Spring Boot application (finance-dashboard)
- Spring Boot 4.0.5 with Java 21, Spring Security, JPA, and OpenAPI documentation
- **11:01 AM UTC+05:30**: Updated Spring Boot application structure
- Added Lombok dependency for code generation
- Created JPA Entity classes: Users.java (complete) and FinancialRecord.java (stub)
- Implemented proper entity mappings with Hibernate annotations
- **11:04 AM UTC+05:30**: Enhanced technical notes with additional design decisions
- Added foreign key naming convention documentation
- Documented Lombok annotation strategy (using @Getter/@Setter instead of @Data)
- **April 3, 2026**
- **10:41 AM UTC+05:30**: Completed FinancialRecord entity implementation
- Added BigDecimal for financial amounts with proper precision (15,2)
- Implemented @ManyToOne relationship with User entity using LAZY fetching
- Added proper JPA annotations for all database mappings
- **10:48 AM UTC+05:30**: Created enum classes for type safety
- Added Role enum with ROLE_VIEWER, ROLE_ANALYST, ROLE_ADMIN constants
- Added TransactionType enum with INCOME and EXPENSE constants
- Enums provide compile-time safety and prevent invalid values
- **10:53 AM UTC+05:30**: Applied @Enumerated(EnumType.STRING) annotation
- Prevents database corruption from enum ordinal changes
- Stores actual enum values in database for data integrity
- Updated both Users.role and FinancialRecord.type fields
- **11:20 AM UTC+05:30**: Added Spring Security implementation
- Created CustomUserDetailsService for user authentication
- Added UserRepository interface with findByEmail method
- Fixed Lombok boolean getter method issue (isActive() vs getIsActive())
- Integrated Spring Security with custom user details and role-based access
- **April 4, 2026**
- **5:57 PM UTC+05:30**: Database connection testing and schema validation
- Configured application.properties with MySQL connection settings
- Encountered schema validation error: missing column [record_date] in financial_records table
- Solution: Changed `spring.jpa.hibernate.ddl-auto=validate` to `update` for automatic schema synchronization
- **6:02 PM UTC+05:30**: Successful application startup and schema synchronization
- Spring Boot application started successfully on port 8080
- Hibernate automatically updated database schema:
  - Modified financial_records.category column to varchar(255)
  - Added missing financial_records.record_date column
- Database connection established with HikariCP connection pool
- Spring Security and OpenAPI endpoints initialized successfully
- **6:15 PM UTC+05:30**: Implemented DataInitializer for bootstrap setup
- Created DataInitializer class to automatically create default admin user
- Encountered PasswordEncoder dependency issue - requires Spring Security configuration
- Need to configure PasswordEncoder bean for password hashing
- Solution addresses chicken-and-egg problem for initial admin user creation
- **6:26 PM UTC+05:30**: Successfully resolved Security configuration
- Implemented clean SecurityConfig with BCryptPasswordEncoder bean
- Spring Boot auto-configuration resolved dependency injection
- DataInitializer successfully created default admin user
- Console output: "✅ Default Admin User created: admin@zorvyn.com / admin123"
- Application startup completed in 4.897 seconds
- **6:32 PM UTC+05:30**: Created FinancialRecordRepository
- Added custom query methods for dashboard analytics
- Implemented findByCategory and findByType methods
- Added calculateTotalAmountByType for financial summaries
- Ready for Phase 3: Core APIs development
- **April 5, 2026**
- **9:26 AM UTC+05:30**: Verified Repository implementation
- Confirmed FinancialRecordRepository.java exists and properly implemented
- Contains three query methods for dashboard functionality
- Missing @Repository annotation (interface works without it due to Spring Data JPA)
- **9:32 AM UTC+05:30**: Added Service layer implementation
- Created FinancialRecordService.java with business logic
- Implemented createRecord() method with user association
- Added getAllRecords() method for data retrieval
- Implemented calculateNetBalance() for dashboard analytics
- Service layer bridges Repository and future Controller layers
- **9:57 AM UTC+05:30**: Implemented Controller layer and API endpoints
- Created FinancialRecordController.java with REST endpoints
- Implemented GET /api/records for retrieving all records
- Added POST /api/records for creating new records
- Created GET /api/summary/net-balance for dashboard analytics
- Encountered 500 Internal Server Error during API testing
- **Issue**: JSON serialization crash with lazy-loaded User objects
- **Root Cause**: Spring Boot trying to serialize Users object with @JsonIgnore missing
- **Resolution**: Added @JsonIgnore annotation to FinancialRecord.createdBy field
- **Result**: Prevents security leaks and serialization crashes
- **10:05 AM UTC+05:30**: Database schema issue resolution and successful API testing
- Encountered database error: "Field 'record_data' doesn't have a default value"
- **Root Cause**: Ghost column 'record_data' from previous development attempts
- **Resolution**: Changed ddl-auto from 'update' to 'create' to rebuild clean database
- **Result**: Successfully created first financial record via POST /api/records
- **API Success**: Received 201 Created response with proper JSON structure
- **Verification**: @JsonIgnore working correctly, no user data exposed in responses
- **10:13 AM UTC+05:30**: Complete API functionality verification
- Successfully tested GET /api/records returning 200 OK with JSON array
- Verified GET /api/summary/net-balance returning correct calculation (100000)
- Confirmed all three endpoints working: POST (create), GET (retrieve), Summary (calculate)
- **Project Status**: Core requirements fully implemented and tested
- **Architecture**: Complete three-tier architecture (Controller-Service-Repository)
- **Security**: Role-based access control with Spring Security working correctly
- **Database**: MySQL integration with Hibernate ORM functioning properly

## Getting Started

### Prerequisites
- Java 21 or higher
- MySQL 8.0.43 or higher
- Maven 3.6 or higher

### Database Setup
1. Create a MySQL database named `zorvyn`
2. Update `application.properties` with your MySQL credentials if needed
3. The application will automatically create/update tables on startup

### Running the Application
1. Clone the repository and navigate to the `finance-dashboard` directory
2. Run the application: `mvn spring-boot:run`
3. Access Swagger UI at: `http://localhost:8080/swagger-ui/index.html`

### Default Admin User
The application automatically creates a default admin user for testing:
- **Email**: admin@zorvyn.com
- **Password**: admin123

### API Endpoints
- **POST /api/records** - Create new financial record (Admin only)
- **GET /api/records** - Get all financial records (Viewer, Analyst, Admin)
- **GET /api/summary/net-balance** - Calculate net balance (Analyst, Admin)

### Sample Request Payload
To create a new record via `POST /api/records`, use the following JSON structure:
```json
{
  "amount": 100000.00,
  "type": "INCOME",
  "category": "BUSINESS",
  "recordDate": "2026-04-05",
  "notes": "Initial business profit"
}
```

### Security
- All endpoints require HTTP Basic Authentication
- Role-based access control implemented
- Admin users can create/manage records
- Viewer/Analyst users have read-only access

## Contributing
*Contributing guidelines will be added as the project develops.*

## Technical Notes & Decisions

### Database Design Choices

#### 1. Timestamp vs DateTime
- **Choice**: Used `TIMESTAMP` instead of `DATETIME` for time fields
- **Reasoning**: 
  - Timezone aware - converts local time to UTC for storage and back to client's local time upon retrieval
  - Ensures consistency for users accessing from different geographical locations
- **Known Limitation**: Will malfunction in January 2038 due to Unix timestamp limitations
- **Note**: Uses localtime for conversion

#### 2. Primary Key Naming Convention
- **Choice**: Used `id` as primary key in both tables
- **Reasoning**: Industry standard practice
- **Usage**: Referenced as `users.id` and `financial_records.id` for clarity

#### 3. Foreign Key Naming Convention
- **Choice**: Used `created_by` as foreign key in financial_records table
- **Reasoning**: Clear and descriptive name indicating the user who created the record
- **Usage**: Referenced as `financial_records.created_by` for clarity

#### 4. Lombok Annotations Strategy
- **Choice**: Used `@Getter` and `@Setter` instead of `@Data`
- **Reasoning**: 
  - `@Data` generates too many methods that are not needed
  - `toString()` method can cause infinite recursion with JPA entities
  - `@Getter` and `@Setter` provide only essential boilerplate reduction
- **Benefits**: Cleaner code with only necessary generated methods

#### 5. Financial Data Type Selection
- **Choice**: Used `BigDecimal` for money transactions instead of `double`
- **Reasoning**: 
  - `BigDecimal` provides precise decimal arithmetic without rounding errors
  - `double` uses floating-point arithmetic which can cause precision loss
  - Financial calculations require exact precision for monetary values
- **Benefits**: Accurate financial calculations and proper money handling

#### 6. Lazy Loading Strategy
- **Choice**: Used `LAZY` fetching for `@ManyToOne` relationship in FinancialRecord
- **Reasoning**: 
  - Reduces database load by only fetching related User data when explicitly needed
  - Improves performance by avoiding unnecessary JOIN operations
  - Prevents circular reference issues that can cause infinite recursion
- **Benefits**: Better resource utilization and cleaner data retrieval

#### 7. Type Safety with Enums
- **Choice**: Used Java enums for Role and TransactionType instead of String constants
- **Reasoning**: 
  - Provides compile-time type safety and prevents invalid values
  - Eliminates string comparison errors and typos
  - Improves code readability and maintainability
  - Enables IDE auto-completion and refactoring support
- **Benefits**: Robust data validation and cleaner, more reliable code

#### 8. Enum Persistence Strategy
- **Choice**: Used `@Enumerated(EnumType.STRING)` for enum fields
- **Reasoning**: 
  - Prevents database corruption when enum order changes
  - Stores actual enum values ("ROLE_ADMIN") instead of ordinal numbers
  - Makes database data human-readable and safe from enum reordering
- **Benefits**: Database stability and data integrity

#### 9. Lombok Boolean Getter Convention
- **Issue**: Compilation error with `getIsActive()` method call
- **Root Cause**: Lombok generates `isActive()` for primitive boolean fields, not `getIsActive()`
- **Resolution**: Use `user.isActive()` instead of `user.getIsActive()`
- **Lesson**: Lombok follows JavaBean conventions for boolean getters

#### 10. Hibernate Schema Management Strategy
- **Issue**: Schema validation error - missing column [record_date] in financial_records table
- **Root Cause**: Database schema doesn't match JPA entity field mappings
- **Resolution**: Changed `spring.jpa.hibernate.ddl-auto` from `validate` to `update`
- **Result**: Successful automatic schema synchronization
  - Modified category column: varchar(100) → varchar(255)
  - Added missing record_date column with DATE type
- **Benefits**: Automatic schema synchronization during development
- **Performance**: Application startup completed in 4.863 seconds

#### 11. Bootstrap Data Initialization Strategy
- **Problem**: Chicken-and-egg issue - need admin user to create users, but need admin to create admin
- **Solution**: DataInitializer implements CommandLineRunner for automatic bootstrap
- **Implementation**: Creates default admin user when database is empty
- **Credentials**: admin@zorvyn.com / admin123 (password hashed with BCrypt)
- **Resolution**: Spring Boot auto-configuration resolved dependency injection
- **Result**: Successfully created default admin user on application startup

#### 12. Spring Security Configuration Strategy
- **Problem**: IDE confusion with DaoAuthenticationProvider and missing PasswordEncoder bean
- **Solution**: Simplified SecurityConfig using Spring Boot auto-configuration
- **Implementation**: 
  - Removed manual DaoAuthenticationProvider setup
  - Added BCryptPasswordEncoder @Bean method
  - Spring Boot automatically wires UserDetailsService and PasswordEncoder
- **Benefits**: Cleaner code, less configuration, automatic dependency resolution
- **Performance**: Application startup in 4.897 seconds

#### 13. Repository Query Strategy
- **Choice**: Used Spring Data JPA method naming conventions
- **Implementation**: FinancialRecordRepository with custom query methods
- **Features**: 
  - findByCategory(String category) - Filter by category
  - findByType(TransactionType type) - Filter by transaction type
  - calculateTotalAmountByType(TransactionType type) - Dashboard analytics
- **Benefits**: Automatic SQL generation, type-safe queries, dashboard-ready

#### 14. JSON Serialization Strategy
- **Problem**: 500 Internal Server Error during API testing
- **Root Cause**: JSON serialization crash with lazy-loaded User relationships
- **Issue**: Spring Boot attempting to serialize Users object in FinancialRecord responses
- **Security Risk**: Potential exposure of hashed passwords in API responses
- **Solution**: Added @JsonIgnore annotation to FinancialRecord.createdBy field
- **Benefits**: 
  - Prevents serialization crashes
  - Enhances security by hiding sensitive user data
  - Maintains clean API responses
- **Result**: Successfully resolved 500 errors in POST /api/records endpoint

#### 15. Database Schema Management Strategy
- **Problem**: MySQL error "Field 'record_data' doesn't have a default value"
- **Root Cause**: Ghost column from previous development attempts
- **Issue**: Spring Boot's ddl-auto=update never deletes columns for safety
- **Resolution**: Changed ddl-auto to 'create' to rebuild clean database schema
- **Process**: 
  1. Temporarily changed ddl-auto to 'create'
  2. Restarted application to rebuild tables
  3. Changed back to 'update' for ongoing development
- **Result**: Clean database schema with proper column structure
- **Success**: POST /api/records returns 201 Created with proper JSON response

## Project Notes
- This README will be continuously updated as the project progresses
- Last updated: April 5, 2026 at 10:13 AM UTC+05:30


