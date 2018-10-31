package com.apap.tutorial7.service;

import java.util.List;

import com.apap.tutorial7.model.CarModel;

/*
 * CarService
 */
public interface CarService {
	void addCar(CarModel car);
	void deleteCar(Long idCar);
	void updateCar(Long idCar, CarModel car);
	public CarModel getCar(Long id);
	List<CarModel> getListCardOrderByPriveAsc(Long dealerId);
	
}
