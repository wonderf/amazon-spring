package app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class TaskResult {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    private String amazonUrl;
    private String googleUrl;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
