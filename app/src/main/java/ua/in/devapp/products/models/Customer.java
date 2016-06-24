package ua.in.devapp.products.models;

/**
 * Created by o.dikhtyaruk on 09.06.2016.
 */
public class Customer {
    private String id;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getOaut() {
        return oaut;
    }

    public void setOaut(String oaut) {
        this.oaut = oaut;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String login;
    private String pwd;
    private String oaut;
    private String key;
    private String adress;
    private String comment;
}