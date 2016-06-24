package ua.in.devapp.products.models;

import java.util.List;

/**
 * Created by o.dikhtyaruk on 17.05.2016.
 */

public class ProductsContainer {
    private int success;
    private String messageError;
    private List<Product> products;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setGpsis(List<Product> gpsis) {
        this.products = products;
    }

    public String getMessage() {
        return messageError;
    }

    public void setMessage(String messageError) {
        this.messageError = messageError;
    }

}