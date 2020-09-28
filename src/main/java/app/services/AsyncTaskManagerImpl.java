package app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class AsyncTaskManagerImpl implements AsyncTaskManager {
    private ConcurrentMap<Long,ServiceExecutor> concurrentMap = new ConcurrentHashMap<>();
    private final TaskService taskService;

    @Autowired
    public AsyncTaskManagerImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void addTask(ServiceExecutor serviceExecutor, Long id) {
        concurrentMap.put(id,serviceExecutor);
    }

    @Override
    public boolean isCompleteTask(Long id) {
        return concurrentMap.containsKey(id);
    }

    //test breaker
    @Override
    @Async
    public void stopTask(Long id) {
        if(!isCompleteTask(id)) {
            ServiceExecutor serviceExecutor = concurrentMap.get(id);
            serviceExecutor.complete();
            completeTask(id);
        }

    }

    @Override
    public void completeTask(Long id) {
        concurrentMap.remove(id);
    }


}
