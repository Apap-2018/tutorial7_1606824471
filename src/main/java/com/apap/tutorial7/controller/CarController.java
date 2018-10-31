package com.apap.tutorial7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.service.CarService;
import com.apap.tutorial7.service.DealerService;

/**
 * CarController
 */

@RestController
@RequestMapping("/car")
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@PutMapping(value = "/{id}")
	private String updateCarSubmit(
			@PathVariable (value = "id") long id,
			@RequestParam("brand") String brand,
			@RequestParam("type") String type,
			@RequestParam("price") long price,
			@RequestParam("amount") Integer amount,
			@RequestParam("dealerId") long dealerId) {
		CarModel car = carService.getCarDetailById(id).get();
		if(car.equals(null)) {
			return "Couldn't fid your car";
		}
		car.setBrand(brand);
		car.setType(type);
		car.setPrice(price);
		car.setAmount(amount);

		carService.addCar(car);
		
		return "car update success";
	}
	
	@PostMapping(value = "/add")
	private CarModel addCarSubmit(@RequestBody CarModel car) {
		return carService.addCar(car);
	}
	
	@GetMapping(value = "/{carId}")
	private CarModel viewCar(@PathVariable ("carId") long carId) {
		return carService.getCar(carId);
	}
	
	@GetMapping()
	private List<CarModel> viewAllCar(Model model){
		return carService.getAllCar();
	}
	
	@DeleteMapping(value = "/delete")
    private String deleteCar(@RequestParam("carId") long carId, Model Model) {
    	CarModel car = carService.getCarDetailById(carId).get();
    	carService.deleteCar(car);
    	return "Car has been deleted";
    }
	
	
}