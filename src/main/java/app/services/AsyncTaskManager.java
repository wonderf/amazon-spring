package app.services;

public interface AsyncTaskManager {
    void addTask(ServiceExecutor t,Long id);
    boolean isCompleteTask(Long id);
    void stopTask(Long id) ;
    void completeTask(Long id);
}
