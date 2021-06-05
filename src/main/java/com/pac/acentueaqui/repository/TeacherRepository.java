package com.pac.acentueaqui.repository;

import com.pac.acentueaqui.models.users.Admin;
import com.pac.acentueaqui.models.users.Teacher;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Long> {
    List<Teacher> findAll();
    Teacher findByCode(Long code);
    Teacher findByEmail(String email);
}
