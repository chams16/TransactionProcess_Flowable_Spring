package com.example.demo.handler;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

@Data
@Slf4j
@RequiredArgsConstructor
public class TransactionRejected implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("Rejected, sending an email");
        log.info("transaction rejzcted you will receive an email explaining that");
    }
}
