package com.bnt.serviceTest;

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

import com.bnt.model.Category;
import com.bnt.model.Question;
import com.bnt.model.Subcategory;
import com.bnt.repository.QuestionRepository;
import com.bnt.service.serviceImpl.QuestionServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    QuestionServiceImpl questionService;

    @Test
    void testCreateQuestion(){
         Question expectedResult = new Question(1, "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus","@Controller and @ResponseBody","3", "-1",new Subcategory(1, "Exception Handling", "Exception Handling Form in java", new Category(1, "Java", "Core java Category")));
       when(questionRepository.save(expectedResult)).thenReturn(expectedResult);
        Question actualResult = questionService.createQuestion(expectedResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllQuestion(){
        List<Question> question = new ArrayList();
        question.add(new Question(1, "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus","@Controller and @ResponseBody","3", "-1",new Subcategory(1, "Exception Handling", "Exception Handling Form in java", new Category(1, "Java", "Core java Category"))));
        question.add(new Question(1, "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus","@Controller and @ResponseBody","3", "-1",new Subcategory(1, "Exception Handling", "Exception Handling Form in java", new Category(1, "Java", "Core java Category"))));
        when(questionRepository.findAll()).thenReturn(question);
        List<Question> actualResult = questionService.getAllQuestion();
        assertEquals(question, actualResult);
    }

    @Test
    void testGetQuestionById(){
        Optional<Question> question = Optional.of(new Question(1, "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus","@Controller and @ResponseBody","3", "-1",new Subcategory(1, "Exception Handling", "Exception Handling Form in java", new Category(1, "Java", "Core java Category"))));
        when(questionRepository.findById(1)).thenReturn(question);
        Optional<Question> actualResult = questionService.getQuestionById(1);
        assertEquals(question, actualResult);
    }

    @Test
    void testUpdateQuestion() {
        int questionId = 1;
        Question existingQuestion = new Question(questionId, "Original Question", "Option 1", "Option 2", "Option 3", "Option 4", "Correct Option", "1", "-1", new Subcategory(1, "Subcategory Name", "Subcategory Description", new Category(1, "Category Name", "Category Description")));
        Question updatedQuestion = new Question(questionId, "Updated Question", "Updated Option 1", "Updated Option 2", "Updated Option 3", "Updated Option 4", "Updated Correct Option", "2", "-2", new Subcategory(1, "Subcategory Name", "Subcategory Description", new Category(1, "Category Name", "Category Description")));
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(existingQuestion));
        when(questionRepository.save(existingQuestion)).thenReturn(updatedQuestion);
        Question actualResult = questionService.updateQuestion(questionId, updatedQuestion);
        assertEquals(updatedQuestion, actualResult);
        verify(questionRepository).findById(questionId);
        verify(questionRepository).save(existingQuestion);
    }

    @Test
    void testDeleteQuestion(){
        questionService.deleteQuestion(1);
        verify(questionRepository).deleteById(1);
    }
    
}
