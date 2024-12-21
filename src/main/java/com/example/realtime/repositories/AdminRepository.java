package com.example.realtime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.realtime.entities.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer>{

	@Query("SELECT a FROM Admin a WHERE a.mailId = :mail AND a.password = :Password")
	Admin findAdminByMailAndPassword(@Param("mail")String mail,@Param("Password")String Password);
	
	@Query("SELECT a FROM Admin a WHERE a.mailId=:mail")
	Admin findAdminByMailId(@Param("mail")String mail);
}
