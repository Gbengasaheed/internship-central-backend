package com.example.demo.dtos;

import com.example.demo.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDto<T> {
    private ResponseStatus status;
    private T data;
    private String message;

    @JsonIgnore
    private Object[] messageArgs;

    public static <T> ResponseDto<T> wrapSuccessResult(T data, String message) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setData(data);
        response.setMessage(message);
        response.setStatus(ResponseStatus.SUCCESS);
        return response;
    }

    public static <T> ResponseDto<T> wrapErrorResult(String message) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setStatus(ResponseStatus.ERROR);
        response.setMessage(message);
        return response;
    }

    /**
     * This method is often used in web services or APIs to send responses to clients in a consistent format.
     * By using this method, the response is wrapped in a standard format, which can be easily parsed by the
     * client applications.
     */
}