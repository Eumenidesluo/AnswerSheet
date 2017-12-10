package me.eumenides.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by Eumenides on 2017/10/23.
 */
@Document(collection = "result")
@Data
public class Result {
    @Id
    private String id;
    private int[] answers;
    private Date uploadDate;
    private int[] testId;
    private List<Integer> checkout;
    private Integer userId;

}
