package com.seen.lspmyhr.repository;

import com.seen.lspmyhr.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, UUID> {
    Optional<UserApp> findByUsernameIgnoreCase(String username);
}
