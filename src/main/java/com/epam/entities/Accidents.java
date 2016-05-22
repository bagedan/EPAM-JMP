package com.epam.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accidents")
public class Accidents {

	@Id
	@Column(name = "Accident_Index")
	private String accidentIndex;

	@Column(name = "Road_Surface_Conditions")
	private int roadCondition;

	@Column(name = "Weather_Conditions")
	private int weatherConditions;

	@Column(name = "Date")
	private Date date;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column(name = "Time")
	private String time;

	public String getAccidentIndex() {
		return accidentIndex;
	}

	public void setAccidentIndex(String accidentIndex) {
		this.accidentIndex = accidentIndex;
	}

	public int getRoadCondition() {
		return roadCondition;
	}

	public void setRoadCondition(int roadCondition) {
		this.roadCondition = roadCondition;
	}

	public int getWeatherConditions() {
		return weatherConditions;
	}

	public void setWeatherConditions(int weatherConditions) {
		this.weatherConditions = weatherConditions;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
