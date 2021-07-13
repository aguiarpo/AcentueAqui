package com.pac.acentueaqui.repository;

import com.pac.acentueaqui.models.questions.QuestionStudent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionStudentRepository extends PagingAndSortingRepository<QuestionStudent, Long> {
    @Query(value = "SELECT b.word, s.name, sum(attempts) as attempts, " +
            "(sum(hour) * 3600) + (sum(min) * 60) + sum(second) as second, sum(xp) as xp, " +
            "COUNT(CASE WHEN right_answer THEN 1 END) as right, c.class_id as classe, q.question_id as question " +
            "FROM question_student q INNER JOIN class_question c ON q.question_id = c.question_id " +
            "INNER JOIN question b ON q.question_id = b.id " +
            "INNER JOIN class s on c.class_id = s.id " +
            "WHERE b.teacher_id = :code " +
            "GROUP BY q.question_id, c.class_id, s.name, b.word", nativeQuery = true)
    List<Chart> findChartsClass(@Param("code") Long code);
    @Query(value = "SELECT b.word, s.name, sum(attempts) as attempts, " +
            "(sum(hour) * 3600) + (sum(min) * 60) + sum(second) as second, sum(xp) as xp, " +
            "COUNT(CASE WHEN right_answer THEN 1 END) as right, e.classe_id as classe, q.question_id as question, u.name as student " +
            "FROM question_student q INNER JOIN class_question c ON q.question_id = c.question_id " +
            "INNER JOIN question b ON q.question_id = b.id " +
            "INNER JOIN student as e ON q.student_id = e.id " +
            "INNER JOIN public.user as u ON e.user_id = u.id " +
            "INNER JOIN class s on e.classe_id = s.id " +
            "WHERE b.teacher_id = 5 " +
            "GROUP BY q.student_id, u.name, q.question_id, b.word, e.classe_id, s.name;", nativeQuery = true)
    List<Chart> findChartsStudent(@Param("code") Long code);
    @Query(value = "SELECT sum(xp) as xp " +
            "FROM public.question_student " +
            "WHERE student_id = :code " +
            "group by student_id;", nativeQuery = true)
    Chart findStudent(@Param("code") Long code);
    interface Chart {

        String getWord();
        String getName();
        Long getAttempts();
        Long getSecond();
        Long getXp();
        Long getRight();
        Long getClasse();
        Long getQuestion();
        String getStudent();
    }
}
