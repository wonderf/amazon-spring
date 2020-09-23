package app.controller.api;

import app.dto.TaskForm;
import app.entity.Task;
import app.services.ServiceExecutor;
import app.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;
    private final ServiceExecutor serviceExecutor;

    public TaskController(TaskService taskService, ServiceExecutor serviceExecutor) {
        this.taskService = taskService;
        this.serviceExecutor = serviceExecutor;
    }

    @GetMapping
    public List<Task> loadAll(){
        return taskService.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskForm t){
        serviceExecutor.collectAllResults(t);
        return ResponseEntity.ok().build();
    }
}
