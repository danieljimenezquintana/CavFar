package com.example.cavfar;

public class FuenteModelos {

    String title;
    String description;
    int imagen;

    public FuenteModelos(String title, String description, int imagen) {
        this.title = title;
        this.description = description;
        this.imagen = imagen;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImagen() {
        return imagen;
    }
}
