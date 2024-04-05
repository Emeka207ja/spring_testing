package com.imprint.springboot_testing.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imprint.springboot_testing.Dto.Store.CreateStoreDto;
import com.imprint.springboot_testing.Dto.Store.CreateStoreResponse;
import com.imprint.springboot_testing.repository.StoreRepository;
import com.imprint.springboot_testing.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StoreControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setup(){
//        storeRepository.deleteAll();
    }
    @DisplayName("integration test for create store")
    @Test
    public  void createStore() throws Exception{
        CreateStoreDto createStoreDto = new CreateStoreDto();
        createStoreDto.setName("emy store");
        CreateStoreResponse createStoreResponse = CreateStoreResponse.builder()
                .message("store created")
                .build();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/store//create_store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createStoreDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(createStoreResponse.getMessage()))
                .andDo(MockMvcResultHandlers.print());

        System.out.println(resultActions);
    }
}
