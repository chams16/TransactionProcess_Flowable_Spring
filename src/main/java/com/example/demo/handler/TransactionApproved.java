package com.example.demo.handler;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

@Data
@Slf4j
@RequiredArgsConstructor
public class TransactionApproved implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {

        System.out.println("Approved, sending an email");
        log.info("transaction Approved and the employee have to accept and you will receive email confirming that");
    }

}
