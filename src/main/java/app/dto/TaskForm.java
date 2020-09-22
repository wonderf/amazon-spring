package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskForm {
    private String[] words;
    private String[] filtering;
    private boolean deep;
    private boolean reverse;
    private String market;
}
