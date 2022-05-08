package edu.cmu.demo.repository;

import edu.cmu.demo.entity.Item;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @description: [data accessing layer, connect with mongoDB instance]
 * @author: [ysx]
 * @version: [v1.0]
 * @createTime: [2022/5/5 15:42]
 * @updateUser: [ysx]
 * @updateTime: [2022/5/5 15:42]
 * @updateRemark: [initial commit]
 */

public interface ItemRepo extends MongoRepository<Item, ObjectId> {

    //  find items by matching regex pattern with name
    @Query("{'name' : {$regex : ?0, $options : 'i'}}")
    List<Item> findByName(String name);

    //  find items by matching category
    @Query("{'category' : ?0}")
    List<Item> findByCategory(String category);

    //  find items by last updated date
    @Query("{'createTime': {$gte : ?0, $lt : ?1}}")
    List<Item> findByLastUpdated(Date date1, Date date2);

    //  find items by matching name and category
    @Query("{'name' : {$regex : ?0, $options : 'i'}, 'category' : ?1}")
    List<Item> findByNameAndCategory(String name, String category);

    //  find items by matching name and last updated date
    @Query("{'name' : {$regex : ?0, $options : 'i'}, 'createTime': {$gte : ?1, $lt : ?2}}")
    List<Item> findByNameAndLastUpdated(String name, Date date1, Date date2);

}
