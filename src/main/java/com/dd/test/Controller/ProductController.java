package com.dd.test.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dd.test.Dto.ErrorResponseDto;
import com.dd.test.Dto.RequestProductDto;
import com.dd.test.Exceptions.ProductNotFoundException;
import com.dd.test.Service.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;
    
    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        try{
            return ResponseEntity.ok(productService.getAllProducts());
        }
        catch(RuntimeException e){
             return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
           return ResponseEntity.ok(productService.getProductById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> RemoveProduct(@PathVariable Long id){
            return ResponseEntity.ok(productService.DeleteProduct(id));    
    }

    @PostMapping("/create")
    public ResponseEntity<?> CreateProduct(@Valid @RequestBody RequestProductDto product) {
            return ResponseEntity.ok(productService.CreateProduct(product));
    }

   @PutMapping("/update/{id}")
   public ResponseEntity<?> UpdateProduct(@PathVariable Long id,@Valid @RequestBody RequestProductDto product) {
            return ResponseEntity.ok(productService.UpdateProduct(id,product));    
   }


   @ExceptionHandler(ProductNotFoundException.class)
   public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException e){
      ErrorResponseDto error=new ErrorResponseDto(LocalDateTime.now(), e.getMessage(), new ArrayList<ObjectError>());
      return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
      ErrorResponseDto error=new ErrorResponseDto(LocalDateTime.now(), "field not valid", ex.getAllErrors()); 
      return ResponseEntity.badRequest().body(error);
    }
    
    
}
