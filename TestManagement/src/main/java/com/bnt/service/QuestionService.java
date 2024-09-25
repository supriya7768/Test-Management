package com.bnt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.bnt.model.Question;

public interface QuestionService {

    public Question createQuestion(Question question);

    public List<Question> getAllQuestion();

    public Optional<Question> getQuestionById(int questionId);

    public void deleteQuestion(int questionId);

    public Question updateQuestion(Integer questionId, Question updateQuestion);

    public List<Question> uploadQuestions(MultipartFile file);
    
} 

