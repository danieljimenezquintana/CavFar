package com.example.cavfar;

import java.io.Serializable;

public class FuenteModelos implements Serializable {

    String title;
    String description;
    String brandName;

    public FuenteModelos(String title, String description,String brandName) {
        this.title = title;
        this.description = description;
        this.brandName = brandName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBrandName() {
        return brandName;
    }
}
