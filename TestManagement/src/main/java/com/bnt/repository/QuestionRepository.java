package com.bnt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bnt.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{

    @Query("SELECT q FROM Question q WHERE q.question = :question")
    List<Question> findByName(@Param ("question") String question);

    Optional<Question> findByQuestion(String question);
}
