package com.pac.acentueaqui.endpoints.user;

import com.pac.acentueaqui.models.LevelsOfAccess;
import com.pac.acentueaqui.models.users.Admin;
import com.pac.acentueaqui.models.users.User;
import com.pac.acentueaqui.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class AdminEndpoint {

    private final AdminRepository adminDao;
    private final UserRepository userDao;

    @Autowired
    public AdminEndpoint(AdminRepository adminDao,
                         UserRepository userDao) {
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
            Admin admin = new Admin();
            admin.setEmail("eduardo.aguiarpo@gmail.com");
            admin.setUser(user);
            Admin savedAdmin = adminDao.save(admin);
            return new ResponseEntity<>(savedAdmin,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "admin/save")
    public ResponseEntity<?> updateAdmin(@RequestBody Admin admin){
        Admin find = adminDao.findByCode(admin.getCode());
        admin.getUser().setCode(find.getUser().getCode());
        admin.getUser().setLevelsOfAccess(find.getUser().getLevelsOfAccess());
        admin.getUser().setUsername(admin.getEmail());
        admin.getUser().setPassword(find.getUser().getPassword());
        Admin saved = adminDao.save(admin);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findall")
    public ResponseEntity<?> findAll(){
        List<Admin> admins = adminDao.findAll();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findbycode/{code}")
    public ResponseEntity<?> findByCode(@PathVariable Long code){
        Admin admin = adminDao.findByCode(code);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findbyemail/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        Admin admin = adminDao.findByEmail(email);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }
}
