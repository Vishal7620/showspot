package com.example.realtime.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.realtime.entities.Theaters;
import com.example.realtime.entities.seats_layout;
import com.example.realtime.repositories.SeatLayoutRepository;

import jakarta.transaction.Transactional;

@Service
public class SeatLayoutService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SeatLayoutRepository seatLayoutRepository;
	
//	public List<seats_layout> findLayoutByTheaterId(Theaters theater_id)
//	{
//		return seatLayoutRepository.findSeatLayoutByTheaterId(theater_id);
//	}
	
	public List<seats_layout> findLayoutByLayoutName(String layout)
	{
        String s1=layout.substring(0,2);
        
        String s2=layout.substring(2,4);
        String s3=layout.substring(4,6);
        
        return seatLayoutRepository.findSeatLayoutByName(s1, s2, s3);
		 
	}
	
	public String generateLayout(String categoryInitial)
	{
		String sql="SELECT layout_type " +
	             "FROM seats_layout " +
	             "WHERE layout_type LIKE ? " +
	             "ORDER BY CAST(SUBSTRING(layout_type, LENGTH(?) + 1) AS UNSIGNED) DESC " +
	             "LIMIT 1";
		
		try
		{
			String lastUsedLayoutType= jdbcTemplate.queryForObject(sql, new Object[]{categoryInitial.toUpperCase() + "%", categoryInitial.toUpperCase()},
			        String.class);
			
			int lastNumber = Integer.parseInt(lastUsedLayoutType.substring(categoryInitial.length()));
            int nextNumber = lastNumber + 1;

            // Return the new layout type
            return categoryInitial.toUpperCase() + nextNumber;

        } catch (Exception e) {
            // If no layout type exists, start with 1
            return categoryInitial.toUpperCase() + "1";
        }
		
	
	}
	
	public String updateSeatLayoutUsingAdminPannel(String categoryName, String rowCount, String columCount,String price)
	{
		String updatedLayout="";
		try {
			
			
			  int rowCount1 = Integer.parseInt(rowCount);
		        int columnCount1 = Integer.parseInt(columCount);
		        double price1 = Double.parseDouble(price);

	        
	        Long row=(long)rowCount1;
	         Long column=(long)columnCount1;
	         int p=(int)price1;
	        
	        String tempcategoryName=categoryName.substring(0,1).toUpperCase();
	        tempcategoryName+=categoryName.substring(1,categoryName.length()).toLowerCase();
	        seats_layout seatLayout=new seats_layout();
	        
	        seatLayout.setColumn_count(column);
	        seatLayout.setRow_count(row);
	        seatLayout.setCategoryName(tempcategoryName);
	        seatLayout.setPrice(p);
	          updatedLayout=generateLayout(categoryName.substring(0,1));
	        seatLayout.setLayout(updatedLayout);
			
	        seatLayoutRepository.save(seatLayout);
	       
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return updatedLayout;
	}
	
	public seats_layout findSeatLayoutById(Long id)
	{
		try {
			return seatLayoutRepository.findSeatLayoutById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional
	public void deleteLayoutById(Long id)
	{
		seatLayoutRepository.deleteSeatLayoutById(id);
		
	}
}
