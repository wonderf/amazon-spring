package app.startup;

import app.entity.Task;
import app.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UnCompleteRemover {
    private final TaskService taskService;

    @Autowired
    public UnCompleteRemover(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostConstruct
    public void remove(){
        taskService
                .findAll()
                .stream()
                .filter(x->x.getCurrentWorks()!=x.getTotalWorkls())
                .map(Task::getId)
                .forEach(taskService::deleteById);
    }
}
