package com.qorder.qorderws.dto;

import java.util.ArrayList;
import java.util.List;


public class MenuDTO {

	private List<CategoryDTO> categoryInfoList = new ArrayList<>();


	public List<CategoryDTO> getCategoryInfoList() {
		return categoryInfoList;
	}

	public void setCategoryInfoList(List<CategoryDTO> categoryInfoList) {
		this.categoryInfoList = categoryInfoList;
	}
	
	public void addCategoryInfo(CategoryDTO categoryInfo) {
		categoryInfoList.add(categoryInfo);
	}
}
