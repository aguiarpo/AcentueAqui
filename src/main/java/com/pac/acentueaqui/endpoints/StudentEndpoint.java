package com.pac.acentueaqui.endpoints;

import com.pac.acentueaqui.models.LevelsOfAccess;
import com.pac.acentueaqui.models.users.Student;
import com.pac.acentueaqui.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class StudentEndpoint {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentEndpoint(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping(path = "save/student")
    public ResponseEntity<?> create(@RequestBody Student student){
        student.getUser().setLevelsOfAccess(LevelsOfAccess.ALUNO);
        student.getUser().setUsername(student.getRegistration());
        student.getUser().setBcryptPassword();
        Student saved = studentRepository.save(student);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findall/student")
    public ResponseEntity<?> findAll(){
        List<Student> student = studentRepository.findAll();
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findbycode/student/{code}")
    public ResponseEntity<?> findByCode(@PathVariable Long code){
        Student student = studentRepository.findByCode(code);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findbyregistration/student/{registration}")
    public ResponseEntity<?> findByRegistration(@PathVariable String registration){
        Student student = studentRepository.findByRegistration(registration);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping(path = "admin/save/student")
    public ResponseEntity<?> updateStudent(@RequestBody Student student){
        Student find = studentRepository.findByCode(student.getCode());
        student.getUser().setCode(find.getUser().getCode());
        student.getUser().setLevelsOfAccess(find.getUser().getLevelsOfAccess());
        student.getUser().setUsername(student.getRegistration());
        student.getUser().setPassword(find.getUser().getPassword());
        Student saved = studentRepository.save(student);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
}
