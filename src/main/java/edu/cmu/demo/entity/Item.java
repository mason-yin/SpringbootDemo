package edu.cmu.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @description: [entity/POJO class for Item]
 * @author: [ysx]
 * @version: [v1.0]
 * @createTime: [2022/5/4 16:20]
 * @updateUser: [ysx]
 * @updateTime: [2022/5/4 16:20]
 * @updateRemark: [initial commit]
 */

@Data
@AllArgsConstructor
@Document(collection = "Item")
public class Item {

    @Id
    private String id;

    @NotBlank(message = "name is required")
    private String name;

    @Pattern(regexp = "(^dairy$|^meat$|^seafood$|^vegetable$|^fruit$|^beverage$|^cereal$)",
            message = "category can only choose from {dairy, meat, seafood, vegetable, fruit, beverage, cereal}")
    @NotBlank(message = "category is required")
    private String category;

    @Min(value = 0, message = "quantity must be larger than 0")
    @NotNull(message = "quantity is required")
    private long quantity;

    private String comment;

    @Min(value = 0, message = "price must above 0")
    @NotNull(message = "price is required")
    private double price;

    private Date createTime;

    private Date lastUpdatedTime;

}
