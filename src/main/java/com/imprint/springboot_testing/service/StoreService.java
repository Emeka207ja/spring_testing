package com.imprint.springboot_testing.service;

import com.imprint.springboot_testing.Dto.Store.CreateStoreDto;
import com.imprint.springboot_testing.Dto.Store.CreateStoreResponse;

public interface StoreService {
    CreateStoreResponse createStore(CreateStoreDto createStoreDto);
}
