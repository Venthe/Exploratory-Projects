package eu.venthe.camunda_demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.jvnet.hk2.annotations.Service;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowManager {
    private final RuntimeService runtimeService;

    @EventListener(PostDeployEvent.class)
    public void postDeploy(PostDeployEvent event) {
        runtimeService.startProcessInstanceByKey("loadProcess");
        log.info("Post deploy. {}", event);
    }
}
