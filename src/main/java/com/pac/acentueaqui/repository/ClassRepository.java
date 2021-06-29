package com.pac.acentueaqui.repository;

import com.pac.acentueaqui.models.Class;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassRepository extends PagingAndSortingRepository<Class, Long> {
    Class findByName(String name);
    List<Class> findAll();
    Class findByCode(Long code);
    List<Class> findBySchoolEmail(String email);
    @Query(value = "SELECT id, name, school_id " +
            "FROM public.class as c inner join teacher_class as t on c.id = t.class_id " +
            "where t.teacher_id = :code", nativeQuery = true)
    List<Class> findByTeacherCode(@Param("code") Long code);
}
