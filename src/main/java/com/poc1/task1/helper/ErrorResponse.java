package com.poc1.task1.helper;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
 
@XmlRootElement(name = "error")
@Data
public class ErrorResponse 
{
  public ErrorResponse(String message, List<String> details) {
    super();
    this.message = message;
    this.details = details;
  }
 
  //General error message about nature of error
  private String message;
 
  //Specific errors in API request processing
  private List<String> details;
 
  //Getter and setters
}