# Cấu trúc các bảng (Database Schema)
## 1. Nhóm Quản trị Nhân sự:

### Bảng roles (Phân quyền):

- id (PK): Mã quyền.

- name: Tên quyền (VD: ROLE_ADMIN, ROLE_CASHIER).

### Bảng users (Tài khoản nhân viên):

- id (PK): Mã nhân viên.

- role_id (FK): Khóa ngoại liên kết tới bảng roles.

- username: Tên đăng nhập.

- password: Mật khẩu (cần được băm/hash).

- full_name: Họ và tên.

- status: Trạng thái hoạt động (1 = Active, 0 = Inactive).

## 2. Nhóm Quản lý Menu & Bàn:

### Bảng categories (Danh mục đồ uống):

- id (PK): Mã danh mục.

- name: Tên danh mục (VD: Cà phê, Trà sữa).

### Bảng products (Sản phẩm):

- id (PK): Mã sản phẩm.

- category_id (FK): Khóa ngoại liên kết tới categories.

- name: Tên món.

- price: Giá bán mặc định.

- is_available: Tình trạng còn hàng (Boolean).

### Bảng tables (Bàn phục vụ):

- id (PK): Mã bàn.

- name: Tên bàn (VD: Bàn 1, Tầng 2 - Bàn 3).

- status: Trạng thái bàn (VD: EMPTY, OCCUPIED).

## 3. Nhóm Quản lý Giao dịch (Hóa đơn):

### Bảng orders (Hóa đơn tổng):

- id (PK): Mã hóa đơn.

- table_id (FK): Khóa ngoại tới tables (bàn nào đang gọi món).

- user_id (FK): Khóa ngoại tới users (nhân viên nào thu ngân).

- total_amount: Tổng tiền cần thanh toán.

- status: Trạng thái hóa đơn (VD: PENDING, PAID, CANCELLED).

- created_at: Thời gian tạo hóa đơn.

### Bảng order_details (Chi tiết món trong hóa đơn):

- id (PK): Mã chi tiết.

- order_id (FK): Khóa ngoại tới orders.

- product_id (FK): Khóa ngoại tới products.

- quantity: Số lượng.

- size: Kích cỡ (S, M, L).

- note: Ghi chú (VD: Ít đường, thêm trân châu).

- unit_price: Giá tại thời điểm bán (lưu lại để tránh việc đổi giá sản phẩm sau này làm sai lệch lịch sử doanh thu).