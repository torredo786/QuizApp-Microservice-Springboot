package microservice_ex.quizApp.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import microservice_ex.quizApp.Model.Quiz;
import java.util.List;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Long> {

}
