package com.example.demo;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentTest {

    @Autowired
    private StudentService studentService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    public void testUpdateAndDeleteAfterCommandLineRunner() {
        // Tự tạo data mẫu ngay trong Test để chắc chắn ID tồn tại
        Student testStudent1 = new Student("Nguyễn Văn A", "a@fpt.edu.vn", 20);
        Student testStudent2 = new Student("Trần Thị B", "b@fpt.edu.vn", 21);

        entityManager.persist(testStudent1);
        entityManager.persist(testStudent2);

        // Đồng bộ xuống DB và xóa cache để các lệnh find() sau đó chạy chuẩn
        entityManager.flush();
        entityManager.clear();

        // Lấy ID thực tế vừa được sinh ra (thay vì fix cứng 1L, 2L)
        Long id1 = testStudent1.getId();
        Long id2 = testStudent2.getId();

        // --- BẮT ĐẦU TEST UPDATE VỚI id2 ---
        Student s2 = entityManager.find(Student.class, id2);
        assertNotNull(s2, "Sinh viên ID " + id2 + " phải tồn tại");
        // ... tiếp tục các bước update của bạn ...

        // --- BẮT ĐẦU TEST DELETE VỚI id1 ---
        Student s1 = entityManager.find(Student.class, id1);
        assertNotNull(s1, "Sinh viên ID " + id1 + " phải tồn tại");
        // ... tiếp tục các bước delete của bạn ...
    }
}