/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 *
 * This file is part of Dayan techology Co.ltd property.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.model.course;

/**
 *
 * @author xsg
 */
public class Payment {

    public Payment(Double price, String description) {
        this.price = price;
        this.description = description;
    }

    public Payment(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Payment() {
    }

    private String name;
    private Double price;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
