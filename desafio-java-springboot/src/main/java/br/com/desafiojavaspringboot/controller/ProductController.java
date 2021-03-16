package br.com.desafiojavaspringboot.controller;

import br.com.desafiojavaspringboot.controller.form.ProductForm;
import br.com.desafiojavaspringboot.controller.dto.ProductDto;

import br.com.desafiojavaspringboot.controller.form.UserFIlter;
import br.com.desafiojavaspringboot.model.Product;
import br.com.desafiojavaspringboot.repository.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Api(value="DESAFIO PRODUTOS API REST")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    @Transactional
    @ApiOperation(value="Retorna o produto criado")
    public ResponseEntity<ProductDto> postProduct(@RequestBody @Valid ProductForm productForm, UriComponentsBuilder uriComponentsBuilder){
        Product product = productForm.convert();
        productRepository.save(product);

        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(new ProductDto(product));
    }

    @PutMapping("/{id}")
    @Transactional
    @ApiOperation(value="Retorna o produto atualizado")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @RequestBody @Valid ProductForm productForm){
        Product product = productForm.update(id,productRepository);

        return ResponseEntity.ok(new ProductDto(product));

    }

    @GetMapping
    @ApiOperation(value="Retorna a lista de produtos")
    public List<ProductDto> getProducts(){
        List<Product> product = productRepository.findAll();
        return ProductDto.convert(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value="Retorna o produto pelo ID")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return ResponseEntity.ok(new ProductDto(product.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    @ApiOperation(value="Retorna uma lista de produtos filtrados")
    public List<ProductDto> getProductFilter(String q,String min_price,String max_price ){
        UserFIlter userFIlter= new UserFIlter(q,min_price,max_price);
        List<Product> products = productRepository.findAll(userFIlter.toSpec());

        return  ProductDto.convert(products);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ApiOperation(value="Deleta o produto pelo ID")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }



}
