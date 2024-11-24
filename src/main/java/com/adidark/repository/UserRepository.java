package com.adidark.repository;

package com.adidark.repository;

import com.adidark.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {




}
