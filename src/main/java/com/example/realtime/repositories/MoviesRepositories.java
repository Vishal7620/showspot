package com.example.realtime.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.realtime.entities.Movies;

import jakarta.transaction.Transactional;

@Repository
public interface MoviesRepositories extends JpaRepository<Movies, Long>{

	List<Movies> findByLocation(String location);
	
//	@Query("SELECT m FROM Movies m where m.id = :id")
//	Movies findMoviesById(@Param("id") Long id);

	Movies findMoviesById(Long id);
	
	Movies findByTitle(String title);
	
	@Modifying
	@Query("Delete From Movies m Where m.id=:movieId")
	void deleteMovieByMovieId(@Param("movieId")Long movieId);

}
