package com.example.demo.service;

import com.example.demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StudentService {

    @PersistenceContext
    private EntityManager em;

    // 1. CREATE (Đã có)
    @Transactional
    public void createStudent(String name, String email, int age) {
        Student s = new Student(name, email, age);
        em.persist(s);
        System.out.println("Saved with ID = " + s.getId());
    }

    // 2. READ (Đã có)
    @Transactional(readOnly = true)
    public void printAll() {
        List<Student> students = em.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList();
        System.out.println("--- Danh sách sinh viên ---");
        students.forEach(System.out::println);
    }

    // 3. UPDATE (Thêm mới)
    @Transactional
    public void updateStudent(Long id, String newName, String newEmail) {
        // Tìm đối tượng trong DB trước
        Student s = em.find(Student.class, id);
        if (s != null) {
            s.setFullName(newName);
            s.setEmail(newEmail);
            // Với EntityManager trong @Transactional, bạn không cần gọi em.merge(s)
            // Hibernate sẽ tự động nhận diện thay đổi và tạo lệnh SQL UPDATE (Dirty Checking)
            System.out.println("Đã cập nhật sinh viên ID: " + id);
        } else {
            System.out.println("Không tìm thấy sinh viên ID: " + id);
        }
    }

    // 4. DELETE (Thêm mới)
    @Transactional
    public void deleteStudent(Long id) {
        Student s = em.find(Student.class, id);
        if (s != null) {
            em.remove(s); // Thực hiện lệnh DELETE
            System.out.println("Đã xóa sinh viên ID: " + id);
        } else {
            System.out.println("Không thể xóa, ID không tồn tại: " + id);
        }
    }
}