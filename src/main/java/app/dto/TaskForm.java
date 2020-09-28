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
    private String search;
    private String filtering;
    private boolean deepSearch;
    private boolean reverseSearch;
    private String market;
}
