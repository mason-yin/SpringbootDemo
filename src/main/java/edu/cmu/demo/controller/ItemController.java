package edu.cmu.demo.controller;

import edu.cmu.demo.entity.Item;
import edu.cmu.demo.repository.ItemRepo;
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
public class ItemController {

    @Autowired
    ItemRepo itemRepo;

    @PostMapping("/create")
    public ResponseEntity<String> createNewItem(@Valid @RequestBody Item item) {
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
        return new ResponseEntity<>("create success", HttpStatus.OK);
    }

    @GetMapping("/retrieveAll")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = new ArrayList<>();
        items = itemRepo.findAll();
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/retrieveByName")
    public ResponseEntity<List<Item>> getItemsByName(@Valid @RequestParam("name") @NotBlank String name) {
        List<Item> items = new ArrayList<>();
        items = itemRepo.findByName(name);
        if (items.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/retrieveByCategory")
    public ResponseEntity<List<Item>> getItemsByCategory(@Valid @RequestParam("category")
            @NotBlank @Pattern(regexp = "(^dairy$|^meat$|^seafood$|^vegetable$|^fruit$|^beverage$|^cereal$)") String category) {
        List<Item> items = new ArrayList<>();
        items = itemRepo.findByCategory(category);
        if (items.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/retrieveByLastUpdated")
    public ResponseEntity<List<Item>> getItemsByLastUpdated(@Valid @RequestParam("date") String date) throws ParseException {
        List<Item> items = new ArrayList<>();
        Date today = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        today = TimeUtil.getStandardDay(today);
        items = itemRepo.findByLastUpdated(today, TimeUtil.getStandardNextDay(today));
        if (items.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/retrieveByNameAndCategory")
    public ResponseEntity<List<Item>> getItemsByNameAndCategory(@Valid @RequestParam("name") @NotBlank String name,
            @Valid @RequestParam("category") @NotBlank @Pattern(regexp = "(^dairy$|^meat$|^seafood$|^vegetable$|^fruit$|^beverage$|^cereal$)") String category) {
        List<Item> items = new ArrayList<>();
        items = itemRepo.findByNameAndCategory(name, category);
        if (items.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/retrieveByNameAndLastUpdated")
    public ResponseEntity<List<Item>> getItemsByNameAndLastUpdated(@Valid @RequestParam("name") @NotBlank String name,
            @Valid @RequestParam("date") String date) throws ParseException {
        List<Item> items = new ArrayList<>();
        Date today = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        today = TimeUtil.getStandardDay(today);
        items = itemRepo.findByNameAndLastUpdated(name, today, TimeUtil.getStandardNextDay(today));
        if (items.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateItem(@Valid @RequestBody Item item) {
        String id = item.getId();
        Optional<Item> itemData = itemRepo.findById(new ObjectId(id));
        if (itemData.isPresent()) {
            Item _item = itemData.get();
            _item.setQuantity(item.getQuantity());
            _item.setComment(item.getComment());
            _item.setPrice(item.getPrice());
            _item.setLastUpdatedTime(new Date());
            itemRepo.save(_item);
            return new ResponseEntity<>("update success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Resource Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") String id) {
        itemRepo.deleteById(new ObjectId(id));
        return new ResponseEntity<>("delete success", HttpStatus.OK);
    }
}
