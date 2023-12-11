package PDS.pdsproject;
import lombok.Data;
import PDS.pdsproject.*;

@Data
public class TodoDto {
    private Integer id;
    private String title;
    private boolean isChecked;
}