package com.thinkify.events.repo;

import com.thinkify.events.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query("SELECT u from User u WHERE u.email = :email")
    public User getUserByEmail(@Param("email") String email);
}
