package br.com.desafiojavaspringboot.controller.form;

import br.com.desafiojavaspringboot.model.Product;
import br.com.desafiojavaspringboot.repository.ProductRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductForm {

    @NotNull @NotEmpty
    private String name;
    @NotNull @NotEmpty
    private String description;
    @Positive
    private double price;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product convert(){
        return new Product(name,description,price);

    }

    public Product update(String id,ProductRepository productRepository){
        Product product = productRepository.getOne(id);
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        return product;
    }

}
