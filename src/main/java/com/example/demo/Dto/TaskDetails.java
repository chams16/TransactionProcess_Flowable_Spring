package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TaskDetails {

    String taskId;
    String taskName;
    Map<String, Object> taskData;
}
