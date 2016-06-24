package ua.in.devapp.products.models;

import ua.in.devapp.products.Repository;

/**
 * Created by o.dikhtyaruk on 03.06.2016.
 */
public class OrderDetails {
    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getProd_id() {
        return prod_id;
    }

    public void setProd_id(Integer prod_id) {
        this.prod_id = prod_id;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    private Integer order_id = 0;
    private Integer prod_id = 0;
    private Integer id = 0;
    private Integer qty = 0;
    private Integer price = 0;
    private Integer lineNumber = 0;
    private Integer sum = 0;

    public OrderDetails(Integer idProduct) {
        this.prod_id = idProduct;
        Repository repository = Repository.getInstance();
        Product prod = repository.getProduct(idProduct);
        this.price = prod.getPrice();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
        setSum();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer count) {
        this.qty = count;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum() {
        this.sum = this.qty * this.price;
    }

    public void addCount(int i) {
        this.qty = qty + i;
        setSum();
    }
}
