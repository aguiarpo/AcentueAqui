package com.pac.acentueaqui.endpoints;

import com.pac.acentueaqui.models.Class;
import com.pac.acentueaqui.models.users.School;
import com.pac.acentueaqui.models.users.Teacher;
import com.pac.acentueaqui.repository.ClassRepository;
import com.pac.acentueaqui.repository.SchoolRepository;
import com.pac.acentueaqui.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class ClassEndpoint {
    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public ClassEndpoint(TeacherRepository teacherRepository,
                         ClassRepository classRepository,
                         SchoolRepository schoolRepository) {
        this.teacherRepository = teacherRepository;
        this.classRepository = classRepository;
        this.schoolRepository = schoolRepository;
    }

    @PostMapping(path = "school/add/class")
    public ResponseEntity<?> addClass(@RequestBody Class classe,
                                      @AuthenticationPrincipal UserDetails auth){
        School findSchool = schoolRepository.findByEmail(auth.getUsername());
        classe.setSchool(findSchool);
        for(Teacher teacher: classe.getTeachers()){
            Teacher findTeacher = teacherRepository.findByEmail(teacher.getEmail());
            teacher.setCode(findTeacher.getCode());
        }
        Class saved = classRepository.save(classe);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PutMapping(path = "school/edit/class")
    public ResponseEntity<?> addClass(@RequestBody Class classe){
        Class findClass = classRepository.findByCode(classe.getCode());
        classe.setSchool(findClass.getSchool());
        for(Teacher teacher: classe.getTeachers()){
            Teacher findTeacher = teacherRepository.findByEmail(teacher.getEmail());
            teacher.setCode(findTeacher.getCode());
        }
        Class saved = classRepository.save(classe);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping(path = "school/findname/class/{name}")
    public ResponseEntity<?> findClassName(@PathVariable String name){
        Class find = classRepository.findByName(name);
        return new ResponseEntity<>(find, HttpStatus.OK);
    }

    @GetMapping(path = "teacher/findteacher/class")
    public ResponseEntity<?> findClassName(@AuthenticationPrincipal UserDetails auth){
        Teacher teacher = teacherRepository.findByEmail(auth.getUsername());
        List<Class> find = classRepository.findByTeacherCode(teacher.getCode());
        return new ResponseEntity<>(find, HttpStatus.OK);
    }

    @GetMapping(path = "school/findemail/class")
    public ResponseEntity<?> findClassEmail(@AuthenticationPrincipal UserDetails auth){
        List<Class> find = classRepository.findBySchoolEmail(auth.getUsername());
        return new ResponseEntity<>(find, HttpStatus.OK);
    }

    @GetMapping(path = "school/findall/class")
    public ResponseEntity<?> findAll(){
        List<Class> find = classRepository.findAll();
        return new ResponseEntity<>(find, HttpStatus.OK);
    }

    @DeleteMapping(path = "school/delete/class/{code}")
    public ResponseEntity<?> deleteClass(@PathVariable Long code){
        Class findClass = classRepository.findByCode(code);
        classRepository.delete(findClass);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "school/delete/teacher/{email}/{nameClass}")
    public ResponseEntity<?> deleteClassTeacher(@PathVariable String email,
                                              @PathVariable String nameClass){
        Class findClass = classRepository.findByName(nameClass);
        Teacher findTeacher = teacherRepository.findByEmail(email);
        findClass.getTeachers().remove(findTeacher);
        classRepository.save(findClass);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
