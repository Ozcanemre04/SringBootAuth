package com.dd.test.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.dd.test.Configuration.JwtUtils;
import com.dd.test.Dto.RequestProductDto;
import com.dd.test.Dto.ResponseProductDto;
import com.dd.test.Entity.Product;
import com.dd.test.Exceptions.ProductNotFoundException;
import com.dd.test.Service.CustomUserDetailsService;
import com.dd.test.Service.ProductService;
import com.dd.test.Service.ProductServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
   
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductServiceImpl productServiceImpl;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;


    @Test
    void getAllProductsShouldReturnOk() throws Exception{
        ResponseProductDto responseProductDto = new ResponseProductDto(1L,"Phone","description",999.99);
        ResponseProductDto responseProductDto2 = new ResponseProductDto(2L,"Laptop","description",999.99);
        when(productServiceImpl.getAllProducts()).thenReturn(List.of(responseProductDto,responseProductDto2));

        mockMvc.perform(get("/api/product"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].Name").value("Phone"))
               .andExpect(jsonPath("$[0].Id").value(1L));
    }

    @Test
    void getProductByIdShouldReturnOk() throws Exception{
        ResponseProductDto responseProductDto = new ResponseProductDto(1L,"Phone","description",999.99);
        when(productServiceImpl.getProductById(1L)).thenReturn(responseProductDto);

        mockMvc.perform(get("/api/product/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.Name").value("Phone"))
               .andExpect(jsonPath("$.Id").value(1L));
    }

    @Test
    void getProductByIdShouldReturnNotFound() throws Exception{
        when(productServiceImpl.getProductById(4L)).thenThrow(new ProductNotFoundException("Product not found"));

        mockMvc.perform(get("/api/product/4"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.message").value("Product not found"));
    }
    @Test
    void RemoveProductShouldReturnOk() throws Exception{
        when(productServiceImpl.DeleteProduct(1L)).thenReturn("product deleted");

        mockMvc.perform(delete("/api/product/delete/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").value("product deleted"));
               
    }
    @Test
    void RemoveProductShouldReturnNotFound() throws Exception{
        when(productServiceImpl.DeleteProduct(3L)).thenThrow(new ProductNotFoundException("product is not found"));

        mockMvc.perform(delete("/api/product/delete/3"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.message").value("product is not found"));
               
    }
    //create
    @Test
    void CreateProductShouldReturnOk() throws Exception{
        String json = """
                {
                  "Id": 1,
                  "Name": "Phone",
                  "description": "description",
                  "Price": 999.99
                }
                """;
        ResponseProductDto responseProductDto =  new ResponseProductDto(1L,"Phone","description",999.99);
        when(productServiceImpl.CreateProduct(Mockito.any()))
                               .thenReturn(responseProductDto);

        mockMvc.perform(post("/api/product/create")
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(json)
                    )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Id").value(1L))
                .andExpect(jsonPath("$.Name").value("Phone"))
                .andExpect(jsonPath("$.Price").value(999.99)); 
    }
    @Test
    void createProductshouldReturnBadRequestwhenInvalid() throws Exception {
         String json = """
                {
                  "Id": 1,
                  "Name": "",
                  "description": "description",
                  "Price": 999.99
                }
                """;
         mockMvc.perform(post("/api/product/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("field not valid"));
   }
   //update
   @Test
    void UpdateProductShouldReturnOk() throws Exception{
        String json = """
                {
                  "Id": 1,
                  "Name": "Phone",
                  "description": "description",
                  "Price": 999.99
                }
                """;
        ResponseProductDto responseProductDto =  new ResponseProductDto(1L,"Phone","description",999.99);
        RequestProductDto requestProductDto =  new RequestProductDto("Phone","description",999.99);
        when(productServiceImpl.UpdateProduct(1L,requestProductDto))
                               .thenReturn(responseProductDto);
        

        mockMvc.perform(put("/api/product/update/1")
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(json)
                    )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Id").value(1L))
                .andExpect(jsonPath("$.Name").value("Phone"))
                .andExpect(jsonPath("$.Price").value(999.99)); 
    }

    @Test
    void UpdateProductshouldReturnBadRequestwhenInvalid() throws Exception {
         String json = """
                {
                  "Id": 1,
                  "Name": "",
                  "description": "",
                  "Price": 999.99
                }
                """;
         mockMvc.perform(put("/api/product/update/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("field not valid"));
   }
    @Test
    void UpdateProductshouldReturnNotFound() throws Exception {
         String json = """
                {
                  "Id": 1,
                  "Name": "Phone",
                  "description": "description",
                  "Price": 999.99
                }
                """;
         RequestProductDto requestProductDto =  new RequestProductDto("Phone","description",999.99);
         when(productServiceImpl.UpdateProduct(4L,requestProductDto))
             .thenThrow(new ProductNotFoundException("produt is not found"));
         mockMvc.perform(put("/api/product/update/4")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("produt is not found"));
   }

}
