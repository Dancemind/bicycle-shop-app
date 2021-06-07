package com.dancemind.springmvc.bicycleshop.entities;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;


import lombok.Data;

@Data
@Entity
@Table(name="Bike_Order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Date placedAt;

    @ManyToOne
    private User user;

    @NotEmpty(message="Delivery name is required")
    private String deliveryName;

    @NotEmpty(message="Street is required")
    private String deliveryStreet;

    @NotEmpty(message="City is required")
    private String deliveryCity;

    @NotEmpty(message="State is required")
    private String deliveryState;

    @NotEmpty(message="Zip code is required")
    private String deliveryZip;

    //@CreditCardNumber(message="Not a valid credit card number")
    @NotEmpty(message="Credit card number is required")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message="Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    @ManyToOne(targetEntity=Bicycle.class)
    private Bicycle bicycle;

    @PrePersist
    public void placedAt() {
        this.placedAt = new Date();
    }

    public void addBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }
}
