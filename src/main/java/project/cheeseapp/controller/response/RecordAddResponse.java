package project.cheeseapp.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.cheeseapp.model.Record;

@Data
@NoArgsConstructor
public class RecordAddResponse {
    private Record record;
    private String message;
}
