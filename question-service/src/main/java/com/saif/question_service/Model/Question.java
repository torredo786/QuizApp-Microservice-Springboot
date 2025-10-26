package com.saif.question_service.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Question title is required")
    private String questionTitle;
    
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    
    @NotBlank(message = "Right answer is required")
    private String rightAnswer;
    
    private String difficultyLevel;
    private String category;
}
