package microservice_ex.quizApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import microservice_ex.quizApp.Service.QuizService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import microservice_ex.quizApp.Model.QuestionWrapper;
import microservice_ex.quizApp.Model.Response;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ,
            @RequestParam String title) {
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("/getQuiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Long id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Long id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }
}
