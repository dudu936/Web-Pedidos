package com.educandoweb.course.services.validate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.Product;
import com.educandoweb.course.repositories.CategoryRepository;
import com.educandoweb.course.services.CategoryService;

@Service
public class ProductValidate {
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryService service;
	
	public final Boolean categoryIsValid(Product product) {
		List<Long> listId = new ArrayList<>();
		product.forEach(category -> listId.add(category.getId()));
		Boolean allCategoriesExist = listId.stream().allMatch(id -> categoryRepository.existsById(id));
		return !listId.isEmpty() && allCategoriesExist;
	}
	
	public final Product addValidCategories(Product product) {
		Product entity = new Product.Builder().id(product.getId()).name(product.getName())
				.description(product.getDescription()).price(product.getPrice()).imgUrl(product.getImgUrl()).Build();
		product.forEach(category -> entity.addCategory(service.findById(category.getId())));
		return entity;
	}
	
}
