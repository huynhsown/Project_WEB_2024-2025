package com.adidark.repository;

import com.adidark.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Long> {

    Optional<SizeEntity> findBySize(BigDecimal size);

}
