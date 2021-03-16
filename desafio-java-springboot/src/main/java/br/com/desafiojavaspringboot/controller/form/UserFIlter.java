package br.com.desafiojavaspringboot.controller.form;

import br.com.desafiojavaspringboot.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class UserFIlter {
    private String d;
    private String minValue;
    private String maxValue;

    public UserFIlter(String d, String minValue, String maxValue) {
        this.d = d;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }


    public Specification<Product> toSpec(){
        return (root, query, builder) ->{
            List<Predicate> predicates = new ArrayList<>();
            try {
                if (StringUtils.hasText(d)) {
                    Path<String> name = root.<String>get("name");
                    Path<String> description = root.<String>get("description");
                    Predicate predicateName = builder.equal(name, d);
                    Predicate predicateDescription = builder.equal(description, d);
                    Predicate predicate = builder.or(predicateName, predicateDescription);
                    predicates.add(predicate);
                }
                if (StringUtils.hasText(minValue)) {
                    Path<Double> price = root.<Double>get("price");
                    Predicate predicateMinValue = builder.greaterThanOrEqualTo(price, Double.parseDouble(minValue));
                    predicates.add(predicateMinValue);

                }
                if (StringUtils.hasText(maxValue)) {
                    Path<Double> price = root.<Double>get("price");
                    Predicate predicateMinValue = builder.lessThanOrEqualTo(price, Double.parseDouble(maxValue));
                    predicates.add(predicateMinValue);
                }
            }catch (Exception e){
                return builder.and(predicates.toArray(new Predicate[0]));

            }

            return builder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
