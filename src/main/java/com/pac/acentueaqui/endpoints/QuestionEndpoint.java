package com.pac.acentueaqui.endpoints;

import com.pac.acentueaqui.models.Class;
import com.pac.acentueaqui.models.questions.Accentuation;
import com.pac.acentueaqui.models.questions.Question;
import com.pac.acentueaqui.models.questions.QuestionStudent;
import com.pac.acentueaqui.models.users.Student;
import com.pac.acentueaqui.models.users.Teacher;
import com.pac.acentueaqui.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class QuestionEndpoint {
    private final QuestionRepository questionRepository;
    private final AccentuatuionRepository accentuatuionRepository;
    private final ClassRepository classRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final QuestionStudentRepository questionStudentRepository;

    @Autowired
    public QuestionEndpoint(QuestionRepository questionRepository,
                            AccentuatuionRepository accentuatuionRepository,
                            TeacherRepository teacherRepository,
                            ClassRepository classRepository,
                            StudentRepository studentRepository,
                            QuestionStudentRepository questionStudentRepository) {
        this.questionRepository = questionRepository;
        this.accentuatuionRepository = accentuatuionRepository;
        this.teacherRepository = teacherRepository;
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.questionStudentRepository = questionStudentRepository;
    }

    @PostMapping(path = "teacher/add/question")
    public ResponseEntity<?> addQuestion(@RequestBody Question question,
                                         @AuthenticationPrincipal UserDetails auth){
        for(Class c : question.getClasses()){
            Class findClass = classRepository.findByName(c.getName());
            c.setCode(findClass.getCode());
        }
        for(Accentuation a : question.getAccentuations()){
            Accentuation findAccentuation = accentuatuionRepository.findByName(a.getName());
            a.setCode(findAccentuation.getCode());
        }
        question.getInitialDate().plusDays(1);
        question.getFinalDate().plusDays(1);
        Teacher teacher = teacherRepository.findByEmail(auth.getUsername());
        question.setTeacher(teacher);
        Question saved = questionRepository.save(question);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PostMapping(path = "student/add/questionStudent/{code}")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionStudent questionStudent, @PathVariable Long code, @AuthenticationPrincipal UserDetails auth){
        Question question = questionRepository.findByCode(code);
        Student student = studentRepository.findByRegistration(auth.getUsername());
        questionStudent.setStudent(student);
        questionStudent.setQuestion(question);
        QuestionStudent saved = questionStudentRepository.save(questionStudent);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PutMapping(path = "teacher/save/question")
    public ResponseEntity<?> edit(@RequestBody Question question,
                                         @AuthenticationPrincipal UserDetails auth){
        for(Class c : question.getClasses()){
            Class findClass = classRepository.findByName(c.getName());
            c.setCode(findClass.getCode());
        }
        for(Accentuation a : question.getAccentuations()) {
            Accentuation findAccentuation = accentuatuionRepository.findByName(a.getName());
            a.setCode(findAccentuation.getCode());
        }
        question.getInitialDate().plusDays(1);
        question.getFinalDate().plusDays(1);
        Teacher teacher = teacherRepository.findByEmail(auth.getUsername());
        question.setTeacher(teacher);
        Question saved = questionRepository.save(question);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping(path = "teacher/findemail/question")
    public ResponseEntity<?> findClassEmail(@AuthenticationPrincipal UserDetails auth){
        List<Question> find = questionRepository.findByTeacherEmail(auth.getUsername());
        return new ResponseEntity<>(find, HttpStatus.OK);
    }

    @GetMapping(path = "student/findcode/question/class")
    public ResponseEntity<?> findClassCode(@AuthenticationPrincipal UserDetails auth){
        Student find = studentRepository.findByRegistration(auth.getUsername());
        List<Question> questions = questionRepository.findByClassCode(find.getClasse().getCode(),
                find.getCode());
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping(path = "teacher/findchart/question/class")
    public ResponseEntity<?> findChartClass(@AuthenticationPrincipal UserDetails auth){
        Teacher find = teacherRepository.findByEmail(auth.getUsername());
        List chartClasses = questionStudentRepository.findChartsClass(find.getCode());
        return new ResponseEntity<>(chartClasses, HttpStatus.OK);
    }

    @GetMapping(path = "teacher/findchart/question/student")
    public ResponseEntity<?> findChartStudent(@AuthenticationPrincipal UserDetails auth){
        Teacher find = teacherRepository.findByEmail(auth.getUsername());
        List chartClasses = questionStudentRepository.findChartsStudent(find.getCode());
        return new ResponseEntity<>(chartClasses, HttpStatus.OK);
    }

    @GetMapping(path = "student/findid/question/{code}")
    public ResponseEntity<?> findCode(@PathVariable Long code){
        Question question = questionRepository.findByCode(code);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @GetMapping(path = "student/find/student/xp")
    public ResponseEntity<?> findStudent(@AuthenticationPrincipal UserDetails auth){
        Student student = studentRepository.findByRegistration(auth.getUsername());
        return new ResponseEntity<>(questionStudentRepository.findStudent(student.getCode()), HttpStatus.OK);
    }


    @DeleteMapping(path = "teacher/delete/question/{code}")
    public ResponseEntity<?> delete(@PathVariable Long code){
        Question question = questionRepository.findByCode(code);
        questionRepository.delete(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
