package com.apap.tutorial7.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.client.RestTemplate;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.rest.DealerDetail;
import com.apap.tutorial7.rest.Setting;
import com.apap.tutorial7.service.CarService;
import com.apap.tutorial7.service.DealerService;

/**
 * DealerController
 */
@RestController
@RequestMapping("/dealer")
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@PostMapping(value = "/add")
	private DealerModel addDealerSubmit(@RequestBody DealerModel dealer) {
		return dealerService.addDealer(dealer);
	}
	
	@GetMapping(value = "/{dealerId}")
	private DealerModel viewDealer(@PathVariable ("dealerId") long dealerId, Model model) {
		return dealerService.getDealerDetailById(dealerId).get();
	}
	
	@DeleteMapping(value = "/delete")
	private String deleteDealer(@RequestParam("dealerId") long id, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(id).get();
		dealerService.deleteDealer(dealer);
		return "Success";
	}
	
	@PutMapping(value = "/{id}")
	private String updateDealerSubmit(
			@PathVariable (value = "id") long id,
			@RequestParam("alamat") String alamat,
			@RequestParam("telp") String telp) {
		DealerModel dealer = (DealerModel) dealerService.getDealerDetailById(id).get();
		if(dealer.equals(null)) {
			return "Couldn't find your dealer";
		}
		dealer.setAlamat(alamat);
		dealer.setNoTelp(telp);
		dealerService.updateDealer(id, dealer);
		return "update success";
	}
	
	@GetMapping()
	private List<DealerModel> viewAllDealer(Model model){
		return dealerService.viewAllDealer();
	}
	
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}
	
	@GetMapping(value = "/status/{dealerId}")
	private String getStatus(@PathVariable ("dealerId") long dealerId) throws Exception {
		String path = Setting.dealerUrl + "/dealer?id=" + dealerId;
		return restTemplate.getForEntity(path, String.class).getBody();
	}
	
	@GetMapping(value = "/full/{dealerId}")
	private DealerDetail postStatus(@PathVariable ("dealerId") long dealerId) throws Exception {
		String path = Setting.dealerUrl + "/dealer";
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		DealerDetail detail = restTemplate.postForObject(path,  dealer, DealerDetail.class);
		return detail;
	}
	
//	@Autowired
//	private CarService carService;
//	
//	@RequestMapping("/")
//	private String home(Model model) {
//		model.addAttribute("title", "Home");
//		return "home";
//	}
//	
//	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
//	private String add(Model model) {
//		model.addAttribute("dealer", new DealerModel());
//		model.addAttribute("title", "Add Dealer");
//		return "addDealer";
//	}
//	
//	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
//	private String addDealerSubmit(@ModelAttribute DealerModel dealer) {
//		dealerService.addDealer(dealer);
//		return "add";
//	}
//
//	public CarService getCarService() {
//		return carService;
//	}
//
//	public void setCarService(CarService carService) {
//		this.carService = carService;
//	}
//	
//	// Menampilkan sebuah Dealer berdasarkan Id Dealer
////	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
////	private String viewDealer(@RequestParam(value = "dealerId") Long dealerId, Model model) {
////		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
////		List<CarModel> carList = dealer.getListCar();
////		carList.sort(Comparator.comparingLong(CarModel::getPrice));
////		String urladdcar = "/car/add/"+dealerId;
////		model.addAttribute("name", dealer.getId());
////		model.addAttribute("alamat", dealer.getAlamat());
////		model.addAttribute("telepon", dealer.getNoTelp());
////		model.addAttribute("carList", carList);
////		model.addAttribute("urladdcar", urladdcar);
////		return "view-dealer";
////	}
//	
//	@RequestMapping(value="/dealer/view", method=RequestMethod.GET)
//	private String view(@RequestParam(value="dealerId") Long dealerId, Model model) {
//		DealerModel archiveDealer = dealerService.getDealerDetailById(dealerId).get();
//		/**
//		 * Untuk mendapatkan list car terurut berdasarkan harga dengan Query
//		 * Bisa jadi berbeda dengan cara anda
//		 */
//		List<CarModel> archiveListCar = carService.getListCardOrderByPriveAsc(dealerId);
//		archiveDealer.setListCar(archiveListCar);
//		
//		model.addAttribute("dealer", archiveDealer);
//		return "view-dealer";
//	}
//	
//	@RequestMapping(value = "/dealer/viewall", method = RequestMethod.GET)
//	private String viewDealer(Model model) {
//		List<DealerModel> allDealer = dealerService.findAll();
//	
//		model.addAttribute("dealerlist", allDealer);
//		model.addAttribute("title", "All Dealer");
//		
//		return "viewall-dealer";
//	}
//	
//	@RequestMapping(value = "/dealer/delete/{idDealer}", method = RequestMethod.GET)
//	private String deleteCar(@PathVariable(value = "idDealer") long idDealer, Model model) {
//		dealerService.deleteDealer(idDealer);
//		model.addAttribute("title", "Delete Dealer");
//		return "delete";
//	}
//
//	@RequestMapping(value = "/dealer/update/{id}", method = RequestMethod.GET)
//	private String updateDealer(@PathVariable(value = "id") Long id, Model model) {
//		DealerModel dealer =  dealerService.getDealerDetailById(id).get();
//		model.addAttribute("dealer", dealer);
//		model.addAttribute("Iddealer", "ID Dealer:"+id);
//		model.addAttribute("title", "Update Dealer");
//		return "updateDealer";
//	}
//	
//	@RequestMapping(value = "/dealer/update/{id}", method = RequestMethod.POST)
//	private String updateDealerSubmit(@PathVariable(value = "id") Long id, @ModelAttribute Optional<DealerModel> dealer, Model model) {
//		dealerService.updateDealer(id, dealer.get());
//		model.addAttribute("title", "Update");
//		return "update";
//	}
}

