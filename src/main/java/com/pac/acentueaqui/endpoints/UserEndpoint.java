package com.pac.acentueaqui.endpoints;

import com.pac.acentueaqui.models.users.User;
import com.pac.acentueaqui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class UserEndpoint {

    private UserRepository userDao;

    @Autowired
    public UserEndpoint(UserRepository userDao){
        this.userDao = userDao;
    }

    @GetMapping(path = "/user/get/login")
    public ResponseEntity<?> getLogin(@AuthenticationPrincipal UserDetails auth){
        User user = userDao.findByUsername(auth.getUsername());
        user.getCode();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
