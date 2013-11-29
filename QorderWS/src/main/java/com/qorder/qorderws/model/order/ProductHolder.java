package com.qorder.qorderws.model.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.qorder.qorderws.model.product.IProduct;
import com.qorder.qorderws.model.product.Product;

@Entity
@Table(name="PRODUCT_ORDER")
public class ProductHolder implements IProductHolder{
	
	@Id
	@GeneratedValue
	@Column(name="PRODUCT_ORDER_ID")
	private long id;
	
	@Column(name="COMMENTS")
	private String comments;
	
	@ManyToOne(targetEntity=Product.class)
	@JoinColumn(name="PRODUCT_ID")
	private IProduct product;
	
	public ProductHolder(IProduct product) {
		this.product = product;
	}

	@Override
	public IProduct getProduct() {
		return product;
	}

	@Override
	public void setProduct(IProduct product) {
		this.product = product;
		
	}

	@Override
	public String getComments() {
		return comments;
	}

	@Override
	public void setComments(String comments) {
		this.comments = comments;
		
	}
	
}