package com.pac.acentueaqui.repository;

import com.pac.acentueaqui.models.users.Admin;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdminRepository extends PagingAndSortingRepository<Admin, Long> {
}
