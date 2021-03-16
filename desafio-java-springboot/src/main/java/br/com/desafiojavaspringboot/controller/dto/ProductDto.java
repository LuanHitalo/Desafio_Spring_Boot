package br.com.desafiojavaspringboot.controller.dto;

import br.com.desafiojavaspringboot.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDto {


    private String id;
    private String name;
    private String description;
    private double price;

    public ProductDto(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public static List<ProductDto> convert(List<Product> products){
        return products.stream().map(ProductDto::new).collect(Collectors.toList());
    }
}
