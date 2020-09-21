package app.entity;

import lombok.*;

import javax.persistence.*;

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

    @OneToMany(mappedBy = "taskResult")
    private Task task;
}