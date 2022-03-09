package com.pac.acentueaqui.repository;

import com.pac.acentueaqui.models.questions.Question;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    List<Question> findByTeacherEmail(String email);
}
