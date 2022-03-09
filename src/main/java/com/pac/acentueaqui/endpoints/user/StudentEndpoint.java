package com.pac.acentueaqui.endpoints.user;

import com.pac.acentueaqui.models.Class;
import com.pac.acentueaqui.models.LevelsOfAccess;
import com.pac.acentueaqui.models.users.Student;
import com.pac.acentueaqui.repository.ClassRepository;
import com.pac.acentueaqui.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class StudentEndpoint {
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;

    @Autowired
    public StudentEndpoint(StudentRepository studentRepository,
                           ClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    @PostMapping(path = "school/add/student")
    public ResponseEntity<?> create(@RequestBody Student student){
        student.getUser().setPassword(student.getRegistration());
        student.getUser().setLevelsOfAccess(LevelsOfAccess.ALUNO);
        student.getUser().setUsername(student.getRegistration());
        student.getUser().setBcryptPassword();
        Class find = classRepository.findByName(student.getClasse().getName());
        student.getClasse().setCode(find.getCode());
        Student saved = studentRepository.save(student);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @DeleteMapping(path = "school/student/remove/class/{registration}")
    public ResponseEntity<?> removeClass(@PathVariable String registration){
        Student findStudent = studentRepository.findByRegistration(registration);
        findStudent.setClasse(null);
        Student saved = studentRepository.save(findStudent);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PutMapping(path = "school/student/update/class/{registration}/{name}")
    public ResponseEntity<?> updateClass(@PathVariable String registration,
                                         @PathVariable String name){
        Class findClass = classRepository.findByName(name);
        Student findStudent = studentRepository.findByRegistration(registration);
        findStudent.setClasse(findClass);
        Student saved = studentRepository.save(findStudent);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping(path = "school/findall/student")
    public ResponseEntity<?> findAll(){
        List<Student> student = studentRepository.findAll();
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "school/findemail/student")
    public ResponseEntity<?> findEmail(@AuthenticationPrincipal UserDetails auth){
        List<Student> student = studentRepository.findByClasseSchoolEmail(auth.getUsername());
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findbycode/student/{code}")
    public ResponseEntity<?> findByCode(@PathVariable Long code){
        Student student = studentRepository.findByCode(code);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "school/findbyregistration/student/{registration}")
    public ResponseEntity<?> findByRegistration(@PathVariable String registration){
        Student student = studentRepository.findByRegistration(registration);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping(path = "school/save/student")
    public ResponseEntity<?> updateStudent(@RequestBody Student student){
        Class findClass = classRepository.findByName(student.getClasse().getName());
        Student find = studentRepository.findByCode(student.getCode());
        student.setClasse(findClass);
        student.getUser().setCode(find.getUser().getCode());
        student.getUser().setLevelsOfAccess(find.getUser().getLevelsOfAccess());
        student.getUser().setUsername(student.getRegistration());
        student.getUser().setPassword(find.getUser().getPassword());
        Student saved = studentRepository.save(student);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
}
