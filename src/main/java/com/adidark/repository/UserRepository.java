package com.adidark.repository;

import com.adidark.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    List<UserEntity> findByLastNameContainingIgnoreCase(String query);
    Page<UserEntity> findByTelephoneContainingIgnoreCase(String query, Pageable pageable);
    Page<UserEntity> findByLastNameContainingIgnoreCase(String query, Pageable pageable);
}
