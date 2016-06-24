package ua.in.devapp.products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.in.devapp.products.models.Cart;
import ua.in.devapp.products.models.Order;
import ua.in.devapp.products.models.OrderDetails;
import ua.in.devapp.products.models.Product;

/**
 * Created by o.dikhtyaruk on 06.06.2016.
 */
public class Repository {
    private static Repository rep = new Repository();
    //private static Context context;

    public List<Order> getOrders() {
        return orders;
    }

    public Order getOrder(int id) {
//        Order order = null;
//        if (orders.size() > 0) {
//            for (Order item: orders) {
//                if(item.getId() == id) {
//                    order = item;
//                    break;
//                }
//            }
//        }
        return orders.get(id);
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    private List<Order> orders;

    public static Cart getCart() {
        return cart;
    }

    public static void clearOrderDetails() {
        Repository.cart.clearOrderDetails();
    }

    private static Cart cart = new Cart();
    private static Map<Integer,Product> products = new HashMap<Integer,Product>();

    public static Repository getInstance() {
        return rep;
    }

    private Repository() {
    }

    public Map<Integer, Product> getMapProduct() {
        return products;
    }

    public void addMapProduct(Product mapProduct) {
        products.put(mapProduct.getId(),mapProduct);
    }

    public Product getProduct(Integer id) {
        return products.get(id);
    }

    public void addOrderDetails(String idProduct) {
        cart.addOrderDetails(idProduct);
    }
    
    public List<OrderDetails> getListOrderDetails() {
        //Map<Integer,OrderDetails> map = cart.getOrderDetails();
        //List<OrderDetails> list = new ArrayList<OrderDetails>(map.values());
        ArrayList<OrderDetails> list = cart.getOrderDetails();
        return list;
    }

    public Integer getTotalSum() {
        return cart.getTotalSum();
    }
}