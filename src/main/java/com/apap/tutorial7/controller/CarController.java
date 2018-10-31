package com.apap.tutorial7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.service.CarService;
import com.apap.tutorial7.service.DealerService;

/**
 * CarController
 */
@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		
		List<CarModel> listNewCar = new ArrayList<CarModel>();
		listNewCar.add(new CarModel());
		
		dealer.setListCar(listNewCar);
		
		model.addAttribute("title", "Add Car");
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add", method = RequestMethod.POST)
	private String addCarSubmit(@ModelAttribute CarModel car, Model model) {
		carService.addCar(car);
		model.addAttribute("title", "Add Car");
		return "add";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST, params= {"save"})
	private String addCarSubmit (@ModelAttribute DealerModel dealer, Model model) {
		DealerModel dealerSkrg = dealerService.getDealerDetailById(dealer.getId()).get();
		for(CarModel car : dealer.getListCar()) {
			car.setDealer(dealerSkrg);
			carService.addCar(car);
		}
		model.addAttribute("title", "Add Success");
		return "add";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", params= {"addRow"}, method = RequestMethod.POST)
	private String addRow (@ModelAttribute DealerModel dealer, Model model) {
		dealer.getListCar().add(new CarModel());
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params={"removeRow"})
	private String removeRow (@ModelAttribute DealerModel dealer, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
		dealer.getListCar().remove(rowId.intValue());
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	
//	@RequestMapping(value = "/car/delete/{idCar}", method = RequestMethod.GET)
//	private String deleteCar(@PathVariable(value = "idCar") long idCar, Model model) {
//		carService.deleteCar(idCar);
//		return "delete";
//	}

	
	@RequestMapping(value="/car/delete", method=RequestMethod.POST)
	private String delete(@ModelAttribute DealerModel dealer, Model model) {
		for (CarModel car : dealer.getListCar()) {
			carService.deleteCar(car.getId());
		}
		model.addAttribute("title", "Delete Car");
		return "delete";
	}
	
	
	@RequestMapping(value = "/car/update/{id}", method = RequestMethod.GET)
	private String updateCar(@PathVariable(value = "id") long id, Model model) {
		CarModel car =  carService.getCar(id);
		model.addAttribute("car", car);
		model.addAttribute("title", "Update Car");
		return "updateCar";
	}
	
	@RequestMapping(value = "/car/update/{id}", method = RequestMethod.POST)
	private String updateCarSubmit(@PathVariable(value = "id") long id, @ModelAttribute CarModel car, Model model) {
		carService.updateCar(id, car);
		model.addAttribute("title", "Update Car");
		return "update";
	}
}
