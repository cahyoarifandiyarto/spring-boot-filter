package com.belajar.springbootfilter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Response<T> {

    private Integer code;

    private String status;

    private T data;

    private Map<String, List<String>> errors;

    private Map<String, Object> metadata;

}
