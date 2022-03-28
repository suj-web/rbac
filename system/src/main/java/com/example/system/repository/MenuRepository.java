package com.example.system.repository;

import com.example.system.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author suj
* @date 2022-03-28
*/
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor {
}