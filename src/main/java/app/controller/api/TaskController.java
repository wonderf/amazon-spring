package app.controller.api;

import app.dto.TaskForm;
import app.entity.Task;
import app.entity.TaskResult;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;
    private final ServiceExecutor serviceExecutor;
    private final ExcelService excelService;

    public TaskController(TaskService taskService, ServiceExecutor serviceExecutor, ExcelService excelService) {
        this.taskService = taskService;
        this.serviceExecutor = serviceExecutor;
        this.excelService = excelService;
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

    @GetMapping("/download/{id}")
    public ResponseEntity<List<InputStreamResource>> downloadSplittedResult(@PathVariable(name = "id") Long taskId){
        Optional<Task> byId = taskService.findById(taskId);
        List<InputStreamResource> streams = new ArrayList<>();
        if(byId.isPresent()){
            Task task = byId.get();
            if(task.getTotalWorkls()==task.getCurrentWorks()){
                List<TaskResult> taskResult = task.getTaskResult();
                List<ByteArrayInputStream> load = excelService.load(taskResult,2000);
                for(int i=0;i<load.size();i++){
                    InputStreamResource file = new InputStreamResource(load.get(i));
                    streams.add(file);
                }
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "filename")
                        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                        .body(streams);
            }
        }
        return ResponseEntity.notFound().build();
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
                List<ByteArrayInputStream> load = excelService.load(taskResult,2000);
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
