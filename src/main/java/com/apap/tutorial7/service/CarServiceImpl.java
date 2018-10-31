package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.repository.CarDb;

/**
 * CarServiceImpl
 */
@Service
@Transactional
public class CarServiceImpl implements CarService {
	@Autowired
	private CarDb carDb;
	
	@Override
	public CarModel addCar(CarModel car) {
		return carDb.save(car);
	}
	
	@Override
	public void deleteCar(CarModel car) {
		carDb.delete(car);
	}
	
	@Override
	public void updateCar(Long idCar, CarModel car) {
		CarModel updatedCar = carDb.getOne(idCar);
		updatedCar.setBrand(car.getBrand());
		updatedCar.setType(car.getType());
		updatedCar.setPrice(car.getPrice());
		updatedCar.setAmount(car.getAmount());
		carDb.save(updatedCar);
	}
	
	@Override
	public CarModel getCar(Long id) {
		return carDb.findById(id).get();
	}
	
	@Override
	public List<CarModel> getListCardOrderByPriveAsc(Long dealerId) {
		return carDb.findByDealerIdOrderByPriceAsc(dealerId);
	}
	
    @Override
    public List<CarModel> getAllCar() {
        return carDb.findAll();
    }
    
    @Override
    public Optional<CarModel> getCarDetailById(Long id) {
        return carDb.findById(id);
    }

}
