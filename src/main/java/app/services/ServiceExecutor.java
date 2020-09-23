package app.services;

import app.dto.TaskForm;
import app.entity.Task;
import app.utils.DictGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceExecutor {
    private final RestTemplate client;
    private final TaskResultService taskResultService;
    private final TaskService taskService;
    private final String US="";
    private final String UK="";

    @Autowired
    public ServiceExecutor(TaskResultService taskResultService, TaskService taskService) {
        this.taskResultService = taskResultService;
        this.taskService = taskService;
        this.client = new RestTemplate();
    }

    public void collectAllResults(TaskForm form){
        Task t = new Task();
        t.setDomain(US);
        t.setWords(form.getSearch().split(","));
        t.setFiltering(form.getFiltering().split(","));

        int total = t.getWords().length *36+(form.isDeep()?1:0 )*t.getWords().length*DictGenerator.words().length+(form.isReverse()?1:0)*t.getWords().length*DictGenerator.words().length;
        Task save = taskService.save(t);
        lightSearch(save);
        //calculate all works
    }

    @Async
    public void lightSearch(Task t){
        for(int i=0;i<t.getWords().length;i++){
            for(int j=0;j< DictGenerator.dic().length;j++){
                String ref = "";
                //handle to client
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Async
    public void deepSearch(){

    }

    @Async
    public void reverseSearch(){

    }

}
