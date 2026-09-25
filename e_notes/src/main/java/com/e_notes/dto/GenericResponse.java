package com.e_notes.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GenericResponse {

    private HttpStatus responseStatus;
    private String status;//success ,failed
    private String massage;//saved success
    private Object data;//data

    public ResponseEntity<?> create()
    {
        Map<String,Object> map=new LinkedHashMap<>();
        map.put("status",status);
        map.put("massage",massage);
        if (!ObjectUtils.isEmpty(data))
        {
            map.put("data",data);
        }
        return new ResponseEntity<>(map,responseStatus);
    }
}
