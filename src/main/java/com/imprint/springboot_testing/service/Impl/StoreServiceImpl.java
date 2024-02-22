package com.imprint.springboot_testing.service.Impl;

import com.imprint.springboot_testing.Dto.Store.CreateStoreDto;
import com.imprint.springboot_testing.Dto.Store.CreateStoreResponse;
import com.imprint.springboot_testing.exception.ResourceExistException;
import com.imprint.springboot_testing.model.Store;
import com.imprint.springboot_testing.repository.StoreRepository;
import com.imprint.springboot_testing.service.StoreService;

public class StoreServiceImpl implements StoreService {
    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

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
                .storeId(savedStore.getStoreId())
                .name(savedStore.getName())
                .build();
    }
    private Boolean storeExistsByName(String storeName){
        return  this.storeRepository.existsByName(storeName);
    }
}
