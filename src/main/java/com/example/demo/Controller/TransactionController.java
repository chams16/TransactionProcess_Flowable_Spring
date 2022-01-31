package com.example.demo.Controller;


import com.example.demo.Dto.ProcessInstanceResponse;
import com.example.demo.Dto.TaskDetails;
import com.example.demo.Dto.TransactionRequest;
import com.example.demo.services.TransactionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    //********************************************************** deployment endpoints **********************************************************
    @PostMapping("/deploy")
    public void deployWorkflow() {
        transactionService.deployProcessDefinition();
    }

    //********************************************************** process endpoints **********************************************************
    @PostMapping("/apply")
    public ProcessInstanceResponse applTransaction(@RequestBody TransactionRequest transactionRequest) {
        return transactionService.applyTransaction(transactionRequest);
    }


    @GetMapping("/manager/tasks")
    public List<TaskDetails> getTasks() {
        return transactionService.getManagerTasks();
    }


    @PostMapping("/manager/approve/tasks/{taskId}/{approved}")
    public void approveTask(@PathVariable("taskId") String taskId,@PathVariable("approved") Boolean approved){
        transactionService.approveTransaction(taskId,approved);
    }

    @PostMapping("/user/accept/{taskId}")
    public void acceptHoliday(@PathVariable("taskId") String taskId){
        transactionService.acceptTransaction(taskId);
    }

    @GetMapping("/user/tasks/{receiver}")
    public List<TaskDetails> getUserTasks(@PathVariable("receiver") String receiver) {
        return transactionService.getUserTasks(receiver);
    }


    @GetMapping("/process/{processId}")
    public void checkState(@PathVariable("processId") String processId){
        transactionService.checkProcessHistory(processId);
    }

}
