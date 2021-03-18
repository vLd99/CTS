package ro.ase.java.models;

import java.io.Serializable;

public class Coffe implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7860127851903770338L;
	private String beverageName;
	private String CoffeType;
	private double CoffePrice;
	
	public Coffe() {
		
	}
	
	public Coffe(String b2,String c2,double p2) {
		this.beverageName=b2;
		this.CoffeType=c2;
		this.CoffePrice=p2;
	}
	
	public void setBeverageName(String b2) {
		this.beverageName=b2;
	}
	public void setCoffeType(String c2) {
		this.CoffeType=c2;
	}
	public void setCoffePrice(double p2) {
		this.CoffePrice=p2;
	}
	public String getBeverageName() {
		return this.beverageName;
	}
	public String getCoffeType() {
		return this.CoffeType;
	}
	public double getCoffePrice() {
		return this.CoffePrice;
	}

}
