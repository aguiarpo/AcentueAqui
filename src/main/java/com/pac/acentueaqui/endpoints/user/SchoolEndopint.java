package com.pac.acentueaqui.endpoints.user;

import com.pac.acentueaqui.models.LevelsOfAccess;
import com.pac.acentueaqui.models.users.School;
import com.pac.acentueaqui.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class SchoolEndopint {
    private final SchoolRepository schoolDao;

    @Autowired
    public SchoolEndopint(SchoolRepository schoolDao) {
        this.schoolDao = schoolDao;
    }

    @PostMapping(path = "save/school")
    public ResponseEntity<?> create(@RequestBody School school){
        school.getUser().setLevelsOfAccess(LevelsOfAccess.ESCOLA);
        school.getUser().setUsername(school.getEmail());
        school.getUser().setBcryptPassword();
        School savedSchool = schoolDao.save(school);
        return new ResponseEntity<>(savedSchool, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findall/school")
    public ResponseEntity<?> findAll(){
        List<School> schools = schoolDao.findAll();
        return new ResponseEntity<>(schools, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findbycode/school/{code}")
    public ResponseEntity<?> findByCode(@PathVariable Long code){
        School school = schoolDao.findByCode(code);
        return new ResponseEntity<>(school, HttpStatus.OK);
    }

    @GetMapping(path = "school/findbyemail/school/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        School school = schoolDao.findByEmail(email);
        return new ResponseEntity<>(school, HttpStatus.OK);
    }

    @PutMapping(path = "admin/save/school")
    public ResponseEntity<?> updateSchool(@RequestBody School school){
        School find = schoolDao.findByCode(school.getCode());
        school.getUser().setCode(find.getUser().getCode());
        school.getUser().setLevelsOfAccess(find.getUser().getLevelsOfAccess());
        school.getUser().setUsername(school.getEmail());
        school.getUser().setPassword(find.getUser().getPassword());
        School saved = schoolDao.save(school);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
}
