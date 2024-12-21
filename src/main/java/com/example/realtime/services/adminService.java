package com.example.realtime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.realtime.entities.Admin;
import com.example.realtime.repositories.AdminRepository;

@Service
public class adminService {

	@Autowired
	private AdminRepository adminRepository;
	
	public Admin valiodateAdmin(String mail,String password)
	{
		Admin checkAdmin=adminRepository.findAdminByMailAndPassword(mail, password);
		if(checkAdmin!=null)
			{
			
			return checkAdmin;
			}
		
		return null;
	}
	public Admin findAdmin(String mail)
	{
		Admin admin=null;
	   try {
		admin=adminRepository.findAdminByMailId(mail);
	} catch (Exception e) {
		// TODO: handle exception
	}
	   return admin;
	}
}
