package edu.gatech.cse6242.cbioportal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Arrays;

@RequestMapping("api")
@RestController
public class ApiController {

    @GetMapping("helloworld")
    public ResponseEntity<List<String>> getPatients() {
        return new ResponseEntity<>(Arrays.asList("hello world"), HttpStatus.OK);
    }
}