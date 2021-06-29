package com.pac.acentueaqui.endpoints;

import com.pac.acentueaqui.models.users.School;
import com.pac.acentueaqui.models.users.Teacher;
import com.pac.acentueaqui.repository.SchoolRepository;
import com.pac.acentueaqui.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1")
public class SchoolTeacherEndpoint {
    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public SchoolTeacherEndpoint(SchoolRepository schoolRepository,
                                 TeacherRepository teacherRepository) {
        this.schoolRepository = schoolRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping(path = "school/findemail/teacher/{email}")
    public ResponseEntity<?> findEmailTeacher(@PathVariable String email){
        Teacher find = teacherRepository.findByEmail(email);
        return new ResponseEntity<>(find, HttpStatus.OK);
    }

    @DeleteMapping(path = "school/delete/teacher/{emailTeacher}")
    public ResponseEntity<?> DeleteEmailTeacher(@PathVariable String emailTeacher,
                                                @AuthenticationPrincipal UserDetails auth){
        School findSchool = schoolRepository.findByEmail(auth.getUsername());
        Teacher findTeacher = teacherRepository.findByEmail(emailTeacher);
        findSchool.getTeachers().remove(findTeacher);
        schoolRepository.save(findSchool);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "school/add/teacher/{emailTeacher}")
    public ResponseEntity<?> create(@PathVariable String emailTeacher,
                                    @AuthenticationPrincipal UserDetails auth){
        School findSchool = schoolRepository.findByEmail(auth.getUsername());
        Teacher findTeacher = teacherRepository.findByEmail(emailTeacher);
        for(Teacher teacher : findSchool.getTeachers()){
            if(teacher.getEmail().equals(emailTeacher)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        List<Teacher> teacherList = findSchool.getTeachers() == null ?
                new ArrayList<>() : findSchool.getTeachers();
        teacherList.add(findTeacher);
        findSchool.setTeachers(teacherList);
        School saved = schoolRepository.save(findSchool);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
}
