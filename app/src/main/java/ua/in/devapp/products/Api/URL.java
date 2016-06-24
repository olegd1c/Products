package ua.in.devapp.products.Api;

/**
 * Created by o.dikhtyaruk on 02.06.2016.
 */
public enum URL {

    URL_IMG("http://shop.devapp.in.ua/images/"),
    URL_HTTPS("https://api.devapp.in.ua/"),
    URL_HTTP("http://api.devapp.in.ua/");

    URL(String name) {
        this.name = name;
    }
    private String name;

    public String getValue()
    {
        return name;
    }

}
