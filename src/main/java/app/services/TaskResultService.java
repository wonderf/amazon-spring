package app.services;

import app.entity.TaskResult;
import app.repository.TaskResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
