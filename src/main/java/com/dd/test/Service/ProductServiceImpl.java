package com.dd.test.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dd.test.Dto.RequestProductDto;
import com.dd.test.Dto.ResponseProductDto;
import com.dd.test.Entity.Product;
import com.dd.test.Repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

   private final ProductRepository productRepository;

   ProductServiceImpl(ProductRepository productRepository) {
      this.productRepository = productRepository;
   }

   @Override
   public List<ResponseProductDto> getAllProducts() {
      return this.productRepository.findAll().stream().map(p->{
         ResponseProductDto dto = new ResponseProductDto();
         dto.setId(p.getId());
         dto.setName(p.getName());
         dto.setDescription(p.getDescription());
         dto.setPrice(p.getPrice());
         return dto;
      }
         
      ).collect(Collectors.toList());
   }

   @Override
   public ResponseProductDto getProductById(Long id) {
      var product=this.productRepository.findById(id)
      .orElseThrow(()-> new RuntimeException("product is not found"));
      ResponseProductDto dto = new ResponseProductDto();
      dto.setId(product.getId());
      dto.setName(product.getName());
      dto.setDescription(product.getDescription());
      dto.setPrice(product.getPrice());
      return dto;
   }

   @Override
   public ResponseProductDto CreateProduct(RequestProductDto requestProductDto) {
      Product product = new Product();
      product.setName(requestProductDto.getName());
      product.setDescription(requestProductDto.getDescription());
      product.setPrice(requestProductDto.getPrice());
      Product savedProduct= this.productRepository.save(product);

      ResponseProductDto responseProductDto = new ResponseProductDto();
      responseProductDto.setId(savedProduct.getId());
      responseProductDto.setName(savedProduct.getName());
      responseProductDto.setDescription(savedProduct.getDescription());
      responseProductDto.setPrice(savedProduct.getPrice());
      return responseProductDto;
   }

   @Override
   public String DeleteProduct(Long id) {
       var product=this.productRepository.findById(id)
      .orElseThrow(()-> new RuntimeException("product is not found"));
      this.productRepository.delete(product);
      return "product deleted";
   }

   @Override
   public ResponseProductDto UpdateProduct(Long id,RequestProductDto requestProductDto) {
      Product currentProduct =  this.productRepository.findById(id).orElseThrow(() -> new RuntimeException("produt is not found"));
      currentProduct.setName(requestProductDto.getName());
      currentProduct.setDescription(requestProductDto.getDescription());
      currentProduct.setPrice(requestProductDto.getPrice());
      Product savedProduct=this.productRepository.save(currentProduct);

      ResponseProductDto responseProductDto = new ResponseProductDto();
      responseProductDto.setId(savedProduct.getId());
      responseProductDto.setName(savedProduct.getName());
      responseProductDto.setDescription(savedProduct.getDescription());
      responseProductDto.setPrice(savedProduct.getPrice());
      return responseProductDto;

     
   }
}
