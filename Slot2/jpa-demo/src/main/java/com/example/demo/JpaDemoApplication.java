package com.example.demo;

import com.example.demo.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(StudentService service) {
        return args -> {
            // 1. Tạo mới
            service.createStudent("Nguyễn Văn A", "a@fpt.edu.vn", 20);
            service.createStudent("Trần Thị B", "b@fpt.edu.vn", 21);

            // 2. Thực hiện Update và Delete ngay tại đây
            System.out.println("--- Đang thực hiện thay đổi dữ liệu ---");
            service.updateStudent(2L, "Trần Thị B Updated", "b.updated@fpt.edu.vn");
            service.deleteStudent(1L);

            // 3. In ra console để kiểm tra nhanh
            service.printAll();

            System.out.println(">>> App đang chạy. Hãy vào http://localhost:8080/h2-console để soi!");
        };
    }
}