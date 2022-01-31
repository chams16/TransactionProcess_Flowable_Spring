package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TransactionRequest {

    private String receiverName;


    private Long receiverAccountNumber;

    private Long amount;

    private String senderName;

    private Long code;



}
