package finalgroup.utils;

import finalgroup.temporal.Activity;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;


import java.time.Duration;

public class ActivityStubUtils {
    // we use setScheduleToCloseTimeout for the demo
    // in order to limit the activity retry time
    // this is done so we don't have to wait too long in demo to show failure
    public static Activity getActivitiesStub() {
        return Workflow.newActivityStub(
                Activity.class,
                ActivityOptions.newBuilder()
                        .setScheduleToCloseTimeout(Duration.ofSeconds(15))
                        .setRetryOptions(RetryOptions.newBuilder()
                                .setBackoffCoefficient(1)
                                .build())
                        .build());
    }
}