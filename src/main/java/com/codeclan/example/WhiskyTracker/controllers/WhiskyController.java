package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;import org.springframework.http.ResponseEntity;
import com.codeclan.example.WhiskyTracker.models.Distillery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/whiskies")
public class WhiskyController {

    @Autowired
    WhiskyRepository whiskyRepository;

    @GetMapping
    public ResponseEntity<List<Whisky>> getAllWhiskys(
            @RequestParam(name="year", required=false) Integer year,
            @RequestParam(name = "distillery", required = false) String distillery,
            @RequestParam(name = "age", required = false) Integer age,
            @RequestParam(name = "region", required = false) String region
    ) {
        if (region != null) {
            return new ResponseEntity<>(whiskyRepository.findByDistilleryRegion(region), HttpStatus.OK);
        }
        if (year != null) {
            return new ResponseEntity<>(whiskyRepository.findByYear(year), HttpStatus.OK);
        }
        if (distillery != null && age != null){
            return new ResponseEntity<>(whiskyRepository.findByDistilleryNameAndAge(distillery, age), HttpStatus.OK);
        }
        return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Distillery> getWhisky(@PathVariable Long id){
        return new ResponseEntity(whiskyRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Whisky> createWhisky(@RequestBody Whisky whisky){
        whiskyRepository.save(whisky);
        return new ResponseEntity<>(whisky, HttpStatus.CREATED);
    }
}
