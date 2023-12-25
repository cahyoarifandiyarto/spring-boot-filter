package com.belajar.springbootfilter.repository;

import com.belajar.springbootfilter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

}
