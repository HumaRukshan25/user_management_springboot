package org.jsp.user_management.responsestructure;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseStructure<T> {
    private int status;
    private String message;
    private T data;
}
  //course --->  save ,find by id ,find all, delete by id ,assign faculty to the course