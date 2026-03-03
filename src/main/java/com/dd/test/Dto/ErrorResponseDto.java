package com.dd.test.Dto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.validation.ObjectError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto {
  private LocalDateTime timeStamp;
  private String message;
  private List<ObjectError> details;
  
  public ErrorResponseDto(LocalDateTime timeStamp,String message,List<ObjectError> details){
      this.timeStamp=timeStamp;
      this.message=message;
      this.details=details;
  }
}
