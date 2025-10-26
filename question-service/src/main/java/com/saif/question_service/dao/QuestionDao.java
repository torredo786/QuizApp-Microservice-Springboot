package com.saif.question_service.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.saif.question_service.Model.Question;
import java.util.List;

    

@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {
    // JpaRepository already provides findAll() method
    List<Question> findByCategory(String category); //findByCategory is a not a standard method in JpaRepository, but it is smart enough to find the questions by category
    
    @Query(value = "SELECT q.id FROM question q where q.category = :category order by RANDOM() limit :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, Integer numQ);
    
}
