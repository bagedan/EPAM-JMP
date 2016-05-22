package com.epam.dbrepositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epam.entities.Accidents;

@Repository
public interface AccidentRepository extends CrudRepository<Accidents, String> {

	// declare your query methods for default and if you want to execute any
	// custom queries use @Query annotation.
	@Query(value = "SELECT * FROM Accidents WHERE roadCondition := roadCondition")
	List<Accidents> findByRoadCondition(@Param("roadCondition") int roadCondition);

	@Query(value = "SELECT * FROM Accidents WHERE weatherConditions = :weatherConditions AND to_char(a.date, 'YYYY') = :year")
	List<Accidents> findByWeatherConditionsAndYear(@Param("weatherConditions") int weatherConditions,
			@Param("year") String year);

	@Query(value = "SELECT * FROM accidents WHERE date = :date")
	List<Accidents> findByDate(@Param("date") Date date);
}
