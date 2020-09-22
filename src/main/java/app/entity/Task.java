package app.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@TypeDef(
        name = "list-array",
        typeClass = StringArrayType.class
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

    private int currentWorks;
    private int totalWorkls;


    @Type(type = "list-array")
    @Column(
            name = "filtering",
            columnDefinition = "text[]"
    )
    private String[] filtering;

    private String domain;

    @OneToMany(cascade = CascadeType.REMOVE,mappedBy ="task" )
    private List<TaskResult> taskResult;

}
