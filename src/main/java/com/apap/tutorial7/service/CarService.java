package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.CarModel;

/*
 * CarService
 */
public interface CarService {
	CarModel addCar(CarModel car);
	void deleteCar(CarModel car);
	void updateCar(Long idCar, CarModel car);
	public CarModel getCar(Long id);
	List<CarModel> getListCardOrderByPriveAsc(Long dealerId);
	List<CarModel> getAllCar();
    Optional<CarModel> getCarDetailById(Long id);
	
}
