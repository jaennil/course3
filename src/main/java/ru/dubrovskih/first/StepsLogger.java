package ru.dubrovskih.first;

import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class StepsLogger implements StepLifecycleListener {

    private static final Logger logger = LogManager.getLogger();

    private void logStep(StepResult stepResult) {
        if (!Objects.equals(stepResult.getName(), "step")) {
            logger.info("{} {} {}", stepResult.getName(), stepResult.getStage(), stepResult.getStatus());
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
