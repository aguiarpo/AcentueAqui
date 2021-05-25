package com.pac.acentueaqui.service;

import com.pac.acentueaqui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.pac.acentueaqui.models.users.User user = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        if(user.getStatus() == Status.INVISIBLE)
//            throw new UsernameNotFoundException("User not found");
        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_TEACHER", "ROLE_SCHOOL", "ROLE_ADMIN", "ROLE_USER");
        List<GrantedAuthority> authorityListSchool = AuthorityUtils.createAuthorityList("ROLE_SCHOOL", "ROLE_TEACHER", "ROLE_STUDENT");
        List<GrantedAuthority> authorityListTeacher = AuthorityUtils.createAuthorityList("ROLE_TEACHER", "ROLE_STUDENT");
        List<GrantedAuthority> authorityListStudent = AuthorityUtils.createAuthorityList("ROLE_STUDENT");
        List<GrantedAuthority> nivelUser;
        switch (user.getLevelsOfAccess()){
            case ESCOLA:
                nivelUser = authorityListSchool;
                break;
            case ADMIN:
                nivelUser = authorityListAdmin;
                break;
            case PROFESSOR:
                nivelUser = authorityListTeacher;
                break;
            default:
                nivelUser = authorityListStudent;
                break;
        }
        return new User(user.getUsername(), user.getPassword(),nivelUser);
    }
}