package com.case6.quizchallengeweb.controller;
import com.case6.quizchallengeweb.model.question.Category;
import com.case6.quizchallengeweb.service.question.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;


    @GetMapping
    public ResponseEntity<Iterable<Category>>findAllCategory(){
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Category> insertCategory(@Valid @RequestBody Category category){
        categoryService.save(category);
        return new ResponseEntity<>(category, HttpStatus.ACCEPTED);
    }

    @DeleteMapping()
    public  ResponseEntity<Category>deleteCategory(@RequestParam long id){
        try {
            Category byId = categoryService.findById(id).get();
            categoryService.delete(id);
            return new ResponseEntity<>(byId, HttpStatus.OK);
        }catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Provide correct Actor Id", exception);
        }





}

    //caretory handler exeption
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            ((HashMap) errors).put(fieldName, errorMessage);
        });
        return errors;
    }



}
