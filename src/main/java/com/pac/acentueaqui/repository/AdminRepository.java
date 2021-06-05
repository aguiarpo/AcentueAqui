package com.pac.acentueaqui.repository;

import com.pac.acentueaqui.models.users.Admin;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AdminRepository extends PagingAndSortingRepository<Admin, Long> {
    List<Admin> findAll();
    Admin findByCode(Long code);
    Admin findByEmail(String email);
}
