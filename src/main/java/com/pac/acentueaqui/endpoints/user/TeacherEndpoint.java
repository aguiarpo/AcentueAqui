package com.pac.acentueaqui.endpoints.user;

import com.pac.acentueaqui.models.LevelsOfAccess;
import com.pac.acentueaqui.models.users.Teacher;
import com.pac.acentueaqui.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class TeacherEndpoint {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherEndpoint(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @PostMapping(path = "save/teacher")
    public ResponseEntity<?> create(@RequestBody Teacher teacher){
        teacher.getUser().setLevelsOfAccess(LevelsOfAccess.PROFESSOR);
        teacher.getUser().setUsername(teacher.getEmail());
        teacher.getUser().setBcryptPassword();
        Teacher saved = teacherRepository.save(teacher);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findall/teacher")
    public ResponseEntity<?> findAll(){
        List<Teacher> teachers = teacherRepository.findAll();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findbycode/teacher/{code}")
    public ResponseEntity<?> findByCode(@PathVariable Long code){
        Teacher teacher = teacherRepository.findByCode(code);
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findbyemail/teacher/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        Teacher teacher = teacherRepository.findByEmail(email);
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @PutMapping(path = "admin/save/teacher")
    public ResponseEntity<?> updateTeacher(@RequestBody Teacher teacher){
        Teacher find = teacherRepository.findByCode(teacher.getCode());
        teacher.getUser().setCode(find.getUser().getCode());
        teacher.getUser().setLevelsOfAccess(find.getUser().getLevelsOfAccess());
        teacher.getUser().setUsername(teacher.getEmail());
        teacher.getUser().setPassword(find.getUser().getPassword());
        Teacher saved = teacherRepository.save(teacher);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
}
