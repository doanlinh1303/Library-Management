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
