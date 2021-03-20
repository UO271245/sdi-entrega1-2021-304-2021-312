package com.uniovi.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Offer;
import com.uniovi.repositories.OffersRepository;

@Service
public class OfferService {

	@Autowired
	OffersRepository offersRepository;
	
	public List<Offer> getOffers(){
		List<Offer> offers = new ArrayList<Offer>();
		offersRepository.findAll().forEach(offers::add);
		return offers;
	}
	
	public Offer getOffer(Long id) {
		return offersRepository.findById(id).get();
	}
	
	public void addOffer(Offer offer) {
		offersRepository.save(offer);
	}
	
	public void deleteOffer(Long id) {
		offersRepository.deleteById(id);
	}
	
	public void setOfferAvailable(boolean av, Long id) {
		offersRepository.updateAvailable(av, id);
	}
}
