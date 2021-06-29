package com.pac.acentueaqui.endpoints.user;

import com.pac.acentueaqui.models.users.*;
import com.pac.acentueaqui.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class UserEndpoint {

    private final UserRepository userDao;
    private final AdminRepository adminRepository;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public UserEndpoint(UserRepository userDao,
                        AdminRepository adminRepository,
                        SchoolRepository schoolRepository,
                        StudentRepository studentRepository,
                        TeacherRepository teacherRepository) {
        this.userDao = userDao;
        this.adminRepository = adminRepository;
        this.schoolRepository = schoolRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping(path = "/get/login")
    public ResponseEntity<?> getLogin(@AuthenticationPrincipal UserDetails auth){
        User user = userDao.findByUsername(auth.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/user/get")
    public ResponseEntity<?> get(@AuthenticationPrincipal UserDetails auth){
        User findUser = userDao.findByUsername(auth.getUsername());
        switch(findUser.getLevelsOfAccess()){
            case ADMIN:
                Admin admin;
                admin = adminRepository.findByEmail(findUser.getUsername());
                return new ResponseEntity<>(admin, HttpStatus.OK);
            case ALUNO:
                Student student;
                student = studentRepository.findByRegistration(findUser.getUsername());
                return new ResponseEntity<>(student, HttpStatus.OK);
            case ESCOLA:
                School school;
                school = schoolRepository.findByEmail(findUser.getUsername());
                return new ResponseEntity<>(school, HttpStatus.OK);
            case PROFESSOR:
                Teacher teacher;
                teacher = teacherRepository.findByEmail(findUser.getUsername());
                return new ResponseEntity<>(teacher, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/user/update/admin")
    public ResponseEntity<?> update(@RequestBody Admin admin,
                                    @AuthenticationPrincipal UserDetails auth){
        Admin find = adminRepository.findByEmail(auth.getUsername());
        admin.getUser().setCode(find.getUser().getCode());
        admin.setCode(find.getCode());
        admin.getUser().setUsername(admin.getEmail());
        admin.getUser().setLevelsOfAccess(find.getUser().getLevelsOfAccess());
        if(admin.getUser().getPassword() == null)
            admin.getUser().setPassword(find.getUser().getPassword());
        else admin.getUser().setBcryptPassword();
        Admin saved = adminRepository.save(admin);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PutMapping(path = "/user/update/school")
    public ResponseEntity<?> update(@RequestBody School school,
                                    @AuthenticationPrincipal UserDetails auth){
        School find = schoolRepository.findByEmail(auth.getUsername());
        school.getUser().setCode(find.getUser().getCode());
        school.setCode(find.getCode());
        school.getUser().setUsername(school.getEmail());
        school.getUser().setLevelsOfAccess(find.getUser().getLevelsOfAccess());
        if(school.getUser().getPassword() == null)
            school.getUser().setPassword(find.getUser().getPassword());
        else school.getUser().setBcryptPassword();
        School saved = schoolRepository.save(school);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PutMapping(path = "/user/update/teacher")
    public ResponseEntity<?> update(@RequestBody Teacher teacher,
                                    @AuthenticationPrincipal UserDetails auth){
        Teacher find = teacherRepository.findByEmail(auth.getUsername());
        teacher.getUser().setLevelsOfAccess(find.getUser().getLevelsOfAccess());
        teacher.getUser().setCode(find.getUser().getCode());
        teacher.setCode(find.getCode());
        teacher.getUser().setUsername(teacher.getEmail());
        if(teacher.getUser().getPassword() == null)
            teacher.getUser().setPassword(find.getUser().getPassword());
        else teacher.getUser().setBcryptPassword();
        Teacher saved = teacherRepository.save(teacher);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PutMapping(path = "/user/update/student")
    public ResponseEntity<?> update(@RequestBody Student student,
                                    @AuthenticationPrincipal UserDetails auth){
        Student find = studentRepository.findByRegistration(auth.getUsername());
        student.getUser().setLevelsOfAccess(find.getUser().getLevelsOfAccess());
        student.getUser().setCode(find.getUser().getCode());
        student.setCode(find.getCode());
        student.getUser().setUsername(student.getRegistration());
        if(student.getUser().getPassword() == null)
            student.getUser().setPassword(find.getUser().getPassword());
        else student.getUser().setBcryptPassword();
        Student saved = studentRepository.save(student);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
}
