# POS System - Spring Boot Application

A complete Point of Sale (POS) system for managing coffee shop operations built with Spring Boot, Thymeleaf, Bootstrap, and H2 Database.

## 🎯 Features

### For Cashier (Sales Staff)
- **Order Management**: View product menu organized by categories
- **Customize Items**: Select sizes (S, M, L), add notes (toppings, sugar level, ice amount)
- **Table Management**: View table status (Empty, Occupied, Reserved)
- **Quick Checkout**: Calculate totals with VAT and discounts
- **Payment Methods**: Support for cash, bank transfer, and QR code
- **Receipt Printing**: Print or export invoices

### For Admin/Manager
- **Product Management**: Add, edit, delete products with images and descriptions
- **Category Management**: Organize products into categories
- **User/Staff Management**: Create accounts with role-based access control
- **Table Management**: Configure and manage service tables
- **Revenue Reports**: Daily, weekly, monthly sales analytics
- **Best-sellers Report**: Track most popular items

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Frontend**: Thymeleaf + Bootstrap 5
- **Database**: H2 (in-memory)
- **Security**: Spring Security with BCrypt password encoding
- **Build Tool**: Maven

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Internet connection (for Maven dependencies)

## 🚀 Installation & Running

### 1. Clone the repository
```bash
cd G:\Workspace\pos2
```

### 2. Build the project
```bash
mvn clean install
```

### 3. Run the application
```bash
mvn spring-boot:run
```

Or if you have the JAR built:
```bash
java -jar target/pos-system-1.0.0.jar
```

### 4. Access the application

Open your browser and navigate to:
- **Main Application**: http://localhost:8080
- **H2 Database Console**: http://localhost:8080/h2-console

## 📝 Default Login Credentials

### Admin Account
- **Username**: admin
- **Password**: admin123

### Cashier Account
- **Username**: cashier
- **Password**: cashier123

## 📁 Project Structure

```
pos2/
├── src/main/java/com/pos/
│   ├── PosApplication.java                    # Main application class
│   ├── entity/                                # JPA Entity classes
│   │   ├── Role.java
│   │   ├── User.java
│   │   ├── Category.java
│   │   ├── Product.java
│   │   ├── PosTable.java
│   │   ├── Order.java
│   │   └── OrderDetail.java
│   ├── repository/                            # Spring Data JPA Repositories
│   ├── service/                               # Business logic services
│   ├── controller/                            # MVC Controllers
│   ├── config/                                # Configuration classes
│   │   ├── SecurityConfig.java
│   │   ├── UserDetailsServiceImpl.java
│   │   └── DataInitializer.java
│   └── dto/                                   # Data Transfer Objects (if needed)
├── src/main/resources/
│   ├── templates/                             # Thymeleaf templates
│   │   ├── layout.html
│   │   ├── dashboard.html
│   │   ├── login.html
│   │   ├── order/
│   │   │   ├── list.html
│   │   │   ├── detail.html
│   │   │   ├── checkout.html
│   │   │   └── receipt.html
│   │   └── admin/
│   │       ├── products.html
│   │       ├── categories.html
│   │       ├── users.html
│   │       ├── tables.html
│   │       └── reports.html
│   ├── static/
│   │   ├── css/
│   │   └── js/
│   ├── application.yml                        # Configuration
│   └── data.sql                               # Initial data (optional)
├── pom.xml                                    # Maven dependencies
└── README.md                                  # This file
```

## 📊 Database Schema

### Tables
- **roles**: User roles (ADMIN, CASHIER)
- **users**: System users/staff
- **categories**: Product categories
- **products**: Menu items
- **tables**: Service tables
- **orders**: Sales invoices
- **order_details**: Order line items

## 🔐 Security Features

- **Role-Based Access Control (RBAC)**:
    - ADMIN: Full system access
    - CASHIER: Only order and sale point access

- **Password Security**: BCrypt encryption with salt

- **Session Management**: Automatic logout for inactive users

- **Database Security**: H2 Console access secured

## 🎨 UI/UX Highlights

- Responsive design for desktop and tablets
- Bootstrap 5 components
- Real-time order updates
- Intuitive table selection interface
- Color-coded status indicators
- Fast loading times

## 🔧 Main Endpoints

### Public
- `GET /` → Dashboard
- `GET /login` → Login page
- `GET /register` → Registration page
- `GET /h2-console` → H2 Database console

### Cashier Routes
- `GET /dashboard` → Dashboard
- `GET /order` → Order list and table selection
- `GET /order/new/{tableId}` → Create new order
- `GET /order/detail/{orderId}` → Order detail editing
- `GET /order/checkout/{orderId}` → Checkout page
- `POST /order/checkout/{orderId}/pay` → Process payment

### Admin Routes
- `GET /admin/products` → Product management
- `GET /admin/categories` → Category management
- `GET /admin/users` → User management
- `GET /admin/tables` → Table management
- `GET /admin/reports` → Revenue reports

## 💾 Sample Data

The application auto-initializes with demo data on first run:

### Categories
- Cà phê (Coffee)
- Trà sữa (Milk Tea)
- Nước ép (Juice)
- Bánh ngọt (Pastries)
- Đồ ăn nhẹ (Snacks)

### Products
- Espresso: 25,000 VND
- Americano: 30,000 VND
- Cappuccino: 40,000 VND
- Milk Tea: 35,000 VND
- Orange Juice: 25,000 VND

### Tables
10 tables (Bàn 1 - Bàn 10)

## 🐛 Troubleshooting

### Port already in use
```bash
# Change port in application.yml
server:
  port: 8081
```

### H2 Console not accessible
- Check browser allows cookies
- Verify URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:posdb`

### Cannot login
- Clear browser cache and cookies
- Verify credentials in DataInitializer.java
- Check user status in database

## 📈 Future Enhancements

- [ ] Inventory management
- [ ] Customer loyalty program
- [ ] Staff performance analytics
- [ ] Multi-location support
- [ ] Mobile app (React Native)
- [ ] Real-time notifications
- [ ] Order queueing system
- [ ] Advanced analytics dashboard

## 📄 License

This project is open source and available under the MIT License.

## 👥 Support

For questions or issues, please contact:
- Email: support@possystem.com
- Phone: 0123-456-789

## 📚 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Thymeleaf Documentation](https://www.thymeleaf.org/)
- [Bootstrap Documentation](https://getbootstrap.com/)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [H2 Database](https://www.h2database.com/)

---

**Version**: 1.0.0  
**Last Updated**: March 2025  
**Developed for**: Coffee Shop POS Management

```
Note: This is a development version using in-memory H2 database.
For production, configure a persistent database (MySQL, PostgreSQL, etc.)
```

