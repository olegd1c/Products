package ua.in.devapp.products.models;

import java.util.List;

/**
 * Created by o.dikhtyaruk on 17.05.2016.
 */

public class OrderDetailsContainer {
    private int success;
    private String messageError;

    public List<OrderDetails> getOrdersDetails() {
        return ordersDetails;
    }

    public void setOrdersDetails(List<OrderDetails> ordersDetails) {
        this.ordersDetails = ordersDetails;
    }

    private List<OrderDetails> ordersDetails;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return messageError;
    }

    public void setMessage(String messageError) {
        this.messageError = messageError;
    }

}