package com.epam.springboot.repository;

import com.epam.springboot.modal.RoadConditions;
import com.epam.springboot.modal.WeatherConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bill on 16-5-28.
 */
@Component("accidentService")
@Transactional
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository repository;
    private final RoadConditionRepository roadConditionRepository;
    private final WeatherConditionRepository weatherConditionRepository;

    @Autowired
    public AccidentServiceImpl(AccidentRepository repository, RoadConditionRepository roadConditionRepository, WeatherConditionRepository weatherConditionRepository) {
        this.repository = repository;
        this.roadConditionRepository = roadConditionRepository;
        this.weatherConditionRepository = weatherConditionRepository;
    }

    @Override
    public Map<String, Integer> getAccidentCountGroupByRoadCondition() {
        Map<String, Integer> result = new HashMap<>();
        List<RoadConditions> roadConditionsList = roadConditionRepository.findAll();

        for (RoadConditions roadCondition : roadConditionsList ) {
            result.put(roadCondition.getLabel(),
                repository.countByRoadSurfaceConditions(roadCondition));
        }
        return result;
    }

    @Override
    public Map<String, Integer> getAccidentCountGroupByWeatherConditionAndYear(String year) {
        Map<String, Integer> result = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = dateFormat.parse(year + "-01-01");
            Date date2 = dateFormat.parse(year + "-12-31");
            List<WeatherConditions> weatherConditionsList = weatherConditionRepository.findAll();

            for (WeatherConditions weatherCondition : weatherConditionsList ) {
                result.put(weatherCondition.getLabel(),
                        repository.countByWeatherConditionsAndDateBetween(weatherCondition, date1, date2));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}
