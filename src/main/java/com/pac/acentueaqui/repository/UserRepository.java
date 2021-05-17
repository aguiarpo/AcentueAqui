package com.pac.acentueaqui.repository;

import com.pac.acentueaqui.models.LevelsOfAccess;
import com.pac.acentueaqui.models.users.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    List<User> findByLevelsOfAccess(LevelsOfAccess levelsOfAccess);
    User findByUsername(String username);
}
