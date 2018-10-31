package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.DealerModel;

/*
 * DealerService
 */
public interface DealerService {
	Optional<DealerModel> getDealerDetailById(Long id);
	
	DealerModel addDealer(DealerModel dealer);
	public List<DealerModel> viewAllDealer();
//	void deleteDealer(Long idDealer);
	public void deleteDealer(DealerModel dealer);
	void updateDealer(Long idDealer, DealerModel dealer);
}
