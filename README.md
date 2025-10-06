# Library-Management

## Giai đoạn 1: Nền tảng Spring Core

### Yêu cầu đã thực hiện
- Thiết lập dự án với Spring Core
- Xây dựng các model cơ bản: Book, User, Borrowing
- Áp dụng mẫu **Dependency Injection** (DI) thông qua annotation (@Repository, @Service, @Component)
- Sử dụng **Repository Pattern** cho tầng truy cập dữ liệu (các interface và lớp triển khai trong repository/impl)
- Cấu hình application context thông qua XML và Annotation (AppConfig.java, có thể bổ sung applicationContext.xml nếu cần)

### Cấu trúc thư mục
```
src/
  main/
    java/com/library/
      config/        (Cấu hình Spring)
      controller/    (MVC & REST controllers)
      dto/           (Đối tượng truyền dữ liệu)
      model/         (Các entity domain: Book, User, Borrowing)
      repository/    (Tầng truy cập dữ liệu, Repository Pattern)
        impl/        (Triển khai repository lưu trữ dữ liệu trong RAM)
      service/       (Xử lý nghiệp vụ)
      util/          (Các lớp tiện ích)
    resources/
      static/        (JS, CSS, images)
      templates/     (Các template view)
  test/
    java/com/library/model/      (Test cho model)
    java/com/library/repository/ (Test cho repository)
```

### Hướng dẫn kiểm tra
- Đã có các file test cho model và repository.
- Chạy lệnh sau để kiểm tra các chức năng đã hoàn thành:
  ```bash
  mvn test
  ```
- Kết quả test sẽ hiển thị trên console, bao gồm các giá trị tên sách hoặc tên tác giả đã được kiểm tra.

### Ghi chú
- Nếu muốn cấu hình context bằng XML, hãy tạo file `applicationContext.xml` trong `src/main/resources` và khai báo bean.
- Nếu dùng annotation, đảm bảo các class có annotation phù hợp (@Repository, @Service, @Component).

## Giai đoạn 2: Spring MVC và Web Development

### Yêu cầu đã thực hiện
- Tích hợp Spring MVC để xây dựng giao diện web
- Sử dụng Thymeleaf làm template engine
- Triển khai các controller cho các module chính: Book, User, Borrowing
- Áp dụng DTO pattern để chuyển đổi dữ liệu giữa model và view
- Triển khai upload file cho ảnh bìa sách và avatar người dùng
- Xây dựng form tìm kiếm với nhiều tiêu chí (category, status, ...)
- Xử lý validation và error handling
- Xây dựng các màn hình thông báo và thống kê

### Cấu trúc MVC đã triển khai
- **Controllers**: Xử lý request, điều hướng và trả về view
- **Services**: Chứa business logic, kết nối controllers với repositories
- **Repositories**: Thực hiện thao tác với database thông qua JDBC
- **Models**: Các đối tượng thực thể (entities)
- **DTOs**: Đối tượng trung gian để truyền dữ liệu giữa controller và view
- **Mappers**: Chuyển đổi giữa entities và DTOs

### Các chức năng chính
1. **Quản lý sách**
   - Thêm, sửa, xoá, hiển thị chi tiết sách
   - Tìm kiếm sách theo nhiều tiêu chí
   - Upload ảnh bìa sách

2. **Quản lý người dùng**
   - Thêm, sửa, xoá, hiển thị chi tiết người dùng
   - Tìm kiếm theo username, role và status
   - Upload avatar người dùng
   - Mã hóa mật khẩu với BCrypt

3. **Quản lý mượn trả**
   - Đăng ký mượn sách
   - Xác nhận trả sách
   - Tìm kiếm lịch sử mượn trả

### Cấu hình hệ thống
- Các đường dẫn upload file được cấu hình trong:
  - `BookService.java`: `UPLOAD_DIR = "/home/doanlinh/Downloads/Library-Management/uploads"`
  - `UserService.java`: `UPLOAD_DIR = "/home/doanlinh/Downloads/Library-Management/uploads"`

- Database được cấu hình trong:
  - `src/main/resources/database.properties`

### Hướng dẫn chạy dự án
1. **Cấu hình database**
   ```bash
   # Tạo schema và các bảng cần thiết trong MySQL
   mysql -u your_username -p your_database < database/setup.sql
   ```

2. **Cấu hình upload path**
   - Mở file `BookService.java` và `UserService.java`
   - Thay đổi `UPLOAD_DIR` tới đường dẫn phù hợp với máy của bạn
   - Đảm bảo thư mục có quyền ghi

3. **Chạy dự án**
   ```bash
   # Biên dịch dự án
   mvn clean package

   # Deploy file .war lên Tomcat hoặc chạy trực tiếp
   java -jar target/library-management-1.0.0.war
   ```

4. **Truy cập ứng dụng**
   - URL: `http://localhost:8080/library`
   - Đăng nhập mặc định:
     - Username: `admin`
     - Password: `password`

    
### Ghi chú phát triển
- Các template HTML nằm trong `src/main/webapp/WEB-INF/templates`
- Static resources (CSS, JS) nằm trong `src/main/resources/static`
- Sử dụng Bootstrap 5 và Bootstrap Icons cho giao diện
- Thymeleaf được sử dụng làm template engine
