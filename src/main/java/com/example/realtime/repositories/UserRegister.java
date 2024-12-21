package com.example.realtime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.realtime.entities.RegisterUser;

@Repository
public interface UserRegister extends JpaRepository<RegisterUser, Integer>{

}
