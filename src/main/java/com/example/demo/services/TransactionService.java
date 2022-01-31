package com.example.demo.services;


import com.example.demo.Dto.ProcessInstanceResponse;
import com.example.demo.Dto.TaskDetails;
import com.example.demo.Dto.TransactionRequest;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class TransactionService {

    public static final String TASK_CANDIDATE_GROUP = "managers";
    public static final String PROCESS_DEFINITION_KEY = "transactionRequest";
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final ProcessEngine processEngine;
    private final RepositoryService repositoryService;


    public void deployProcessDefinition() {

        Deployment deployment =
                repositoryService
                        .createDeployment()
                        .addClasspathResource("TransactionProcess.bpmn20.xml")
                        .deploy();
    }

    public ProcessInstanceResponse applyTransaction(TransactionRequest transactionRequest) {

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("receiver", transactionRequest.getReceiverName());
        variables.put("receiverAccountNumber", transactionRequest.getReceiverAccountNumber());
        variables.put("amount", transactionRequest.getAmount());
        variables.put("sender", transactionRequest.getSenderName());
        variables.put("code", transactionRequest.getCode());

        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);

        return new ProcessInstanceResponse(processInstance.getId(), processInstance.isEnded());
    }

    public List<TaskDetails> getManagerTasks() {
        List<Task> tasks =
                taskService.createTaskQuery().taskCandidateGroup(TASK_CANDIDATE_GROUP).list();
//                for(Task t:tasks){
//                    System.out.println(t.getAssignee());
//                }

        List<TaskDetails> taskDetails = getTaskDetails(tasks);

        return taskDetails;
    }
    private List<TaskDetails> getTaskDetails(List<Task> tasks) {
        List<TaskDetails> taskDetails = new ArrayList<>();
        for (Task task : tasks) {
            Map<String, Object> processVariables = taskService.getVariables(task.getId());
            taskDetails.add(new TaskDetails(task.getId(), task.getName(),processVariables));
        }
        return taskDetails;
    }

    public void approveTransaction(String taskId,Boolean approved) {

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("approved", approved.booleanValue());
        taskService.complete(taskId,variables);
    }

    public void acceptTransaction(String taskId) {
        Map<String, Object> processVariables = taskService.getVariables(taskId);
         if (processVariables.get("approved").equals(true)) {
             Object sender = processVariables.get("sender");
             Object receiver = processVariables.get("receiver");
             Object amount = processVariables.get("amount");
             System.out.println(sender + " wil send this amount " + amount + " to this person " + receiver);
             //make the transaction or call an external class
        }
        taskService.complete(taskId);
    }


    public List<TaskDetails> getUserTasks(String receiver) {

        List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(receiver).list();
//        for (Task t:tasks){
//            System.out.println(t.getAssignee());
//        }
        List<TaskDetails> taskDetails = getTaskDetails(tasks);

        return taskDetails;
    }

    //to verify the problem is that she don't present the task history

    public void checkProcessHistory(String processId) {

        HistoryService historyService = processEngine.getHistoryService();

        List<HistoricActivityInstance> activities =

        historyService.createHistoricActivityInstanceQuery()
                .activityType("userTask")
                .processDefinitionId(processId)
                .finished()
                .orderByHistoricActivityInstanceEndTime().desc()
                .listPage(0, 1);

        for (HistoricActivityInstance activity : activities) {
            System.out.println(
                    activity.getActivityId() + " took " + activity.getDurationInMillis() + " milliseconds");
        }


    }
    
}
