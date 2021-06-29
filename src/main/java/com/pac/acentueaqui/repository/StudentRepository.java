package com.pac.acentueaqui.repository;

import com.pac.acentueaqui.models.users.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    List<Student> findAll();
    Student findByCode(Long code);
    Student findByRegistration(String registration);
    List<Student> findByClasseSchoolEmail(String email);
}
