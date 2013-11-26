package com.qorder.qorderws.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.qorder.qorderws.model.order.ProductOrder;
import com.qorder.qorderws.model.productType.ProductType;

@Entity
@Table(name="PRODUCT")
public class Product {
	
	@Id
    @GeneratedValue
    @Column(name="PRODUCT_ID")
	private long id;
	@Column(name="NAME")
	private String name;
	@Column(name="PRICE")
	private BigDecimal price;
	@ManyToOne
	@JoinColumn(name="PRODUCT_TYPE_ID")
	private ProductType ProductType;
	@OneToMany(targetEntity=ProductOrder.class, mappedBy="product")
    private List<ProductOrder> productOrders = new ArrayList<ProductOrder>();
	
	//private List<String> attributes;
	
	public Product(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	

	public BigDecimal getPrice() {
		return price;
	}
	

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
/*
	public List<String> getAttributes() {
		return attributes;
	}
	

	public void addAttribute(String attribute) {
		this.attributes.add(attribute);
	}
	
	*/

}
