# Hệ Thống POS - Ứng Dụng Spring Boot

Hệ thống Điểm Bán Hàng (POS) hoàn chỉnh để quản lý hoạt động quán cà phê, được xây dựng bằng Spring Boot, Thymeleaf, Bootstrap và H2 Database.

## 🎯 Tính Năng

### Dành Cho Thu Ngân (Nhân Viên Bán Hàng)
- **Quản Lý Đơn Hàng**: Xem menu sản phẩm được phân theo danh mục
- **Tùy Chỉnh Món**: Chọn kích cỡ (S, M, L), thêm ghi chú (topping, lượng đường, lượng đá)
- **Quản Lý Bàn**: Xem trạng thái bàn (Trống, Có khách, Đã đặt)
- **Thanh Toán Nhanh**: Tính tổng tiền với VAT và giảm giá
- **Phương Thức Thanh Toán**: Hỗ trợ tiền mặt, chuyển khoản và mã QR
- **In Hóa Đơn**: In hoặc xuất hóa đơn

### Dành Cho Admin/Quản Lý
- **Quản Lý Sản Phẩm**: Thêm, sửa, xóa sản phẩm kèm hình ảnh và mô tả
- **Quản Lý Danh Mục**: Phân loại sản phẩm theo danh mục
- **Quản Lý Nhân Viên**: Tạo tài khoản với phân quyền theo vai trò
- **Quản Lý Bàn**: Cấu hình và quản lý bàn phục vụ
- **Báo Cáo Doanh Thu**: Thống kê doanh số theo ngày, tuần, tháng
- **Báo Cáo Bán Chạy**: Theo dõi các món phổ biến nhất

## 🛠️ Công Nghệ Sử Dụng

- **Backend**: Spring Boot 3.2.0
- **Frontend**: Thymeleaf + Bootstrap 5
- **Cơ sở dữ liệu**: H2 (in-memory)
- **Bảo mật**: Spring Security với mã hóa mật khẩu BCrypt
- **Công cụ build**: Maven

## 📋 Yêu Cầu Hệ Thống

- Java 17 trở lên
- Maven 3.6+
- Kết nối internet (để tải các dependency của Maven)

## 🚀 Cài Đặt & Chạy Ứng Dụng

### 1. Di chuyển đến thư mục dự án
```bash
cd G:\Workspace\pos2
```

### 2. Build dự án
```bash
mvn clean install
```

### 3. Chạy ứng dụng
```bash
mvn spring-boot:run
```

Hoặc nếu đã build file JAR:
```bash
java -jar target/pos-system-1.0.0.jar
```

### 4. Truy cập ứng dụng

Mở trình duyệt và truy cập:
- **Ứng dụng chính**: http://localhost:8080
- **H2 Database Console**: http://localhost:8080/h2-console

## 📝 Thông Tin Đăng Nhập Mặc Định

### Tài Khoản Admin
- **Tên đăng nhập**: admin
- **Mật khẩu**: admin123

### Tài Khoản Thu Ngân
- **Tên đăng nhập**: cashier
- **Mật khẩu**: cashier123

## 📁 Cấu Trúc Dự Án

```
pos2/
├── src/main/java/com/pos/
│   ├── PosApplication.java                    # Lớp khởi động ứng dụng
│   ├── entity/                                # Các lớp JPA Entity
│   │   ├── Role.java
│   │   ├── User.java
│   │   ├── Category.java
│   │   ├── Product.java
│   │   ├── PosTable.java
│   │   ├── Order.java
│   │   └── OrderDetail.java
│   ├── repository/                            # Spring Data JPA Repositories
│   ├── service/                               # Các lớp xử lý nghiệp vụ
│   ├── controller/                            # Các MVC Controller
│   ├── config/                                # Các lớp cấu hình
│   │   ├── SecurityConfig.java
│   │   ├── UserDetailsServiceImpl.java
│   │   └── DataInitializer.java
│   └── dto/                                   # Các đối tượng truyền dữ liệu (nếu cần)
├── src/main/resources/
│   ├── templates/                             # Các template Thymeleaf
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
│   ├── application.yml                        # Tệp cấu hình
│   └── data.sql                               # Dữ liệu khởi tạo (tùy chọn)
├── pom.xml                                    # Các dependency Maven
└── README.md                                  # Tệp này
```

## 📊 Cấu Trúc Cơ Sở Dữ Liệu

### Các Bảng
- **roles**: Vai trò người dùng (ADMIN, CASHIER)
- **users**: Người dùng/nhân viên trong hệ thống
- **categories**: Danh mục sản phẩm
- **products**: Các món trong thực đơn
- **tables**: Bàn phục vụ
- **orders**: Hóa đơn bán hàng
- **order_details**: Chi tiết từng dòng trong đơn hàng

## 🔐 Tính Năng Bảo Mật

- **Phân Quyền Theo Vai Trò (RBAC)**:
    - ADMIN: Toàn quyền truy cập hệ thống
    - CASHIER: Chỉ truy cập phần đặt hàng và bán hàng

- **Bảo Mật Mật Khẩu**: Mã hóa BCrypt có salt

- **Quản Lý Phiên**: Tự động đăng xuất khi không hoạt động

- **Bảo Mật Cơ Sở Dữ Liệu**: Bảo vệ quyền truy cập H2 Console

## 🎨 Điểm Nổi Bật UI/UX

- Giao diện responsive cho máy tính và máy tính bảng
- Các thành phần Bootstrap 5
- Cập nhật đơn hàng theo thời gian thực
- Giao diện chọn bàn trực quan
- Chỉ báo trạng thái bằng màu sắc
- Thời gian tải nhanh

## 🔧 Các Endpoint Chính

### Công Khai
- `GET /` → Trang chủ
- `GET /login` → Trang đăng nhập
- `GET /register` → Trang đăng ký
- `GET /h2-console` → H2 Database console

### Đường Dẫn Thu Ngân
- `GET /dashboard` → Trang chủ
- `GET /order` → Danh sách đơn hàng và chọn bàn
- `GET /order/new/{tableId}` → Tạo đơn hàng mới
- `GET /order/detail/{orderId}` → Chỉnh sửa chi tiết đơn hàng
- `GET /order/checkout/{orderId}` → Trang thanh toán
- `POST /order/checkout/{orderId}/pay` → Xử lý thanh toán

### Đường Dẫn Admin
- `GET /admin/products` → Quản lý sản phẩm
- `GET /admin/categories` → Quản lý danh mục
- `GET /admin/users` → Quản lý người dùng
- `GET /admin/tables` → Quản lý bàn
- `GET /admin/reports` → Báo cáo doanh thu

## 💾 Dữ Liệu Mẫu

Ứng dụng tự động khởi tạo dữ liệu demo khi chạy lần đầu:

### Danh Mục
- Cà phê (Coffee)
- Trà sữa (Milk Tea)
- Nước ép (Juice)
- Bánh ngọt (Pastries)
- Đồ ăn nhẹ (Snacks)

### Sản Phẩm
- Espresso: 25.000 VND
- Americano: 30.000 VND
- Cappuccino: 40.000 VND
- Trà sữa: 35.000 VND
- Nước cam: 25.000 VND

### Bàn
10 bàn (Bàn 1 - Bàn 10)

## 🐛 Xử Lý Sự Cố

### Cổng đã được sử dụng
```bash
# Thay đổi cổng trong application.yml
server:
  port: 8081
```

### Không truy cập được H2 Console
- Kiểm tra trình duyệt cho phép cookie
- Xác minh URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:posdb`

### Không thể đăng nhập
- Xóa cache và cookie của trình duyệt
- Kiểm tra thông tin đăng nhập trong DataInitializer.java
- Kiểm tra trạng thái người dùng trong cơ sở dữ liệu

## 📈 Tính Năng Dự Kiến

- [ ] Quản lý kho hàng
- [ ] Chương trình khách hàng thân thiết
- [ ] Phân tích hiệu suất nhân viên
- [ ] Hỗ trợ đa chi nhánh
- [ ] Ứng dụng di động (React Native)
- [ ] Thông báo thời gian thực
- [ ] Hệ thống hàng đợi đơn hàng
- [ ] Dashboard phân tích nâng cao

## 📄 Giấy Phép

Dự án này là mã nguồn mở và có sẵn theo Giấy phép MIT.

## 👥 Hỗ Trợ

Nếu có câu hỏi hoặc vấn đề, vui lòng liên hệ:
- Email: support@possystem.com
- Điện thoại: 0123-456-789

## 📚 Tài Nguyên Tham Khảo

- [Tài liệu Spring Boot](https://spring.io/projects/spring-boot)
- [Tài liệu Thymeleaf](https://www.thymeleaf.org/)
- [Tài liệu Bootstrap](https://getbootstrap.com/)
- [Tài liệu Spring Security](https://spring.io/projects/spring-security)
- [H2 Database](https://www.h2database.com/)

---

**Phiên bản**: 1.0.0  
**Cập nhật lần cuối**: Tháng 3 năm 2025  
**Phát triển cho**: Quản Lý POS Quán Cà Phê

```
Lưu ý: Đây là phiên bản phát triển sử dụng H2 database in-memory.
Để triển khai thực tế, hãy cấu hình cơ sở dữ liệu lưu trữ lâu dài (MySQL, PostgreSQL, v.v.)
```
