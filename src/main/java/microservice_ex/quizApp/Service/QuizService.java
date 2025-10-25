package microservice_ex.quizApp.Service;

import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import microservice_ex.quizApp.dao.QuizDao;
import microservice_ex.quizApp.Model.Question;
import java.util.List;
import microservice_ex.quizApp.dao.QuestionDao;
import microservice_ex.quizApp.Model.Quiz;


@Service
public class QuizService {
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<String>("Quiz created successfully", HttpStatus.CREATED);
    }
}
