package ua.in.devapp.products.models;

import java.util.List;

/**
 * Created by o.dikhtyaruk on 17.05.2016.
 */

public class OrdersContainer {
    private int success;
    private String messageError;
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }



}