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
    private String filters="";
    private boolean filtering;
    private boolean deep;
    private boolean reverse;
    private String market;
    private String amazonResult;
}
