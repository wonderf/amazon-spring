package app.repository;

import app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    @Modifying
    @Query(value = "UPDATE Task t set t.currentWorks = t.currentWorks + 1 WHERE t = :task")
    void updateCurrnetWorks(@Param("task") Task task);
}
