package microservice_ex.quizApp.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import microservice_ex.quizApp.Model.Question;
import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {
    // JpaRepository already provides findAll() method
    List<Question> findByCategory(String category); //findByCategory is a not a standard method in JpaRepository, but it is smart enough to find the questions by category
    
    
}
