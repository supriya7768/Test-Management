package com.bnt.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bnt.controller.QuestionController;
import com.bnt.model.Category;
import com.bnt.model.Question;
import com.bnt.model.Subcategory;
import com.bnt.repository.QuestionRepository;
import com.bnt.service.serviceImpl.QuestionServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class QuestionControllerTest {

    @Mock
    QuestionRepository questionRepository;

    @Mock
    QuestionServiceImpl questionService;

    @InjectMocks
    QuestionController questionController;

    @Test
    void testCreateQuestion(){
        Question question = new Question(1, "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus","@Controller and @ResponseBody","3", "-1",new Subcategory(1, "Exception Handling", "Exception Handling Form in java", new Category(1, "Java", "Core java Category")));
        ResponseEntity<Question> expectedResult = ResponseEntity.status(HttpStatus.CREATED).body(question);
        when(questionService.createQuestion(question)).thenReturn(question);
        ResponseEntity<Question> actualResult = questionController.createQuestion(question);
        assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
        assertEquals(expectedResult.getBody(), actualResult.getBody());
    }

    @Test
    void testGetAllQuestion(){
        List<Question> question = new ArrayList();
        question.add(new Question(1, "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus","@Controller and @ResponseBody","3", "-1",new Subcategory(1, "Exception Handling", "Exception Handling Form in java", new Category(1, "Java", "Core java Category"))));
        question.add(new Question(1, "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus","@Controller and @ResponseBody","3", "-1",new Subcategory(1, "Exception Handling", "Exception Handling Form in java", new Category(1, "Java", "Core java Category"))));
        ResponseEntity<List<Question>> expectedResult = ResponseEntity.status(HttpStatus.FOUND).body(question);
        when(questionService.getAllQuestion()).thenReturn(question);
        ResponseEntity<List<Question>> actualResult = questionController.getAllQuestion();
        assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
        assertEquals(expectedResult.getBody(), actualResult.getBody());
    }

    @Test
    void testGetById(){
        Optional<Question> question = Optional.of(new Question(1, "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus","@Controller and @ResponseBody","3", "-1",new Subcategory(1, "Exception Handling", "Exception Handling Form in java", new Category(1, "Java", "Core java Category"))));
        ResponseEntity<Optional<Question>> expectedResult = ResponseEntity.status(HttpStatus.FOUND).body(question);
        when(questionService.getQuestionById(1)).thenReturn(question);
        ResponseEntity<Optional<Question>> actualResult = questionController.getQuestionById(1);
        assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
        assertEquals(expectedResult.getBody(), actualResult.getBody());   
    }

    @Test
    void testUpdateQuestion(){
        Question question = new Question(1, "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus","@Controller and @ResponseBody","3", "-1",new Subcategory(1, "Exception Handling", "Exception Handling Form in java", new Category(1, "Java", "Core java Category")));
        when(questionService.updateQuestion(1, question)).thenReturn(question);
        Question actualResult = questionController.updateQuestion(1, question);
        assertEquals(question, actualResult);
    }

    @Test
    void testDeleteQuestion(){
        ResponseEntity<String> expectedResult = questionController.deleteQuestion(3);
        verify(questionService).deleteQuestion(3);
        assertEquals(HttpStatus.OK, expectedResult.getStatusCode());
        assertEquals("Deleted the question with id : 3", expectedResult.getBody());
    }
    
}

