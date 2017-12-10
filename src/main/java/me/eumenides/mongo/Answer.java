package me.eumenides.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Eumenides on 2017/10/20.
 */
@Document(collection = "answer")
@Data
public class Answer {
    @Id
    private String id;
    private int[] answers;
    private Date uploadDate;
    private int[] testId;
    private String name;
    private String uploader;
}
