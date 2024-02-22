package com.imprint.springboot_testing.Dto.Store;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateStoreResponse {
    private Long storeId;
    private String name;
}
