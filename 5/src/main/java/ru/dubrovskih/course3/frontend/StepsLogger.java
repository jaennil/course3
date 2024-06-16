package ru.dubrovskih.course3.frontend;

import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.Objects;

public class StepsLogger implements StepLifecycleListener {

    private static final Logger logger = LogManager.getLogger();

    private void logStep(StepResult stepResult) {
        if (Objects.equals(stepResult.getName(), "step")) {
            return;
        }

        ThreadContext.put("name", stepResult.getName());
        ThreadContext.put("stage", stepResult.getStage().toString());

        Status status = stepResult.getStatus();
        ThreadContext.put("status", Objects.isNull(status) ? "null" : status.toString());

        logger.info("{} {} {}", stepResult.getName(), stepResult.getStage(), status);

        ThreadContext.clearAll();
    }

    @Override
    public void afterStepUpdate(StepResult result) {
        logStep(result);
    }

    @Override
    public void afterStepStop(StepResult result) {
        logStep(result);
    }
}
