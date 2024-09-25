package com.bnt.service.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bnt.exception.CategoryNotFoundException;
import com.bnt.exception.QuestionAlreadyPresentException;
import com.bnt.exception.QuestionNotFoundException;
import com.bnt.exception.SubcategoryNotFoundException;
import com.bnt.model.Category;
import com.bnt.model.Question;
import com.bnt.model.Subcategory;
import com.bnt.repository.CategoryRepository;
import com.bnt.repository.QuestionRepository;
import com.bnt.repository.SubcategoryRepository;
import com.bnt.service.QuestionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Question createQuestion(Question question) {
        String questions = question.getQuestion();
        List<Question> existingQuestions = questionRepository.findByName(questions);
        if (!existingQuestions.isEmpty()) {
            throw new QuestionAlreadyPresentException("Question is already present");
        }

        String subcategoryName = question.getSubcategory().getSubcategoryName();
        Optional<Subcategory> optionalSubcategory = subcategoryRepository.findBySubcategoryName(subcategoryName);
        Subcategory subcategory = optionalSubcategory.orElseThrow(() -> new SubcategoryNotFoundException("Subcategory '" + subcategoryName + "' is not present"));
        question.setSubcategory(subcategory);

        String categoryName = subcategory.getCategory().getCategoryName();
        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(categoryName);
        Category category = optionalCategory.orElseThrow(() -> new CategoryNotFoundException("Category '" + categoryName + "' is not present"));
        subcategory.setCategory(category);

        log.info("Question is created");
        return questionRepository.save(question);   
    }

    @Override
    public List<Question> getAllQuestion() {
        try {
            log.info("All list of question is retrieved");
            return questionRepository.findAll();
        } catch (Exception ex) {
            throw new QuestionNotFoundException("Question table is empty");
        }  
         
    }

    @Override
    public Optional<Question> getQuestionById(int questionId){
        try {
            log.info("Question with id{} is retrieved" + questionId);
            return questionRepository.findById(questionId);
        } catch (Exception ex) {
            throw new QuestionNotFoundException("Question of id " + questionId + " not found");
        }
    }

    @Override
    public void deleteQuestion(int questionId){
        Optional<Question> existingQuestion= questionRepository.findById(questionId);
        if(!existingQuestion.isPresent()){
            throw new QuestionNotFoundException("Question not found with id : " + questionId);
        }
        log.info("Questio with id{} is deleted", questionId);
        questionRepository.deleteById(questionId);
    }

    @Override
    public Question updateQuestion(Integer questionId, Question updateQuestion){
        Optional<Question> existinqQuestion = questionRepository.findById(questionId);
        if(existinqQuestion.isPresent()){
            Question question = existinqQuestion.get();
            question.setQuestion(updateQuestion.getQuestion());
            question.setOptionOne(updateQuestion.getOptionOne());
            question.setOptionTwo(updateQuestion.getOptionTwo());
            question.setOptionThree(updateQuestion.getOptionThree());
            question.setOptionFour(updateQuestion.getOptionFour());
            question.setCorrectOption(updateQuestion.getCorrectOption());
            question.setPositiveMark(updateQuestion.getPositiveMark());
            question.setNegativeMark(updateQuestion.getNegativeMark());
            question.setSubcategory(updateQuestion.getSubcategory());
            log.info("Question with id{} is updated", questionId);
            return questionRepository.save(question);
        }
        else {
            throw new QuestionNotFoundException("Question not found with id " + questionId);
        }
    }

    @Override
    public List<Question> uploadQuestions(MultipartFile file) {
        List<Question> questions = new ArrayList<>();
        Workbook workbook = null;
        List<Question> databaseCreatedQuestions = new ArrayList<>();
		try {
			workbook = new XSSFWorkbook(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0); 
           
	        for (Row row : sheet) {
	            if (row.getRowNum() == 0) { 
	                continue;
	            }
 
	            Question question = new Question();
	            question.setQuestion(getStringValueFromCell(row.getCell(3)));
	            question.setOptionOne(getStringValueFromCell(row.getCell(4)));
	            question.setOptionTwo(getStringValueFromCell(row.getCell(5)));
	            question.setOptionThree(getStringValueFromCell(row.getCell(6)));
	            question.setOptionFour(getStringValueFromCell(row.getCell(7)));
	            question.setCorrectOption(getStringValueFromCell(row.getCell(8)));
	            question.setPositiveMark(getStringValueFromCell(row.getCell(9)));
	            question.setNegativeMark(getStringValueFromCell(row.getCell(10)));
 
            // Retrieve or create Category
            String categoryName = getStringValueFromCell(row.getCell(1));
            List<Category> category = categoryRepository.findByName(categoryName);
            if (category.isEmpty()) {
                return new ArrayList<Question>();
            }

            // Retrieve or create Subcategory
            String subcategoryName = getStringValueFromCell(row.getCell(2));
            List<Subcategory> subcategory = subcategoryRepository.findByName(subcategoryName);
            if (subcategory.isEmpty()) {
                return new ArrayList<Question>();
            }

            question.setSubcategory(subcategory.getFirst());
            questions.add(question);
        }
 
	        workbook.close();
 
	        for (Question question : questions) {
	            Question que = createQuestion(question);
	            databaseCreatedQuestions.add(que);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                   System.out.println("Error in closing workbook");
                }
            }
        }
        return databaseCreatedQuestions;
    }
 
    private String getStringValueFromCell(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim(); // Trim to remove leading/trailing spaces
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue()); // Convert numeric value to string
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()); // Convert boolean value to string
            case FORMULA:
                return cell.getCellFormula(); // Return formula as string
            default:
                return null; // Handle other cell types as needed
        }
    }
 
}
