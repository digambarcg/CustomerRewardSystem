package com.rewards.points.model;

import java.time.LocalDate;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Transaction {
	@Id
	private String cust_id;
	private double trans_amt;
	private LocalDate trans_date;
	public Transaction() {}
	public Transaction(String cust_id, double trans_amt, LocalDate trans_date) {
		super();
		this.cust_id = cust_id;
		this.trans_amt = trans_amt;
		this.trans_date = trans_date;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public double getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(double trans_amt) {
		this.trans_amt = trans_amt;
	}
	public LocalDate getTrans_date() {
		return trans_date;
	}
	public void setTrans_date(LocalDate trans_date) {
		this.trans_date = trans_date;
	}
	@Override
	public String toString() {
		return "Transaction [cust_id=" + cust_id + ", trans_amt=" + trans_amt + ", trans_date=" + trans_date + "]";
	}
	
}
