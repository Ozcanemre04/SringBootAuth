package com.dd.test.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dd.test.Dto.RequestProductDto;
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
       try{
           return ResponseEntity.ok(productService.getProductById(id));

       }
       catch(RuntimeException e){
           if(e.getMessage().contains("found")){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
           }
           else{
               return ResponseEntity.badRequest().body(e.getMessage());
           }
       }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> RemoveProduct(@PathVariable Long id){
        try {
            return ResponseEntity.ok(productService.DeleteProduct(id));
            
        } catch (RuntimeException e) {
            
            if(e.getMessage().contains("found")){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
           }
           else{
               return ResponseEntity.badRequest().body(e.getMessage());
           }
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> CreateProduct(@Valid @RequestBody RequestProductDto product) {
        try{

            return ResponseEntity.ok(productService.CreateProduct(product));
        }
        catch(RuntimeException e){
             return ResponseEntity.badRequest().body(e.getMessage());
        }
       

    }

   @PutMapping("/update/{id}")
   public ResponseEntity<?> UpdateProduct(@PathVariable Long id,@Valid @RequestBody RequestProductDto product) {
       try {
            return ResponseEntity.ok(productService.UpdateProduct(id,product));
            
        } catch (RuntimeException e) {
            if(e.getMessage().contains("found")){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
           }
           else{
               return ResponseEntity.badRequest().body(e.getMessage());
           }
        }
       
   }
    
    
}
