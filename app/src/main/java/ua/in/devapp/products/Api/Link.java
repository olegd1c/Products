package ua.in.devapp.products.Api;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import ua.in.devapp.products.models.Cart;

/**
 * Created by o.dikhtyaruk on 16.05.2016.
 */
public interface Link {
    @FormUrlEncoded
    //@Query
    @POST("php/add_order.php")
    Call<Object> addOrder1(@FieldMap Map<String, String> parameters);

    //@QueryMap Map<String, String> parameters
    //http://devapp.in.ua/script/add_gps.php?name=oleg12&lon=35.568994&lat=56.565623

    @GET("php/get_orders.php")
    Call<Object> getOrders(@QueryMap Map<String, Integer> parameters);

    @GET("php/get_orders.php")
    Call<Object> getOrders();

    @GET("php/get_products.php")
    Call<Object> getProducts(@QueryMap Map<String, String> parameters);

    @GET("php/get_order_details.php")
    Call<Object> getOrderDetails(@QueryMap Map<String, String> parameters);

    @GET("php/get_products.php")
    Call<Object> getProducts();

    @Multipart
    @Headers("Content-Type: multipart/form-data;")
    @POST("upload1/newDoc.txt")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);

    @POST("php/add_order.php")
    Call<ResponseBody> sentOrder(@Body Cart cart);

    //@POST("php/add_order.php")
    @POST("php/post_json.php")
    Call<Object> sentOrder1(@Body Cart cart);

    /*
    @Headers( "Content-Type: application/json" )
    @POST("/json/foo/bar/")
    Response fubar( @Body TypedString sJsonBody ) ;
*/
    @POST("php/post_json.php")
    Call<ResponseBody> postWithJson(
            @Body Cart cart
    );


    @FormUrlEncoded
    //@POST("php/post_json.php")
    @POST("php/add_order.php")
    Call<Object> postObjectJson(@FieldMap Map<String, String> parameters);

    //@POST("php/post_json.php")
    @POST("php/post_json.php")
    Call<Object> postObjectStr(@Body String param);

}
