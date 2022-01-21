package finalgroup.services;

import finalgroup.DTO.WalletRequestBodyDTO;
import finalgroup.temporal.WorkFlow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import net.bytebuddy.implementation.bytecode.ShiftRight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemporalWalletService {

    @Autowired
    WorkflowServiceStubs workflowServiceStubs;

    @Autowired
    WorkflowClient workflowClient;

    public static final String key = "123232";

    public String createWalletWorkFlow(WalletRequestBodyDTO walletDTO){
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(WorkFlow.QUEUE_NAME)
                .setWorkflowId("Order_" + key).build();

        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, options);
        WorkflowClient.start(workflow::startApprovalWorkflow, walletDTO);

        //workflow.signalWalletCreated(walletDTO);
        return "Workflow started";
    }

    public void getWalletWorkflow(int id){
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, "Order_" + key);
        workflow.signalWalletGet(id);
    }

    public void updateWalletWorkflow(int id, WalletRequestBodyDTO walletDTO){
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, "Order_" + key);
        workflow.signalWalletUpdated(id, walletDTO);
    }

    public void deleteWalletWorkflow(int id){
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, "Order_" + key);
        workflow.signalWalletDelete(id);
    }


}
