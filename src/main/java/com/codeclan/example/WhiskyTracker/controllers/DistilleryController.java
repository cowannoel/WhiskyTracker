package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;

import com.codeclan.example.WhiskyTracker.repositories.DistilleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping(value = "/distilleries")
public class DistilleryController {
    @Autowired
    DistilleryRepository distilleryRepository;

    @GetMapping
    public ResponseEntity<List<Distillery>> getAllDistilleries(
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "age", required = false) Integer age
    ) {
        if (age != null) {
            return new ResponseEntity<>(distilleryRepository.findByWhiskiesAge(age), HttpStatus.OK);
        }
        if (region != null) {
            return new ResponseEntity<>(distilleryRepository.findByRegionIgnoreCase(region), HttpStatus.OK);
        }
        return new ResponseEntity<>(distilleryRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Distillery> getDistillery(@PathVariable Long id){
        return new ResponseEntity(distilleryRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Distillery> createDistillery(@RequestBody Distillery distillery){
        distilleryRepository.save(distillery);
        return new ResponseEntity<Distillery>(distillery, HttpStatus.CREATED);
    }


}
