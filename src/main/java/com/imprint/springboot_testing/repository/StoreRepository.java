package com.imprint.springboot_testing.repository;

import com.imprint.springboot_testing.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Boolean existsByName(String name);
}
