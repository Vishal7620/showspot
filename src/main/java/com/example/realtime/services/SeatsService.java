package com.example.realtime.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.realtime.entities.Bookings;
import com.example.realtime.entities.Seats;
import com.example.realtime.entities.User;
import com.example.realtime.repositories.BookingRepository;
import com.example.realtime.repositories.SeatsRepositories;

import ch.qos.logback.core.util.Duration;
import jakarta.transaction.Transactional;

@Service
public class SeatsService {

	@Autowired
	private SeatsRepositories seatsRepositories;
	
	@Autowired
	private BookingRepository bookingRepository;
	
//	public List<Seats> findSeatsByTheaterId(Long theater_id)
//	{
//		return seatsRepositories.findSeats(theater_id);
//	}
//	
	public List<Seats> findSeatsByLayout(String layout)
	{ String s1=layout.substring(0,2);
    
    String s2=layout.substring(2,4);
    String s3=layout.substring(4,6);
    
    return seatsRepositories.findSeatsByLayout(s1, s2, s3);
		
	}
	
	public Seats findSeatsById(Long id)
	{
		return seatsRepositories.findSeatBySeatId(id);
	}
	
	
	
//	@Transactional
//	public boolean bookSeat(Long seatId,Long userId)
//	{
//		Optional<Seats> seatOpt=seatsRepositories.findByIdForUpdates(seatId);
//		
//		if(seatOpt.isPresent())
//		{
//			Seats seat= seatOpt.get();
//			
//			// we have to fetch the user by userid
//			User user=new User();
//			Seats dummySeat=seatsRepositories.findSeatBySeatId(seatId);
//			if(!seat.isAvailable())
//			{
//				System.out.println("Seat Already booked");
//			}
//			
//			seat.setAvailable(false);
//			seatsRepositories.save(seat);
//			
//			Bookings booking=new Bookings();
//			booking.setUser(user);
//			booking.setSeat(dummySeat);
//			bookingRepository.save(booking);
//			
//			return true;
//		}
//		else
//		{
//			System.out.println("Seat not found");
//		}
//		
//		return false;
//	}
	
	
//	@Transactional
//	public boolean holdSeat(Long seatId)
//	{
//		Optional<Seats> seatOptional=seatsRepositories.findById(seatId);
//		
//		System.out.println("In the holdSeat-->"+seatId);
//		
//		if(seatOptional.isPresent())
//		{
//			Seats seat=seatOptional.get();
//			
//			System.out.println("present-->"+seat.getSeatId());
//			if(seat.isAvailable()&&! seat.isHeld())
//			{
//				seat.setHeld(true);
//				seat.setAvailable(false);
//				seat.setHoldExpirationTime(LocalDateTime.now().plusMinutes(3));
//				seatsRepositories.save(seat);
//				return true;
//			}
//		}
//		
//		return false;
//		
//	}
	
	@Transactional
	public boolean holdSeat(Long seatId)
	{
		Seats seat=seatsRepositories.findSeatBySeatId(seatId);
		
		System.out.println("In the holdSeat-->"+seatId);
		
		if(seat!=null)
		{
			
			System.out.println("present-->"+seat.getSeatId());
			if(seat.isAvailable()&&! seat.isHeld())
			{
				seat.setHeld(true);
				seat.setAvailable(false);
				seat.setHoldExpirationTime(LocalDateTime.now().plusMinutes(3));
				seatsRepositories.save(seat);
				return true;
			}
		}
		
		return false;
		
	}
	
	
	
	   @Transactional
	    public void releaseExpiredHolds(Long seatId) {
	        Optional<Seats> seatOptional = seatsRepositories.findById(seatId);

	        if (seatOptional.isPresent()) {
	            Seats seat = seatOptional.get();

	            // Release the hold manually if needed
	            seat.setHeld(false);
	            seat.setAvailable(true);
	            seatsRepositories.save(seat);
	        }
	    }
	   
	   @Transactional
	   public void makeAvailable(List<Seats>seats)
	   {
		   for(Seats seat:seats)
		   {
			   seat.setAvailable(false);
		   }
		   
		   seatsRepositories.saveAll(seats);
	   }
	   
	   
//	   public void addSeats(int row,int column,String layout_type,int price,List<Integer>blankedSeats)
//	   {
//		   char seatNumbar='A';
//		 int globalSeatNumber=0;  
//		   for(int i=1;i<=row;i++)
//		   {
//			   for(int j=1;j<=column;j++)
//			   {
//				   ++globalSeatNumber;
//				   Seats seat=new Seats();
//				   
//				   seat.setAvailable(true);
//				   seat.setColumnNumber(j);
//				   seat.setHeld(false);
//				   seat.setHoldExpirationTime(null);
//				   seat.setPrice(price);
//				   seat.setSeatRow(i);
//				   seat.setBooking(null);
//				   seat.setLayout_type(layout_type);
//				   String stringNumber="";
//				   
//				   stringNumber+=layout_type;
//				   stringNumber+=" ";
//				   stringNumber+=Integer.toString(j);
//				   
//				   seat.setSeatNumber(stringNumber);
//				   
//				   for(int k=0;k<blankedSeats.size();k++)
//				   {
//					   if(globalSeatNumber==blankedSeats.get(k))
//					   {
//						   seat.setAvailable(false);
//						   seat.setGap(true);
//						   blankedSeats.remove(k);
//					   }
//				   }
//				   
//				   seatsRepositories.save(seat);
//			   }
//		   }
//	   }
	   public void addSeats(int row, int column, String layout_type, int price, List<Integer> blankedSeats,char rowIdentifier) {
		    //char rowIdentifier = 'A'; // Start with 'A'
		    int seatNumber = 0; // Seat number for each row
		    int globalSeatNumber = 0; // Tracks the global seat position

		    for (int i = 1; i <= row; i++) {
		        seatNumber = 0; // Reset seat number for each row
		        for (int j = 1; j <= column; j++) {
		            ++globalSeatNumber;

		            // Check if this seat is a gap
		            if (blankedSeats.contains(globalSeatNumber)) {
		                // Create a gap seat
		                Seats seat = new Seats();
		                seat.setAvailable(false);
		                seat.setGap(true);
		                seat.setColumnNumber(j);
		                seat.setSeatRow(i);
		                seat.setLayout_type(layout_type);
		                seat.setPrice(price);
		                seat.setHeld(false);
		                seat.setHoldExpirationTime(null);
		                seat.setSeatNumber("Gap"); // Optional, just to mark as a gap
		                seat.setBooking(null);

		                seatsRepositories.save(seat);
		                blankedSeats.remove(Integer.valueOf(globalSeatNumber)); // Remove gap from list
		                continue;
		            }

		            // Increment seat number for valid seats
		            seatNumber++;

		            // Create the seat object
		            Seats seat = new Seats();
		            seat.setAvailable(true);
		            seat.setColumnNumber(j);
		            seat.setHeld(false);
		            seat.setHoldExpirationTime(null);
		            seat.setPrice(price);
		            seat.setSeatRow(i);
		            seat.setBooking(null);
		            seat.setLayout_type(layout_type);

		            // Generate the seat number, e.g., A1, A2, etc.
		            String seatIdentifier = rowIdentifier + String.valueOf(seatNumber);
		            seat.setSeatNumber(seatIdentifier);

		            seatsRepositories.save(seat);
		        }
		        rowIdentifier++; // Move to the next row, e.g., A -> B
		    }
		}

	   
	   @Transactional
	   public void deleteSeatsbyLayoutType(String layout_type)
	   {
		   seatsRepositories.deleteSeatsByLayoutType(layout_type);
	   }
	 
	 
}