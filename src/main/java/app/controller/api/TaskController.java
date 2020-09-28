package app.controller.api;

import app.dto.TaskForm;
import app.entity.Task;
import app.services.TaskService;
import app.utils.DictGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> loadAll(){
        return taskService.findAll();
    }

    @PostMapping(value="/create",consumes = "application/json",produces = "application/json")
    public ResponseEntity<Task> createTask(@RequestBody TaskForm t){

        return ResponseEntity.ok().build();
    }
}
