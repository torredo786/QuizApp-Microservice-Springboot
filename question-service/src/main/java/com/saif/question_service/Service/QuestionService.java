package com.saif.question_service.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.saif.question_service.dao.QuestionDao;
import com.saif.question_service.Model.Question;
import com.saif.question_service.Model.Response;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.saif.question_service.Model.QuestionWrapper;
import java.util.ArrayList;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionDao.findAll();
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        List<Question> questions = questionDao.findByCategory(category);
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }
    public ResponseEntity<String> addQuestion(Question question) {
        try {
            if (question.getQuestionTitle() == null || question.getQuestionTitle().trim().isEmpty()) {
                return new ResponseEntity<String>("Question title cannot be empty", HttpStatus.BAD_REQUEST);
            }
            if (question.getRightAnswer() == null || question.getRightAnswer().trim().isEmpty()) {
                return new ResponseEntity<String>("Right answer cannot be empty", HttpStatus.BAD_REQUEST);
            }
            questionDao.save(question);
            return new ResponseEntity<String>("Question added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Error adding question: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<String> deleteQuestion(Long id) {
        try {
            if (!questionDao.existsById(id)) {
                return new ResponseEntity<String>("Question not found with id: " + id, HttpStatus.NOT_FOUND);
            }
            questionDao.deleteById(id);
            return new ResponseEntity<String>("Question deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Error deleting question: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<String> updateQuestion(Long id, Question question) {
        try {
            Optional<Question> existingQuestionOpt = questionDao.findById(id);
            if (!existingQuestionOpt.isPresent()) {
                return new ResponseEntity<String>("Question not found with id: " + id, HttpStatus.NOT_FOUND);
            }
            
            if (question.getQuestionTitle() == null || question.getQuestionTitle().trim().isEmpty()) {
                return new ResponseEntity<String>("Question title cannot be empty", HttpStatus.BAD_REQUEST);
            }
            if (question.getRightAnswer() == null || question.getRightAnswer().trim().isEmpty()) {
                return new ResponseEntity<String>("Right answer cannot be empty", HttpStatus.BAD_REQUEST);
            }
            
            Question existingQuestion = existingQuestionOpt.get();
            existingQuestion.setQuestionTitle(question.getQuestionTitle());
            existingQuestion.setOption1(question.getOption1());
            existingQuestion.setOption2(question.getOption2());
            existingQuestion.setOption3(question.getOption3());
            existingQuestion.setOption4(question.getOption4());
            existingQuestion.setRightAnswer(question.getRightAnswer());
            existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
            existingQuestion.setCategory(question.getCategory());
            questionDao.save(existingQuestion);
            return new ResponseEntity<String>("Question updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Error updating question: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        for (Integer id : questionIds) {
            Optional<Question> questionOpt = questionDao.findById(id.longValue());
            if (questionOpt.isPresent()) {
                Question question = questionOpt.get();
                QuestionWrapper wrapper = new QuestionWrapper();
                wrapper.setId(question.getId());
                wrapper.setQuestionTitle(question.getQuestionTitle());
                wrapper.setOption1(question.getOption1());
                wrapper.setOption2(question.getOption2());
                wrapper.setOption3(question.getOption3());
                wrapper.setOption4(question.getOption4());
                wrappers.add(wrapper);
            }
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }   

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        
        int score = 0;
        for (Response response : responses) {
            Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer())) {
                score++;
            }
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
