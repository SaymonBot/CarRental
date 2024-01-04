package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private Double pricePerhour;
	private Double pricePerDay;
	
	private TaxService taxService;

	public RentalService(Double pricePerhour, Double pricePerDay, TaxService taxService) {
		super();
		this.pricePerhour = pricePerhour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();		
		double hours = minutes / 60.0;
		
		double basicPayment;
		if (hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerhour;
		}else {
			basicPayment = Math.ceil(hours / 24.0) * pricePerDay;
		}
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
		 
	}
	
}
