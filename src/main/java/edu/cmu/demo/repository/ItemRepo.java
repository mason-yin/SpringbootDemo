package edu.cmu.demo.repository;

import edu.cmu.demo.entity.Item;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface ItemRepo extends MongoRepository<Item, ObjectId> {

    @Query("{'name' : {$regex : ?0, $options : 'i'}}")
    List<Item> findByName(String name);

    @Query("{'category' : ?0}")
    List<Item> findByCategory(String category);

    @Query("{'createTime': {$gte : ?0, $lt : ?1}}")
    List<Item> findByLastUpdated(Date date1, Date date2);

    @Query("{'name' : {$regex : ?0, $options : 'i'}, 'category' : ?1}")
    List<Item> findByNameAndCategory(String name, String category);

    @Query("{'name' : {$regex : ?0, $options : 'i'}, 'createTime': {$gte : ?1, $lt : ?2}}")
    List<Item> findByNameAndLastUpdated(String name, Date date1, Date date2);

}
