package com.qorder.model;

public interface IOrderProduct {

	public int getQuantity();
	public void setQuantity(int quantity);
	public IProduct getProduct();
	public void setProduct(IProduct product);
	public String getComments();
	public void setComments(String comments);
	//change 
}
