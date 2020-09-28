package app.controller.api;

import app.dto.TaskForm;
import app.entity.Task;
import app.entity.TaskResult;
import app.services.AsyncTaskManager;
import app.services.ExcelService;
import app.services.ServiceExecutor;
import app.services.TaskService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;
    private final ServiceExecutor serviceExecutor;
    private final ExcelService excelService;
    private final AsyncTaskManager taskManager;

    public TaskController(TaskService taskService, ServiceExecutor serviceExecutor, ExcelService excelService, AsyncTaskManager taskManager) {
        this.taskService = taskService;
        this.serviceExecutor = serviceExecutor;
        this.excelService = excelService;
        this.taskManager = taskManager;
    }

    @GetMapping
    public List<Task> loadAll(){
        return taskService.findAll();
    }

    @GetMapping("/stop/{id}")
    public ResponseEntity<Boolean> stopThread(@PathVariable(name = "id") Long task){
        Optional<Task> byId = taskService.findById(task);
        if(byId.isPresent()){
            Task task1 = byId.get();
            if(task1.getCurrentWorks()==task1.getTotalWorkls())
                taskService.deleteById(task);
            else
                taskManager.completeTask(task);
        }

        return ResponseEntity.ok(true);
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskForm t){
        serviceExecutor.collectAllResults(t);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/downloadZip/{id}")
    public void downloadFile(@PathVariable(name = "id") Long taskId,HttpServletResponse response) {
        Optional<Task> byId = taskService.findById(taskId);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=download.zip");
        response.setStatus(HttpServletResponse.SC_OK);
        List<InputStreamResource> streams = new ArrayList<>();
        if(byId.isPresent()){
            Task task = byId.get();
            if(task.getTotalWorkls()==task.getCurrentWorks()){
                List<TaskResult> taskResult = task.getTaskResult();
                Set<String> set = new HashSet<>(taskResult.size());
                List<TaskResult> collect = taskResult.stream().filter(p -> set.add(p.getName())).collect(Collectors.toList());
                List<ByteArrayInputStream> load = excelService.load(collect,2000);
                try (ZipOutputStream zippedOut = new ZipOutputStream(response.getOutputStream())) {
                    for (int i=0;i<load.size();i++) {
                        ZipEntry e = new ZipEntry(task.getWords()[0]+"_"+(i+1)+".xlsx");
                        // Configure the zip entry, the properties of the file
                        e.setSize(load.get(i).available());
                        e.setTime(System.currentTimeMillis());
                        // etc.
                        zippedOut.putNextEntry(e);
                        // And the content of the resource:
                        StreamUtils.copy(load.get(i), zippedOut);
                        zippedOut.closeEntry();
                    }
                    zippedOut.finish();
                } catch (Exception e) {
                    // Exception handling goes here
                }

            }
        }
    }
}
