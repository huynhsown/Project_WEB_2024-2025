package com.adidark.repository;

import com.adidark.entity.ProductEntity;
import com.adidark.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Long> {
}
