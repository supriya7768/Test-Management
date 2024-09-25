package com.bnt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bnt.model.Question;
import com.bnt.service.serviceImpl.QuestionServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/question")
public class QuestionController {

    @Autowired
    private QuestionServiceImpl questionService;

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question){
        log.info("Question is to be created");
        Question createQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(createQuestion);
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestion(){
        log.info("All question are to be retrieved");
        List<Question> getAllQuestion = questionService.getAllQuestion();
        return ResponseEntity.status(HttpStatus.FOUND).body(getAllQuestion);
    }

    @GetMapping("/{id}")
    public ResponseEntity <Optional<Question>> getQuestionById(@PathVariable("id") int questionId){
        log.info("Question of id {} is retrived", questionId);
        Optional<Question> getQuestionById = questionService.getQuestionById(questionId);
        return ResponseEntity.status(HttpStatus.FOUND).body(getQuestionById);
    }

    @PutMapping("/{id}")
    public Question updateQuestion(@PathVariable("id") int questionId,@RequestBody Question question){
        log.info("Question of id{} is to be updated", questionId);
        return questionService.updateQuestion(questionId, question);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") int questionId){
        log.info("Question of id{} is to be deleted", questionId);
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok("Deleted the question with id : " + questionId);
    }

    @PostMapping("/upload")
    public ResponseEntity<List<Question>> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Question> questions = questionService.uploadQuestions(file);
		return ResponseEntity.status(HttpStatus.CREATED).body(questions);
    }
   
}
