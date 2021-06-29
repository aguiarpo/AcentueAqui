package com.pac.acentueaqui.endpoints;

import com.pac.acentueaqui.models.Class;
import com.pac.acentueaqui.models.questions.Accentuation;
import com.pac.acentueaqui.models.questions.Question;
import com.pac.acentueaqui.models.users.Teacher;
import com.pac.acentueaqui.repository.AccentuatuionRepository;
import com.pac.acentueaqui.repository.ClassRepository;
import com.pac.acentueaqui.repository.QuestionRepository;
import com.pac.acentueaqui.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("v1")
public class QuestionEndpoint {
    private final QuestionRepository questionRepository;
    private final AccentuatuionRepository accentuatuionRepository;
    private final ClassRepository classRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public QuestionEndpoint(QuestionRepository questionRepository,
                            AccentuatuionRepository accentuatuionRepository,
                            TeacherRepository teacherRepository,
                            ClassRepository classRepository) {
        this.questionRepository = questionRepository;
        this.accentuatuionRepository = accentuatuionRepository;
        this.teacherRepository = teacherRepository;
        this.classRepository = classRepository;
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
        Teacher teacher = teacherRepository.findByEmail(auth.getUsername());
        question.setTeacher(teacher);
        Timestamp dataDeHoje = new Timestamp(System.currentTimeMillis());
        question.setInitialDate(dataDeHoje);
        question.setFinalDate(dataDeHoje);
        Question saved = questionRepository.save(question);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping(path = "school/findemail/question")
    public ResponseEntity<?> findClassEmail(@AuthenticationPrincipal UserDetails auth){
        List<Question> find = questionRepository.findByTeacherEmail(auth.getUsername());
        return new ResponseEntity<>(find, HttpStatus.OK);
    }
}
