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
import microservice_ex.quizApp.Model.QuestionWrapper;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.ArrayList;
import microservice_ex.quizApp.Model.Response;

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

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Long id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        if (!quiz.isPresent()) {
            return new ResponseEntity<List<QuestionWrapper>>(HttpStatus.NOT_FOUND);
        }
        List<Question> questions = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();
        for (Question question : questions) {
            QuestionWrapper questionWrapper = new QuestionWrapper();
            questionWrapper.setId(question.getId());
            questionWrapper.setQuestionTitle(question.getQuestionTitle());
            questionWrapper.setOption1(question.getOption1());
            questionWrapper.setOption2(question.getOption2());
            questionWrapper.setOption3(question.getOption3());
            questionWrapper.setOption4(question.getOption4());
            questionForUser.add(questionWrapper);
        }
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }
    public ResponseEntity<Integer> calculateResult(Long id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).orElse(null);
        if (quiz == null) {
            return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
        }
        List<Question> questions = quiz.getQuestions();
        int score = 0;
        int i=0;
        for (Response response : responses) {
            if(response.getResponse().equals(questions.get(i).getRightAnswer())) {
                score++;
            }
            i++;   
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
