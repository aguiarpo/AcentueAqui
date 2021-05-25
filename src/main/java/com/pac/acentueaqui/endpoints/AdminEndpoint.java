package com.pac.acentueaqui.endpoints;

import com.pac.acentueaqui.models.LevelsOfAccess;
import com.pac.acentueaqui.models.users.Admin;
import com.pac.acentueaqui.models.users.User;
import com.pac.acentueaqui.repository.AdminRepository;
import com.pac.acentueaqui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("v1")
public class AdminEndpoint {

    private AdminRepository adminDao;
    private UserRepository userDao;

    @Autowired
    public AdminEndpoint(AdminRepository adminDao,
                         UserRepository userDao){
        this.adminDao = adminDao;
        this.userDao = userDao;
    }

    @GetMapping(path = "create/admin")
    public ResponseEntity<?> createAdmin(){
        List<User> users = userDao.findByLevelsOfAccess(LevelsOfAccess.ADMIN);
        if(users.isEmpty()){
            User user = new User();
            user.setName("Lucas Eduardo");
            user.setLevelsOfAccess(LevelsOfAccess.ADMIN);
            user.setPassword("admin123");
            user.setBcryptPassword();
            user.setUsername("eduardo.aguiarpo@gmail.com");
            user.setCreatedBy("Sistema");
            user.setCreatedDate(LocalDateTime.now());
            Admin admin = new Admin();
            admin.setEmail("eduardo.aguiarpo@gmail.com");
            admin.setUser(user);
            Admin savedAdmin = adminDao.save(admin);
            if(savedAdmin != null)
                return new ResponseEntity<>(savedAdmin,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
