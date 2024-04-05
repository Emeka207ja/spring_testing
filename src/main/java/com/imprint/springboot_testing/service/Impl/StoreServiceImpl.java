package com.imprint.springboot_testing.service.Impl;

import com.imprint.springboot_testing.Dto.Store.CreateStoreDto;
import com.imprint.springboot_testing.Dto.Store.CreateStoreResponse;
import com.imprint.springboot_testing.exception.ResourceExistException;
import com.imprint.springboot_testing.model.Store;
import com.imprint.springboot_testing.repository.StoreRepository;
import com.imprint.springboot_testing.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreRepository storeRepository;
    @Override
    public CreateStoreResponse createStore(CreateStoreDto createStoreDto) {
        if(storeExistsByName(createStoreDto.getName())){
            throw new ResourceExistException("store exist");
        }
        Store store = Store.builder()
                .name(createStoreDto.getName())
                .build();
        Store savedStore = storeRepository.save(store);
        return CreateStoreResponse.builder()
                .message("store created")
                .build();
    }
    private Boolean storeExistsByName(String storeName){
        return  this.storeRepository.existsByName(storeName);
    }
}
