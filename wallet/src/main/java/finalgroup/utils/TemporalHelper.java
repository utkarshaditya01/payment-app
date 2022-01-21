package finalgroup.utils;

import finalgroup.temporal.Activity;
import finalgroup.temporal.WorkFlow;
import finalgroup.temporal.WorkflowImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TemporalHelper {

    @Autowired
    private ApplicationContext appContext;

    @PostConstruct
    public void init(){
        WorkerFactory factory = appContext.getBean(WorkerFactory.class);
        Activity signUpActivity = appContext.getBean(Activity.class);
        Worker worker = factory.newWorker(WorkFlow.QUEUE_NAME);
        worker.registerWorkflowImplementationTypes(WorkflowImpl.class);
        worker.registerActivitiesImplementations(signUpActivity);
        factory.start();
    }

}
