package com.pac.acentueaqui.repository;

import com.pac.acentueaqui.models.users.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
