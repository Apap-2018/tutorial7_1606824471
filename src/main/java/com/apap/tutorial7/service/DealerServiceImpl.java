package com.apap.tutorial7.service;

import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.repository.DealerDb;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * DealerServiceImpl
 */
@Service
@Transactional
public class DealerServiceImpl implements DealerService {
	@Autowired
	private DealerDb dealerDb;
	
	@Override
	public Optional<DealerModel> getDealerDetailById(Long id){
		return dealerDb.findById(id);
	}
	
	@Override
	public DealerModel addDealer(DealerModel dealer) {
		return dealerDb.save(dealer);
	}
	
	@Override
	public List<DealerModel> viewAllDealer() {
		return dealerDb.findAll();
	}
	
	@Override
	public void deleteDealer(DealerModel dealer) {
		dealerDb.delete(dealer);
	}
	
	@Override
	public void updateDealer(Long idDealer, DealerModel dealer) {
		DealerModel updatedDealer = dealerDb.getOne(idDealer);
		updatedDealer.setAlamat(dealer.getAlamat());
		updatedDealer.setNoTelp(dealer.getNoTelp());
		dealerDb.save(updatedDealer);
	}
}
