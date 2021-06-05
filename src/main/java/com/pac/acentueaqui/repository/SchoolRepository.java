package com.pac.acentueaqui.repository;

import com.pac.acentueaqui.models.users.School;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SchoolRepository extends PagingAndSortingRepository<School, Long> {
    List<School> findAll();
    School findByCode(Long code);
    School findByEmail(String email);
}
