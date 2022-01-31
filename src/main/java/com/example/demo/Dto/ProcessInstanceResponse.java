package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProcessInstanceResponse {

    String processId;
    boolean isEnded;
}
