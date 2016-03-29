package com.example.amresh.grabonrent;

/**
 * Created by amresh on 29-03-2016.
 */
public class Products {
    String text;
    String imageUrl;

    public Products(String text, String imageUrl) {
        this.imageUrl = imageUrl;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
