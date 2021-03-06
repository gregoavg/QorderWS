package com.qorder.qorderws.controller;

import com.qorder.qorderws.dto.product.DetailedProductDTO;
import com.qorder.qorderws.dto.product.ProductDTO;
import com.qorder.qorderws.exception.ResourceNotFoundException;
import com.qorder.qorderws.model.EEntity;
import com.qorder.qorderws.service.ICategoryService;
import com.qorder.qorderws.utils.providers.EDomainLinkProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

	private final ICategoryService categoryService;

	@Autowired
	public CategoryController(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@RequestMapping(value = "/{categoryID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Collection<ProductDTO>> getCategory(@PathVariable Long categoryID) throws ResourceNotFoundException {
		LOGGER.info("Request for category with id equals "+ categoryID);
		Collection<ProductDTO> categoryProducts = categoryService.fetchCategoryByID(categoryID);
		return new ResponseEntity<>(categoryProducts, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{categoryID}/products", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> storeProducts(@PathVariable Long categoryID, @RequestBody DetailedProductDTO productDTO) throws ResourceNotFoundException {
		LOGGER.info("Request to store products save with category id equals :" + categoryID);
		
		long productID = categoryService.addProduct(categoryID, productDTO);
		
		URI location = URI.create(EDomainLinkProvider.INSTANCE.getLocationFor(EEntity.PRODUCT) + productID);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		return new ResponseEntity<>(headers,HttpStatus.CREATED);
	}
	
	@ExceptionHandler( IOException.class )
	ResponseEntity<String> sendIOException(Exception ex) {
		LOGGER.warn("Exception was thrown, with cause " + ex.getCause() + "\nMessage: " + ex.getLocalizedMessage(), ex );
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
