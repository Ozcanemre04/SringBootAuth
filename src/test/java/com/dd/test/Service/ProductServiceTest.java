package com.dd.test.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dd.test.Dto.RequestProductDto;
import com.dd.test.Dto.ResponseProductDto;
import com.dd.test.Entity.Product;
import com.dd.test.Exceptions.ProductNotFoundException;
import com.dd.test.Repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;
    private List<Product> products;
    @BeforeEach
    public void init(){
         products = List.of(
            new Product(1L, "Phone","description",999.99),
            new Product(2L, "laptop","description",1240.99)

        );
    }
    @Test
    void shouldReturnAllProducts(){
      when(productRepository.findAll()).thenReturn(products);
      List<ResponseProductDto> result = productService.getAllProducts();
      assertEquals(24, result.size());
      verify(productRepository).findAll();
    }

    //getProdcutById
    @Test
    void getProductByIdShouldReturnProductById(){
       var currentProduct = products.stream().filter(p -> p.getId()==1L).findFirst();
       when(productRepository.findById(1L)).thenReturn(currentProduct);
       ResponseProductDto result = productService.getProductById(1L);
       assertEquals(1L, result.getId());
       assertEquals("Phone", result.getName());
       assertEquals(999.99, result.getPrice());
    }

    @Test
    void getProductByIdShouldThrowExceptionWhenProductNotFound(){
       when(productRepository.findById(4L)).thenReturn(Optional.empty());
       assertThrows(ProductNotFoundException.class, ()->productService.getProductById(4L));
    }
    //delete product
    @Test
    void DeleteProductShouldThrowExceptionWhenProductNotFound(){
      
       when(productRepository.findById(4L)).thenReturn(Optional.empty());
       assertThrows(ProductNotFoundException.class, ()->productService.DeleteProduct(4L));
    }
    @Test
    void DeleteProductShouldDeleteProduct(){
       Product product =  new Product(1L, "Phone","description",999.99);
       when(productRepository.findById(1L)).thenReturn(Optional.of(product));
       String result = productService.DeleteProduct(1L);
       verify(productRepository).delete(product);
       assertEquals("product deleted", result);
    }
    //create Product
    @Test
    void CreateProductShouldReturnResponseProductResponseDto(){
        Product product =  new Product(3L, "nothing","description",9.99);
        RequestProductDto createProduct = new RequestProductDto();
        createProduct.setName("nothing");
        createProduct.setDescription("description");
        createProduct.setPrice(9.99);
        when(productRepository.save(any())).thenReturn(product);
        ResponseProductDto result = productService.CreateProduct(createProduct);
        assertEquals("nothing", result.getName());
        assertEquals(9.99, result.getPrice());
    }
    //updateProduct
    @Test
    void UpdateProductShouldReturnResponseProductResponseDto(){
        Product product =  new Product(1L, "Phone","description",999.99);
        RequestProductDto createProduct = new RequestProductDto();
        createProduct.setName("nothing");
        createProduct.setDescription("description");
        createProduct.setPrice(9.99);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);
        ResponseProductDto result = productService.UpdateProduct(1L,createProduct);
        assertEquals("nothing", result.getName());
        assertEquals(9.99, result.getPrice());
    }
    @Test
    void UpdateProductShouldThrowProductNotFound(){
        when(productRepository.findById(4L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, ()->productService.UpdateProduct(4L, any()));
        
    }
}
