package edu.cmu.demo.controller;

import edu.cmu.demo.entity.Item;
import edu.cmu.demo.repository.ItemRepo;
import edu.cmu.demo.util.Response;
import edu.cmu.demo.util.TimeUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @description: [controller class handling request from front-end]
 * @author: [ysx]
 * @version: [v1.0]
 * @createTime: [2022/5/4 16:33]
 * @updateUser: [ysx]
 * @updateTime: [2022/5/4 16:33]
 * @updateRemark: [initial commit]
 */

@RestController
@RequestMapping("/inventory")
@Validated
@CrossOrigin
public class ItemController {

    @Autowired
    ItemRepo itemRepo;

    /**
     * accept post request to create a new item
     * @param item request body
     * @return success message
     */
    @PostMapping("/create")
    public ResponseEntity<Response> createNewItem(@Valid @RequestBody Item item) {
        itemRepo.save(new Item(
                ObjectId.get().toString(),
                item.getName(),
                item.getCategory(),
                item.getQuantity(),
                item.getComment(),
                item.getPrice(),
                new Date(),
                new Date()
        ));
        return new ResponseEntity<>(new Response("create success", null), HttpStatus.OK);
    }

    /**
     * accept get request to retrieve all items in the database
     * @return list of items wrapped by response wrapper
     */
    @GetMapping("/retrieveAll")
    public ResponseEntity<Response> getAllItems() {
        List<Item> items = new ArrayList<>();
        items = itemRepo.findAll();
        if (items.isEmpty()) {
            return new ResponseEntity<>(new Response("no item found", null) ,HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(new Response("OK", items), HttpStatus.OK);
    }

    /**
     * accept get request with a name parameter to retrieve corresponding items in the database
     * @param name request parameter
     * @return list of items wrapped by response wrapper
     */
    @GetMapping("/retrieveByName")
    public ResponseEntity<Response> getItemsByName(@Valid @RequestParam("name") @NotBlank String name) {
        List<Item> items = new ArrayList<>();
        items = itemRepo.findByName(name);
        if (items.isEmpty()) {
            return new ResponseEntity<>(new Response("no item found", null), HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(new Response("OK", items), HttpStatus.OK);
    }

    /**
     * accept get request with a category parameter to retrieve corresponding items in the database
     * @param category request parameter
     * @return list of items wrapped by response wrapper
     */
    @GetMapping("/retrieveByCategory")
    public ResponseEntity<Response> getItemsByCategory(@Valid @RequestParam("category")
            @NotBlank @Pattern(regexp = "(^dairy$|^meat$|^seafood$|^vegetable$|^fruit$|^beverage$|^cereal$)") String category) {
        List<Item> items = new ArrayList<>();
        items = itemRepo.findByCategory(category);
        if (items.isEmpty()) {
            return new ResponseEntity<>(new Response("no item found", null), HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(new Response("OK", items), HttpStatus.OK);
    }

    /**
     * accept get request with a date parameter to retrieve corresponding items in the database
     * unused in actual project
     * @param date request parameter
     * @return list of items wrapped by response wrapper
     * @throws ParseException time parse exception, handled by GlobalExceptionHandler
     */
    @GetMapping("/retrieveByLastUpdated")
    public ResponseEntity<Response> getItemsByLastUpdated(@Valid @RequestParam("date") String date) throws ParseException {
        List<Item> items = new ArrayList<>();
        Date today = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        today = TimeUtil.getStandardDay(today);
        items = itemRepo.findByLastUpdated(today, TimeUtil.getStandardNextDay(today));
        if (items.isEmpty()) {
            return new ResponseEntity<>(new Response("no item found", null), HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(new Response("OK", items), HttpStatus.OK);
    }

    /**
     * accept get request with a name parameter and a category parameter to retrieve corresponding items in the database
     * @param name request parameter
     * @param category request parameter
     * @return list of items wrapped by response wrapper
     */
    @GetMapping("/retrieveByNameAndCategory")
    public ResponseEntity<Response> getItemsByNameAndCategory(@Valid @RequestParam("name") @NotBlank String name,
            @Valid @RequestParam("category") @NotBlank @Pattern(regexp = "(^dairy$|^meat$|^seafood$|^vegetable$|^fruit$|^beverage$|^cereal$)") String category) {
        List<Item> items = new ArrayList<>();
        items = itemRepo.findByNameAndCategory(name, category);
        if (items.isEmpty()) {
            return new ResponseEntity<>(new Response("no item found", null), HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(new Response("OK", items), HttpStatus.OK);
    }

    /**
     * accept get request with a name parameter and a date parameter to retrieve corresponding items in the database
     * @param name request parameter
     * @param date request parameter
     * @return list of items wrapped by response wrapper
     * @throws ParseException time parse exception, handled by GlobalExceptionHandler
     */
    @GetMapping("/retrieveByNameAndLastUpdated")
    public ResponseEntity<Response> getItemsByNameAndLastUpdated(@Valid @RequestParam("name") @NotBlank String name,
            @Valid @RequestParam("date") String date) throws ParseException {
        List<Item> items = new ArrayList<>();
        Date today = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        today = TimeUtil.getStandardDay(today);
        items = itemRepo.findByNameAndLastUpdated(name, today, TimeUtil.getStandardNextDay(today));
        if (items.isEmpty()) {
            return new ResponseEntity<>(new Response("no item found", null), HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(new Response("OK", items), HttpStatus.OK);
    }

    /**
     * accept post request to update an existing item, first find the item by id, then update it using save()
     * @param item item to be updated
     * @return success/fail message
     */
    @PostMapping("/update")
    public ResponseEntity<Response> updateItem(@Valid @RequestBody Item item) {
        String id = item.getId();
        Optional<Item> itemData = itemRepo.findById(new ObjectId(id));
        if (itemData.isPresent()) {
            Item _item = itemData.get();
            _item.setName(item.getName());
            _item.setCategory(item.getCategory());
            _item.setQuantity(item.getQuantity());
            _item.setComment(item.getComment());
            _item.setPrice(item.getPrice());
            _item.setLastUpdatedTime(new Date());
            itemRepo.save(_item);
            return new ResponseEntity<>(new Response("update success", null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response("Resource Not Found", null), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * accept get request with a id parameter to delete an existing item by id from the database
     * @param id get request parameter
     * @return success message
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<Response> deleteItem(@PathVariable("id") String id) {
        itemRepo.deleteById(new ObjectId(id));
        return new ResponseEntity<>(new Response("delete success", null), HttpStatus.OK);
    }
}
