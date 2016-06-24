package ua.in.devapp.products.models;

import java.util.ArrayList;

/**
 * Created by o.dikhtyaruk on 03.06.2016.
 */
public class Cart {
    public void clearOrderDetails() {
        this.orderDetails = new ArrayList<OrderDetails> ();
        this.totalSum = 0;
    }
    //private Map<Integer,OrderDetails> orderDetails;
    private ArrayList<OrderDetails> orderDetails;
    private Integer totalSum = 0;
    private Integer idCustomer = 1;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setTotalSum(Integer totalSum) {
        this.totalSum = totalSum;
    }

    private String adress = "ff";

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Integer getTotalSum() {
        return totalSum;
    }

    public ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public Cart(){
        //orderDetails = new HashMap<Integer,OrderDetails>();
        orderDetails = new ArrayList<OrderDetails>();
    }

    public void addOrderDetails(String idProduct) {
        Integer idInt = Integer.valueOf((String) idProduct);
        OrderDetails orderDetail = null;

        for (OrderDetails item: orderDetails) {
            if (item.getId() == idInt)   {
                item.addCount(1);
                this.totalSum = this.totalSum + item.getPrice();
                orderDetail = item;
                break;
            }
        }

        if (orderDetail == null) {
            orderDetail = new OrderDetails(idInt);
            orderDetail.addCount(1);
            this.totalSum = this.totalSum + orderDetail.getSum();
            orderDetails.add(orderDetail);
        }
        //OrderDetails orderDetail = orderDetails.get(idInt);
//        if (orderDetail == null) {orderDetail = new OrderDetails(idInt);
//        orderDetail.addCount(1);
//        this.totalSum = this.totalSum + orderDetail.getSum();
//        orderDetails.put(idInt,orderDetail);
    }
}