package com.pac.acentueaqui.repository;

import com.pac.acentueaqui.models.questions.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    List<Question> findByTeacherEmail(String email);
    Question findByCode(Long code);
    @Query(value = "SELECT id, amount_of_attempt, final_date, initial_date, tip, valuexp, word, teacher_id " +
            "FROM question q INNER JOIN class_question c on q.id = c.question_id " +
            "WHERE class_id = :code " +
            "AND q.id NOT IN(SELECT question_id FROM question_student WHERE student_id = :id)", nativeQuery = true)
    List<Question> findByClassCode(@Param("code") Long code, @Param("id") Long id);
}
