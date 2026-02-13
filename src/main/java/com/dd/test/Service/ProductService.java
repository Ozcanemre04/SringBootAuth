package com.dd.test.Service;

import java.util.List;

import com.dd.test.Dto.RequestProductDto;
import com.dd.test.Dto.ResponseProductDto;


public interface ProductService {

    List<ResponseProductDto> getAllProducts();
    
    ResponseProductDto getProductById(Long id);
    
    ResponseProductDto CreateProduct(RequestProductDto requestProductDto);

    ResponseProductDto UpdateProduct(Long id,RequestProductDto requestProductDto);

    String DeleteProduct(Long id);

} 
