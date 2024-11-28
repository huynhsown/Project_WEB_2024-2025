package com.adidark.repository;


import com.adidark.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> findByTelephoneContainingIgnoreCase(String query, Pageable pageable);
    @Query("SELECT u FROM UserEntity u WHERE LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<UserEntity> findByFirstNameOrLastNameContainingIgnoreCase(String query, Pageable pageable);
}
