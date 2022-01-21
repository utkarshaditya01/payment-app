package finalgroup.temporal;

import finalgroup.DTO.WalletRequestBodyDTO;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface WorkFlow {

    public static final String QUEUE_NAME = "Wallet life cycle";

    @WorkflowMethod
    void startApprovalWorkflow(WalletRequestBodyDTO walletDTO);

    @SignalMethod
    void signalWalletCreated(WalletRequestBodyDTO walletDTO);

    @SignalMethod
    void signalWalletGet(Integer id);

    @SignalMethod
    void signalWalletUpdated(Integer id, WalletRequestBodyDTO walletDTO);

    @SignalMethod
    void signalWalletDelete(Integer id);

}

