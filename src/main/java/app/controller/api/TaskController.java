package app.controller.api;

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
        DictGenerator.dic()
        return taskService.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody Task t){

        return ResponseEntity.ok().build();
    }
}
