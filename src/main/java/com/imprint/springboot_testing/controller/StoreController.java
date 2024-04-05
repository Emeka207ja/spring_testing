package com.imprint.springboot_testing.controller;

import com.imprint.springboot_testing.Dto.Store.CreateStoreDto;
import com.imprint.springboot_testing.Dto.Store.CreateStoreResponse;
import com.imprint.springboot_testing.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/store")
public class StoreController {
    @Autowired
    private StoreService storeService;
    @PostMapping("/create_store")
    public ResponseEntity<CreateStoreResponse> createStore(@RequestBody CreateStoreDto createStoreDto){
        return  new ResponseEntity<>(storeService.createStore(createStoreDto), HttpStatus.CREATED);
    }
}
