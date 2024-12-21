package com.example.realtime.controller;

import java.security.KeyStore.Entry;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.realtime.entities.Admin;
import com.example.realtime.entities.Bookings;
import com.example.realtime.entities.Movies;
import com.example.realtime.entities.RegisterUser;
import com.example.realtime.entities.Seats;
import com.example.realtime.entities.TheaterTimmings;
import com.example.realtime.entities.Theaters;
import com.example.realtime.entities.User;
import com.example.realtime.entities.seats_layout;
import com.example.realtime.repositories.AdminRepository;
import com.example.realtime.repositories.BookingRepository;
import com.example.realtime.repositories.MoviesRepositories;
import com.example.realtime.repositories.SeatLayoutRepository;
import com.example.realtime.repositories.SeatsRepositories;
import com.example.realtime.repositories.TheaterRepositories;
import com.example.realtime.repositories.UserRegister;
import com.example.realtime.repositories.UserRepository;
import com.example.realtime.services.MoviesService;
import com.example.realtime.services.SeatLayoutService;
import com.example.realtime.services.SeatsService;
import com.example.realtime.services.TheaterService;
import com.example.realtime.services.TheaterTimmingServices;
import com.example.realtime.services.adminService;
import com.example.realtime.services.bookingService;
import com.example.realtime.services.sendEmailService;
import com.example.realtime.services.userService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@RestController
public class MoviesController {

	@Autowired
	private MoviesService moviesService;
	@Autowired
	private TheaterService theaterService;

	@Autowired
	private SeatLayoutService seatLayoutService;
	
	@Autowired
	private SeatsService seatsService;
	
	@Autowired
	private BookingRepository bookingsRepository;
	
	@Autowired
	private TheaterTimmingServices theaterTimmingServices;
	
	@Autowired
	private userService user_service;
	
	@Autowired
	private bookingService book_service;
	
	@Autowired
	private sendEmailService mailService;
	
	@Autowired
	private MoviesRepositories movieRepository;
	
	@Autowired 
	private TheaterRepositories theaterRepositories;
	
	@Autowired
	private SeatLayoutRepository seatLayoutRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRegister userRegister;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private adminService admin_service;
	
	@RequestMapping(value="/views/selectedMovie", method=RequestMethod.POST)
	 public ModelAndView handleCitySubmission(@RequestParam("city") String city)
	 {
		 ModelAndView modelandView=new ModelAndView();
	
		 try
		 {
		 List<Movies>movies=moviesService.getAllMoviesByLocation(city);
		 
		 modelandView.addObject("movies",movies);
		 modelandView.addObject("selectedCity",city);
		 modelandView.setViewName("homeCityWise");
		 }
		 catch(Exception e)
		 {
			 modelandView.setViewName("error");
			 e.printStackTrace();
		 }
		 return modelandView;
	 }
	
	@GetMapping("/views/selectedMovie")
	public ModelAndView getMovie(@RequestParam("movieId")long id)
	{
		ModelAndView modelandView=new ModelAndView();
	
		try
		{
		Movies movie=moviesService.getMoviesById(id);
      
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMM yyyy");
		String formattedDate=movie.getReleaseDate()!=null ? dateFormat.format(movie.getReleaseDate()):" ";
		
	    modelandView.addObject("formattedDate",formattedDate);
	    modelandView.addObject("movie",movie);
		
	    modelandView.setViewName("selectedMovie");
		}
		catch (Exception e) {
			// TODO: handle exception
		modelandView.setViewName("error");
		}
	    return modelandView;
	}
	
	
	
	@GetMapping("/views/selectTheater")
	public ModelAndView bookTickets(@RequestParam("movieId")long movieId,@RequestParam("city")String city)
	{
		ModelAndView modelandView =new ModelAndView();
		try
		{
			//for movies
		Movies movie=moviesService.getMoviesById(movieId);
		modelandView.addObject("movie",movie);
		modelandView.addObject("city",city);
		
		//for theater- timming data fetching start
		
		List<Theaters>theaters=theaterService.findTheatersByCityAndByMovieId(city,movieId);
		
		
		
		for(Theaters t:theaters)
		{
			System.out.println(t.getTheaterName());
		}
		
		Map<String,List<String>> map=new HashMap<>();
		SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm");
		
		for(Theaters theater:theaters)
		{
			   String theaterName = theater.getTheaterName();
	            String formattedDate = theater.getMovieTimings() != null ? dateFormat.format(theater.getMovieTimings()) : " ";

	            // Get the list associated with the current theaterId, or create a new list if it doesn't exist
	            List<String> timings = map.computeIfAbsent(theaterName, k -> new ArrayList<>());
	            timings.add(formattedDate);
		}
	
		
		Set<String>dummySet=new LinkedHashSet<>();
		List<Long>id=new ArrayList<>();
		for(Theaters theater:theaters)
		{
			dummySet.add(theater.getTheaterName());
			id.add(theater.getTheaterId());
		}
		
		Set<Theaters> newUpdatedTheater=new LinkedHashSet<>();
		int i=0;
		for(String str:dummySet)
		{
			Theaters t=new Theaters();
			t=theaterService.getTheaterByTheaterNameAndId(str, id.get(i));
			newUpdatedTheater.add(t);
			++i;
		}
		
		for(Long t:id)
		{
			System.out.println(t);
		}
		
		modelandView.addObject("theaters",newUpdatedTheater);
		
	    modelandView.addObject("theater-timing",map);
		 
	    modelandView.addObject("theaterId",id);
	    
	    //theater theater timming data fetching
		
		modelandView.setViewName("selectTheater");
		}
		catch (Exception e) {
			// TODO: handle exception
			modelandView.setViewName("error");
			e.printStackTrace();
		}
		return modelandView;
	}
	
	@GetMapping("/views/seatLayout")
	public ModelAndView selectTimmingsToproceed(@RequestParam("movieId")long movieId,@RequestParam("city")String city,
			@RequestParam("theaterId")Long theaterId,@RequestParam("timming")String timming,@RequestParam("date")String formatteddate1)
	{
		ModelAndView modelAndView=new ModelAndView();
		
		try
		{
			Theaters theater=theaterService.findTheaterById(theaterId);
			
			Movies movie=moviesService.getMoviesById(movieId);
			
			List<seats_layout> listOfSeatLayout=seatLayoutService.findLayoutByLayoutName(theater.getLayout());
			
			List<Seats>seatList=seatsService.findSeatsByLayout(theater.getLayout());
			
			modelAndView.addObject("date",formatteddate1);
			modelAndView.addObject("timming",timming);
			modelAndView.addObject("movie",movie);
			modelAndView.addObject("theater",theater);
			
			modelAndView.addObject("listOfSeatLayout",listOfSeatLayout);
			
			modelAndView.addObject("seatList",seatList);
			
		    modelAndView.setViewName("seatLayout");
		}
		catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	@PostMapping("/bookingSummary")
	public ModelAndView handleBookings(@RequestParam("selectedSeats")String selectedSeats,
			                           @RequestParam("movieId")Long movieId,
			                           @RequestParam("theaterId")Long theaterId)
	{
		ModelAndView modelAndView =new ModelAndView();
		try
		{
			String[] seatIds=selectedSeats.split(",");
			Movies movie=moviesService.getMoviesById(movieId);
			Theaters theater=theaterService.findTheaterById(theaterId);
			modelAndView.addObject("seatIds",seatIds);
			
			List<Seats>bookedSeats=new ArrayList<>();
			for(String seatid:seatIds)
			{
				Seats seat=new Seats();
				Long temp=Long.parseLong(seatid);
				seat=seatsService.findSeatsById(temp);
				bookedSeats.add(seat);
			}
			
			modelAndView.addObject("bookedSeats",bookedSeats);
			modelAndView.addObject("movieId",movie);
			modelAndView.addObject("theaterId",theater);
			modelAndView.setViewName("bookingSummary");
			
		}
		catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		
		
		return modelAndView;
	}
	

	@PostMapping("/payment")
	public ModelAndView showPaymentPage(@RequestParam("bookedSeats") String bookedSeats,
			                            @RequestParam("movieId")Long movieId,@RequestParam("theaterId")Long thetaerId,
			                            @RequestParam("subTotal")float subTotal) {
	    ModelAndView modelAndView = new ModelAndView("payment");

	    // Split seat numbers by comma and trim any extra spaces
	    List<String> seatNumbers = Arrays.asList(bookedSeats.split(","));
         
	    Movies movies=moviesService.getMoviesById(movieId);
	    Theaters theater=theaterService.findTheaterById(thetaerId);
	    
	    // Fetch seat objects using their IDs
	    List<Seats> seatsList = seatNumbers.stream()
	        .map(seatNumber -> {
	            try {
	                return seatsService.findSeatsById(Long.parseLong(seatNumber.trim()));
	            } catch (NumberFormatException e) {
	                // Handle potential errors
	                return null;
	            }
	        })
	        .filter(Objects::nonNull) // Avoid adding null values to the list
	        .collect(Collectors.toList());
        
	    modelAndView.addObject("movieId",movies);
	    modelAndView.addObject("theaterId",theater);
	    modelAndView.addObject("subTotal",subTotal);
	    modelAndView.addObject("bookedSeats", seatsList);
	    return modelAndView;
	}

	@PostMapping("/submitPayment")
	public ModelAndView addDetails(HttpSession session, @RequestParam("bookedSeatIds")List<Long> bookedSeatsIds,
			                       @RequestParam("fullName")String fullName,
			                       @RequestParam("amount")float amount,
			                       @RequestParam("email")String mail,
			                       @RequestParam("contact")String contact,
			                       @RequestParam("movieId")Long movieId,
			                       @RequestParam("theaterId")Long theaterId,
			                       @RequestParam("currentDate")String currentDateStr)
	{
		
		ModelAndView modelandView=new ModelAndView();
		try
		{
			Date curreDate;
	        if (currentDateStr != null && !currentDateStr.isEmpty()) {
	            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	            curreDate = formatter.parse(currentDateStr);
	        } else {
	            curreDate = new Date();  // fallback to current date
	        }
			
			
			Movies movie=moviesService.getMoviesById(movieId);
			Theaters theater=theaterService.findTheaterById(theaterId);
			long totalAmount=(long)amount;
			long phone=Long.parseLong(contact);
			List<Seats>seatsList=new ArrayList<>();
			for(Long seatId:bookedSeatsIds)
			{
				Seats seat=new Seats();
				seat=seatsService.findSeatsById(seatId);
				seatsList.add(seat);
			}
			
		    //making the seats availibilities false
			
			User currentUser=user_service.getUserByMailId(mail);
			seatsService.makeAvailable(seatsList);
			//user_service.manageUser(mail, Long.parseLong(amount.replaceAll(",", "")));
			user_service.manageUser(mail, phone,fullName);
			 currentUser=user_service.getUserByMailId(mail);
			Bookings bookings=new Bookings();
			bookings.setBooking_date(curreDate);
			bookings.setFullName(fullName);
			bookings.setEmail(mail);
			bookings.setMovieId(movieId);
			bookings.setTheaterId(theaterId);
			bookings.setPhone(Long.parseLong(contact));
			//bookings.setAmount(Long.parseLong(amount.replaceAll(",", "")));
			bookings.setAmount(totalAmount);
			bookings.setBookedSeats(seatsList);
			bookingsRepository.save(bookings);
			modelandView.addObject("bookings",bookings);
			modelandView.addObject("movie",movie);
			modelandView.addObject("theater",theater);
			modelandView.addObject("amount",totalAmount);
			modelandView.addObject("bookedSeats",seatsList);
			
			session.setAttribute("currentUser", currentUser);
		
			/*Sending mail information */
			
			mailService.sendEmail(mail, movie.getTitle(), theater.getAddress(), seatsList);
			
			modelandView.setViewName("success");
		}
		catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			modelandView.setViewName("error");
		}
		
		return modelandView;
	}
	
	@GetMapping("/views/profile")
	public ModelAndView profile(HttpSession session)
	{
		ModelAndView modelAndView=new ModelAndView("profile");
		try {
			
			User currentUser=(User)session.getAttribute("currentUser");
			
			if(currentUser!=null)
			{
			
			List<Bookings>bookings=book_service.findAllBookings(currentUser.getEmail());
			
			
			List<Movies>moviesList=new ArrayList<>();
			List<Theaters>theatersList=new ArrayList<>();
			
			for(Bookings book:bookings)
			{
				moviesList.add(moviesService.getMoviesById(book.getMovieId()));
				theatersList.add(theaterService.findTheaterById(book.getTheaterId()));
			}
			
			modelAndView.addObject("recentBookings", bookings);
			modelAndView.addObject("moviesList", moviesList);
			modelAndView.addObject("theatersList", theatersList);
			}
			else
				modelAndView.setViewName("login");
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
		
	}
	
	@GetMapping("/login")
	public ModelAndView login()
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			
			modelAndView.setViewName("login");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	
	@PostMapping("/login")
	public ModelAndView register(HttpSession session,
			                     @RequestParam("email")String email,
			                     @RequestParam("password")String password)
	{
		ModelAndView modelAndView= new ModelAndView();
		try {
			
			User currentUser=user_service.getUserByMailId(email);
			
            List<Bookings>bookings=book_service.findAllBookings(currentUser.getEmail());
			
			
			List<Movies>moviesList=new ArrayList<>();
			List<Theaters>theatersList=new ArrayList<>();
			
			for(Bookings book:bookings)
			{
				moviesList.add(moviesService.getMoviesById(book.getMovieId()));
				theatersList.add(theaterService.findTheaterById(book.getTheaterId()));
			}
			
            session.setAttribute("currentUser", currentUser);
			modelAndView.addObject("recentBookings", bookings);
			modelAndView.addObject("moviesList", moviesList);
			modelAndView.addObject("theatersList", theatersList);
			
			modelAndView.setViewName("profile");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	@GetMapping("/views/admin")
	public ModelAndView adminSection()
	{
		ModelAndView modelAndView=new ModelAndView("adminPannel");
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	

	
	@GetMapping("/views/admin/addMovie")
	public ModelAndView addMovie()
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			
			modelAndView.setViewName("addMovie");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}
	
	@PostMapping("/admin/addMovie")
	public ModelAndView handleNewMoviedetails(@RequestParam("title") String title,
	        @RequestParam("genre") String genre,
	        @RequestParam("location") String location,
	        @RequestParam("releaseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseDate,
	        @RequestParam("Language") String language,
	        @RequestParam("duration") int duration,
	        @RequestParam("posterName") String posterName,
	        @RequestParam("about") String about)
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			modelAndView.setViewName("adminPannel");
			
			 Date date = Date.from(releaseDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//				LocalDateTime dateTime=releaseDate.atStartOfDay();
			
				Movies newMovie=new Movies();
				newMovie.setGenre(genre);
				newMovie.setTitle(title);
				newMovie.setAbout(about);
				newMovie.setDuration(duration);
				newMovie.setLanguage(language);
				newMovie.setLoaction(location);
				newMovie.setReleaseDate(date);
				newMovie.setPosterName(posterName);
				
				movieRepository.save(newMovie);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	
	
	@GetMapping("/views/admin/addShow")
	public ModelAndView addShow()
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			
			modelAndView.setViewName("addShow");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}
	
	@PostMapping("/admin/addShow")
	public ModelAndView handleNewShowdetails(@RequestParam("theaterName")String theaterName,
	        @RequestParam("address")String address,
	        @RequestParam("movieDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate movieDate,
	        @RequestParam("movieTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime movieTime,
	        @RequestParam("movieName")String movieName,
	        @RequestParam("layoutName")String layoutName)
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			modelAndView.setViewName("adminPannel");
			
			LocalDateTime movieDateTime = LocalDateTime.of(movieDate, movieTime);

		    // Convert LocalDateTime to java.util.Date
		    Date movieTimings = Date.from(movieDateTime.atZone(ZoneId.systemDefault()).toInstant());
		    
		    Theaters newTheater=new Theaters();
		    Movies newMovie=moviesService.findMovieByName(movieName);
             newTheater.setAddress(address);
             newTheater.setTheaterName(theaterName);
             newTheater.setMovieTimings(movieTimings);
             newTheater.setMovie(newMovie);
             newTheater.setLayout(layoutName);
             
              theaterRepositories.save(newTheater);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	
	
	@GetMapping("/views/admin/addSeats")
	public ModelAndView addSeats()
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			
			modelAndView.setViewName("addSeats");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}
	
	@PostMapping("/admin/addSeats")
	public ModelAndView handleNewSeatsetails(@RequestParam("categoryName1")String categoryName1,
	        @RequestParam("rowCount1")String rowCount1,
	        @RequestParam("columnCount1")String columnCount1,
	        @RequestParam("price1")String price1,
	        @RequestParam("categoryName2")String categoryName2,
	        @RequestParam("rowCount2")String rowCount2,
	        @RequestParam("columnCount2")String columnCount2,
	        @RequestParam("price2")String price2,
	        @RequestParam("categoryName3")String categoryName3,
	        @RequestParam("rowCount3")String rowCount3,
	        @RequestParam("columnCount3")String columnCount3,
	        @RequestParam("price3")String price3,
	        @RequestParam("seatNumbers1")String seatNumbers1)
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			modelAndView.setViewName("adminPannel");
			

			String [] seatArray=seatNumbers1.split(",");
			List<Integer>seatList=new ArrayList<>();
			for(String seat:seatArray)
			{
				int seatNumber=Integer.parseInt(seat.trim());
				seatList.add(seatNumber);
				
			}
			String s1="",s2="",s3="";
			
			char seatNumber='A';
	        s1= seatLayoutService.updateSeatLayoutUsingAdminPannel(categoryName1, rowCount1, columnCount1, price1);
	        
	       seatsService.addSeats(Integer.parseInt(rowCount1), Integer.parseInt(columnCount1), s1, Integer.parseInt(price1), seatList,seatNumber);
	       seatNumber=(char)(seatNumber+Integer.parseInt(rowCount1));
	        if(categoryName2!=null && rowCount2!=null && columnCount2!=null && price2!=null)
	        {
	        	 s2=seatLayoutService.updateSeatLayoutUsingAdminPannel(categoryName2, rowCount2, columnCount2, price2);
	        	 seatsService.addSeats(Integer.parseInt(rowCount2), Integer.parseInt(columnCount2), s2, Integer.parseInt(price2), seatList,seatNumber);
	        	  seatNumber=(char)(seatNumber+Integer.parseInt(rowCount2));
	        }
	        
	        if(categoryName3!=null && rowCount3!=null && columnCount3!=null && price3!=null)
	        {
	        	 s3=seatLayoutService.updateSeatLayoutUsingAdminPannel(categoryName3, rowCount3, columnCount3, price3);
	        	 seatsService.addSeats(Integer.parseInt(rowCount3), Integer.parseInt(columnCount3), s3, Integer.parseInt(price3), seatList,seatNumber);
	        }
	        
	        System.out.println("Seat List "+seatList);
	        System.out.println("Seat layout  "+ s1 + " "+s2+" "+s3);
	        
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	

	
	//Admin Remove code
	
	@PostMapping("/remove-movie")
	public ModelAndView removeMovie(@RequestParam("movieId")String movieId)
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			
			
			Long id=Long.parseLong(movieId);
			
			Movies movie=moviesService.getMoviesById(id);
			
		    theaterService.deleteTheaterByMovieId(movie);
		  
			moviesService.deleteMovieByMovieId(id);
			
		    modelAndView.setViewName("adminPannel");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	
	
	@PostMapping("/remove-show")
	public ModelAndView removeShow(@RequestParam("showId")String showId)
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			
			
			Long id=Long.parseLong(showId);
			
            theaterService.deleteTheaterById(id);
		    modelAndView.setViewName("adminPannel");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	
	@PostMapping("/remove-seatLayout")
	public ModelAndView removeSeatLayoutType(@RequestParam("seatLayoutId")String seatLayoutId)
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			
			
			Long id=Long.parseLong(seatLayoutId);
			
          seats_layout currentSeatLayout= seatLayoutService.findSeatLayoutById(id);
        
          System.out.println(currentSeatLayout);
         seatsService.deleteSeatsbyLayoutType(currentSeatLayout.getLayout());
          
          seatLayoutService.deleteLayoutById(id);
		    modelAndView.setViewName("adminPannel");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	
	@GetMapping("/views/register")
	public ModelAndView registerPage()
	{
		ModelAndView modelAndView=new ModelAndView();
		
		try {
			
			modelAndView.setViewName("register");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}
	
	@PostMapping("/registerUser")
	public ModelAndView registerUser(@RequestParam("firstname")String firstname,
			                         @RequestParam("lastname")String lastname,
			                          @RequestParam("email")String email,
			                          @RequestParam("address")String address,
			                          @RequestParam("mobile")Long mobile,
			                          @RequestParam("password")String password)
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
//			RegisterUser newUser=new RegisterUser();
//			newUser.setFirstName(firstname);
//			newUser.setLastName(lastname);
//			newUser.setMailId(email);
//			newUser.setAddress(address);
//			newUser.setMobNo(mobile);
//			newUser.setPassword(password);
			
			User present=user_service.getUserByMailId(email);
			
			if(present==null)
			{
			User user=new User();
			user.setEmail(email);
			user.setFullname(firstname);
			user.setMob(mobile);
			user.setPassword(password);
			
			userRepository.save(user);
			//userRegister.save(newUser);
			}
			modelAndView.setViewName("login");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	
	@PostMapping("/registerAdmin")
	public ModelAndView registerAdmin(@RequestParam("firstname")String firstname,
			                         @RequestParam("lastname")String lastname,
			                          @RequestParam("email")String email,
			                          @RequestParam("address")String address,
			                          @RequestParam("mobile")Long mobile,
			                          @RequestParam("password")String password)
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
//			RegisterUser newUser=new RegisterUser();
//			newUser.setFirstName(firstname);
//			newUser.setLastName(lastname);
//			newUser.setMailId(email);
//			newUser.setAddress(address);
//			newUser.setMobNo(mobile);
//			newUser.setPassword(password);
			
		    Admin present=admin_service.findAdmin(email);
		    if(present==null)
		    {
			Admin admin=new Admin();
			
			admin.setAddress(address);
			admin.setFirstName(firstname);
			admin.setLastName(lastname);
			admin.setMailId(email);
			admin.setMobNo(mobile);
			admin.setPassword(password);
			adminRepository.save(admin);
			//userRegister.save(newUser);
		    }
			modelAndView.setViewName("adminPannel");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	
	@GetMapping("/views/registerAdmin")
	public ModelAndView adminRegisterForm()
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			modelAndView.setViewName("registerAdmin");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	
	@GetMapping("/views/adminLogin")
	public ModelAndView adminLogin()
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			modelAndView.setViewName("adminLogin");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	
	@PostMapping("/adminLogin")
	public ModelAndView adminLogin(HttpSession session,
			                       @RequestParam("email")String email,
			                       @RequestParam("password")String password)
	{
		ModelAndView modelAndView=new ModelAndView();
		
		try {
			
			Admin currentAdmin=admin_service.valiodateAdmin(email, password);
			
				session.setAttribute("currentAdmin",currentAdmin);
			
			modelAndView.setViewName("adminPannel");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}
	
	@GetMapping("/adminLogout")
	public ModelAndView adminLogout(HttpSession session)
	{
		 ModelAndView modelAndView = new ModelAndView();
		try {
			
			session.setAttribute("currentAdmin", null);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		modelAndView.setViewName("redirect:/views/home");  // Redirect to the home page
	    return modelAndView;
	}
	@GetMapping("/views/error")
	public ModelAndView errorHandle()
	{
		ModelAndView modelAndView=new ModelAndView();
		
		
		try {
			modelAndView.setViewName("error");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	@GetMapping("/views/about")
	public ModelAndView aboutPage()
	{
		ModelAndView modelAndView=new ModelAndView();
		try {
			modelAndView.setViewName("about");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelAndView.setViewName("error");
			
		}
		return modelAndView;
	}
	
}
