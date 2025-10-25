package microservice_ex.quizApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

import microservice_ex.quizApp.Service.QuestionService;
import microservice_ex.quizApp.Model.Question;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }
    @PostMapping("/addQuestion")
    public ResponseEntity<String> addQuestion(@Valid @RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/deleteQuestion/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }
    @PutMapping("/updateQuestion/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Long id, @Valid @RequestBody Question question) {
        return questionService.updateQuestion(id, question);
    }

}
