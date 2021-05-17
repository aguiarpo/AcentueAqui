package com.pac.acentueaqui.endpoints;

import com.pac.acentueaqui.repository.AdminRepository;
import com.pac.acentueaqui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
