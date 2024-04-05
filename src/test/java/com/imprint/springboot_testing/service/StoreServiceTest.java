package com.imprint.springboot_testing.service;

import com.imprint.springboot_testing.Dto.Store.CreateStoreDto;
import com.imprint.springboot_testing.Dto.Store.CreateStoreResponse;
import com.imprint.springboot_testing.exception.ResourceExistException;
import com.imprint.springboot_testing.model.Store;
import com.imprint.springboot_testing.repository.StoreRepository;
import com.imprint.springboot_testing.service.Impl.StoreServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {
    @Mock
    private StoreRepository storeRepository;
    @InjectMocks
    private StoreServiceImpl storeService;
    @BeforeEach
    void setup(){

    }
    @DisplayName("junit test for create store when store name does not exist")
    @Test
    public void createStoreServicePC(){
        CreateStoreDto createStoreDto = new CreateStoreDto();
        createStoreDto.setName("emy store");
        Store store = Store.builder()
                .storeId(1L)
                .name(createStoreDto.getName())
                .employees(new ArrayList<>())
                .build();
        BDDMockito.given(storeRepository.existsByName(createStoreDto.getName())).willReturn(false);
        BDDMockito.given(storeRepository.save(Mockito.any(Store.class))).willReturn(store);
        CreateStoreResponse response = storeService.createStore(createStoreDto);
        assertThat(response.getMessage()).isNotNull();
        assertThat(response.getMessage()).isEqualTo("store created");
    }
    @DisplayName("junit test for create store when store name exist")
    @Test
    public void createStoreServiceNC(){
        CreateStoreDto createStoreDto = new CreateStoreDto();
        createStoreDto.setName("emy store");
        BDDMockito.given(storeRepository.existsByName(createStoreDto.getName())).willReturn(true);
        Assertions.assertThrows(ResourceExistException.class,()->storeService.createStore(createStoreDto));
    }
}