package app.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Type(type = "list-array")
    @Column(
            name = "words",
            columnDefinition = "text[]"
    )
    private String[] words;

    private int percents;


    @Type(type = "list-array")
    @Column(
            name = "filtering",
            columnDefinition = "text[]"
    )
    private String[] filtering;

    private String domain;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "task_result_id")
    private TaskResult taskResult;

}
