package app.services;

import app.dto.TaskForm;
import app.entity.Task;
import app.utils.DictGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Service
@Slf4j
public class ServiceExecutor {
    private final RestTemplate client;
    private final TaskResultService taskResultService;
    private final TaskService taskService;
    private final String US="";
    private final String UK="";
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ServiceExecutor(TaskResultService taskResultService, TaskService taskService) {
        this.taskResultService = taskResultService;
        this.taskService = taskService;
        this.client = new RestTemplate();
    }

    @Async
    public void collectAllResults(TaskForm form){
        Task t = new Task();
        t.setDomain(form.getMarket());
        t.setWords(form.getSearch().split(","));
        t.setFiltering(form.getFilters().split(","));
        t.setAmazonResult(form.getAmazonResult());
        int total = t.getWords().length *36+(form.isDeep()?1:0 )*t.getWords().length*DictGenerator.words().length+(form.isReverse()?1:0)*t.getWords().length*DictGenerator.words().length+1;
        t.setTotalWorkls(total);
        Task save = taskService.save(t);
        try {
            lightSearch(save);
            if(form.isDeep())
                deepSearch(save);
            if(form.isReverse())
                reverseSearch(save);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        //calculate all works
    }

    @Async
    public void lightSearch(Task t) throws IOException, URISyntaxException {
        //todo without adding
        for(int i=0;i<t.getWords().length;i++){
            taskService.incrementCurrentWork(t);
            String req =t.getDomain().replace("{0}",t.getWords()[i]);
            URL url = new URL(req);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            ResponseEntity<String> forEntity = client.getForEntity(req, String.class);
            JsonNode jsonNode = mapper.readTree(forEntity.getBody());
            ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {
            });
            List<String> list = reader.readValue(jsonNode.get(1));
            list.stream().filter(x->{
                for(int ii=0;ii<t.getFiltering().length;ii++){
                    if(x.contains(t.getFiltering()[ii])) return true;
                }
                return false;
            }).forEach(x->taskResultService.saveFromSearch(t,x));
            for(int j=0;j< DictGenerator.dic().length;j++){
                taskService.incrementCurrentWork(t);
                log.info(t.getWords()[i]+" "+DictGenerator.dic()[j]);
                req =t.getDomain().replace("{0}",t.getWords()[i]+" "+DictGenerator.dic()[j]);
                url = new URL(req);
                uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                forEntity = client.getForEntity(req, String.class);
                jsonNode = mapper.readTree(forEntity.getBody());
                reader = mapper.readerFor(new TypeReference<List<String>>() {
                });
                list = reader.readValue(jsonNode.get(1));
                list.stream().filter(x->{
                    for(int ii=0;ii<t.getFiltering().length;ii++){
                        if(x.contains(t.getFiltering()[ii])) return true;
                    }
                    return false;
                }).forEach(x->taskResultService.saveFromSearch(t,x));
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Async
    public void deepSearch(Task t) throws IOException, URISyntaxException {
        for(int i=0;i<t.getWords().length;i++){
            for(int j=0;j< DictGenerator.words().length;j++){
                taskService.incrementCurrentWork(t);
                log.info(t.getWords()[i]+" "+DictGenerator.words()[j]);
                String req =t.getDomain().replace("{0}",t.getWords()[i]+" "+DictGenerator.words()[j]);
                URL url = new URL(req);
                URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                ResponseEntity<String> forEntity = client.getForEntity(req, String.class);
                JsonNode jsonNode = mapper.readTree(forEntity.getBody());
                ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {
                });
                List<String> list = reader.readValue(jsonNode.get(1));
                list.stream().filter(x->{
                    for(int ii=0;ii<t.getFiltering().length;ii++){
                        if(x.contains(t.getFiltering()[ii])) return true;
                    }
                    return false;
                }).forEach(x->taskResultService.saveFromSearch(t,x));
                if(j%36==0){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Async
    public void reverseSearch(Task t) throws IOException, URISyntaxException {
        for(int i=0;i<t.getWords().length;i++){
            for(int j=0;j< DictGenerator.words().length;j++){
                taskService.incrementCurrentWork(t);
                log.info(DictGenerator.words()[j] + " " +t.getWords()[i]);
                String req =t.getDomain().replace("{0}",DictGenerator.words()[j]+" "+t.getWords()[i]);
                URL url = new URL(req);
                ResponseEntity<String> forEntity = client.getForEntity(req, String.class);
                JsonNode jsonNode = mapper.readTree(forEntity.getBody());
                ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {
                });
                List<String> list = reader.readValue(jsonNode.get(1));
                list.stream().filter(x->{
                    for(int ii=0;ii<t.getFiltering().length;ii++){
                        if(x.contains(t.getFiltering()[ii])) return true;
                    }
                    return false;
                }).forEach(x->taskResultService.saveFromSearch(t,x));
                if(j%36==0){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
