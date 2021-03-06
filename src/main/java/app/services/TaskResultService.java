package app.services;

import app.entity.Task;
import app.entity.TaskResult;
import app.repository.TaskResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskResultService implements BaseCrudService<TaskResult> {
    private final TaskResultRepository taskResultRepository;


    @Autowired
    public TaskResultService(TaskResultRepository taskResultRepository) {
        this.taskResultRepository = taskResultRepository;
    }
    public TaskResult saveFromSearch(Task owner,String name) throws UnsupportedEncodingException {
        TaskResult result = new TaskResult();
        result.setTask(owner);
        result.setName(name);
        result.setAmazonUrl( URLEncoder.encode(owner.getAmazonResult().replace("{r}",result.getName()), "UTF-8"));
        result.setGoogleUrl(URLEncoder.encode(String.format("https://www.google.com/search?q=%s",result.getName()),"UTF-8"));
        return taskResultRepository.save(result);
    }
    @Override
    public TaskResult save(TaskResult item) {
        return taskResultRepository.save(item);
    }

    @Override
    public TaskResult update(TaskResult item) {
        return taskResultRepository.save(item);
    }

    @Override
    public Optional<TaskResult> findById(Long id) {
        return taskResultRepository.findById(id);
    }

    @Override
    public List<TaskResult> findAll() {
        return taskResultRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        taskResultRepository.deleteById(id);
    }
}
