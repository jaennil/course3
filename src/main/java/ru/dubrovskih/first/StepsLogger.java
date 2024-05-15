package ru.dubrovskih.first;

import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.MDC;

import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

public class StepsLogger implements StepLifecycleListener {

    private static final Logger logger = LogManager.getLogger();

    private void logStep(StepResult stepResult) {
        if (!Objects.equals(stepResult.getName(), "step")) {
            ThreadContext.put("name", stepResult.getName());
            ThreadContext.put("stage", stepResult.getStage().toString());
            if (stepResult.getStatus() == null) {
                ThreadContext.put("status", "null");
            } else {
                ThreadContext.put("status", stepResult.getStatus().toString());
            }
            logger.info("{} {} {}", stepResult.getName(), stepResult.getStage(), stepResult.getStatus());
            ThreadContext.clearAll();
        }
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
