package finalgroup.temporal;

import finalgroup.DTO.SimpleResponseDTO;
import finalgroup.DTO.WalletRequestBodyDTO;
import finalgroup.utils.ActivityStubUtils;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class WorkflowImpl implements WorkFlow {
    private final RetryOptions retryoptions = RetryOptions.newBuilder().setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100)).setBackoffCoefficient(2).setMaximumAttempts(3).build();
    private final ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(retryoptions).build();

    private final Activity activity = Workflow.newActivityStub(Activity.class, options);
    //private final Activity activity = ActivityStubUtils.getActivitiesStub();

    public boolean step1 = false;

    public boolean step2 = false;

    public boolean step3 = false;

    public boolean step4 = false;

//    @Override
//    public void startApprovalWorkflow() {
//
//        Workflow.await(() -> step1);
//        System.out.println("***** Waiting for get wallet");
//        Workflow.await(() -> step2);
//
//        System.out.println("***** Please wait till we update wallet");
//        Workflow.await(() -> step3);
//
//        System.out.println("***** Please wait till we delete wallet");
//        Workflow.await(() -> step4);
//        System.out.println("***** Workflow Complete - Done");
//    }

    @Override
    public void startApprovalWorkflow(WalletRequestBodyDTO walletDTO){
        SimpleResponseDTO response = activity.createWalletActivity(walletDTO);
        System.out.println("this");
//        if(response.getResponse().equals("Failed")){
//            throw new RuntimeException();
//        }

        // get
        System.out.println("Tada : " + activity.getWalletActivity(response.getId()));

        // update
        activity.updateWalletActivity(response.getId(), walletDTO);

        // delete
        activity.deleteWalletActivity(response.getId());

    }


    @Override
    public void signalWalletCreated(WalletRequestBodyDTO walletDTO) {
        SimpleResponseDTO responseDTO = activity.createWalletActivity(walletDTO);
//        if(responseDTO.getResponse().equals("Failed")){
//            throw new RuntimeException();
//        }
        this.step1 = true;
    }

    @Override
    public void signalWalletGet(Integer id) {
        activity.getWalletActivity(id);
        this.step2 = true;
    }

    @Override
    public void signalWalletUpdated(Integer id, WalletRequestBodyDTO walletDTO) {
        activity.updateWalletActivity(id, walletDTO);
        this.step3 = true;
    }

    @Override
    public void signalWalletDelete(Integer id) {
        activity.deleteWalletActivity(id);
        this.step4 = true;
    }
}
