package com.shortenme.urlShortener.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private T body;

    private String message;

    private String documentation;

    private int response;
}
