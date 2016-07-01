package ua.in.devapp.products;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.in.devapp.products.api.Link;
import ua.in.devapp.products.api.MyRetrofit;
import ua.in.devapp.products.models.Order;
import ua.in.devapp.products.models.OrderDetailsContainer;
import ua.in.devapp.products.view.CartListAdapter;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    private Gson gson;
    private Retrofit retrofit;
    private Link intf;
    private Call<Object> call;
    private Call<ResponseBody> callRB;
    private ListView lvData;
    private SimpleCursorAdapter scAdapter;
    private ImageView imageViewCart;
    private Repository repository;
    private View header;
    private View footer;
    private int currentBlok;
    Integer idOrder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ua.in.devapp.products.error.RoboErrorReporter.bindReporter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        repository = Repository.getInstance();
        lvData = (ListView) findViewById(R.id.lvData);
        idOrder = getIntent().getIntExtra("idOrder",0);

        //btnCart.setOnClickListener(this);
        gson = MyRetrofit.getGson();
        //myPicasso = new MyPicasso(this);
        try {
            intf = MyRetrofit.getLink(this);
        } catch (KeyManagementException | CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException e) {
            e.printStackTrace();
        }
        getOrder();
    }

    // создание Footer
    View createHeader() {
        //int countFooter = lvData.getFooterViewsCount();
        //int visible = 1;
        if (header == null) {
            header = getLayoutInflater().inflate(R.layout.header_order, null);
        }

//        ((Button)footer.findViewById(R.id.btnSentOrder)).setOnClickListener(this);
        StringBuilder sb = new StringBuilder();
        if (idOrder > 0) {
            Order order = repository.getOrder(idOrder);

            sb.append("Number: ").append(order.getNumber()).append('\n')
                    .append("Date: ").append(order.getDate()).append('\n')
                    .append("Sum: ").append(order.getSum()).append('\n')
                    .append("Adress: ").append(order.getAdress()).append('\n')
                    .append("Comment: ").append(order.getComment()).append('\n')
                    .append('\n').append("Name Price Qty SUM")
            ;
        }else{
            sb.append("Order not found");
        }

        ((TextView)header.findViewById(R.id.tvText)).setText(sb.toString());
/*
        if (visible ==0){
            header.setVisibility(View.INVISIBLE);
        }
        else{

            header.setVisibility(View.VISIBLE);
        }
        */
        header.setVisibility(View.VISIBLE);
        lvData.addHeaderView(header);
        //lvData.removeFooterView(footer);
        //lvData.removeAllViews();
        /*
        if (visible > 0) {
            lvData.addHeaderView(header);
        }else{
            lvData.removeFooterView(header);
        }
        */
        return header;
    }

    private void getOrder() {
        createHeader();



        if(intf == null) return;
        Map<String, String> map = new HashMap<>();
        map.put("idOrder", Integer.toString(repository.getOrder(idOrder).getId()));
        //call = intf.getOrders(map);
        call = intf.getOrderDetails(map);
        //Executing Call
        call.enqueue(getCallbackEnqueueOrder());
    }

    @NonNull
    private Callback<Object> getCallbackEnqueueOrder() {
        return new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body() != null) {
                    //Map<String,String> map = gson.fromJson(response.body().toString(),Map.class);//асинхронный вызов
                    //lvData.setAdapter(null);

                    try {
                        OrderDetailsContainer jsonContainer = gson.fromJson(response.body().toString(), OrderDetailsContainer.class);// из Json в Java

                        //matrixCursor.addRow(new Object[]{1, "тест", 50, 30});
                        //for (Map.Entry e: map.entrySet()){
                        if (jsonContainer.getSuccess() == 1) {

                            //header = createHeader("Total sum: " + String.valueOf(repository.getTotalSum()));
//                            //lvData.removeAllViews();
//                            lvData.removeHeaderView(header);
//                            lvData.addHeaderView(header);

                            //repository.setOrders(jsonContainer.getOrderDetailses());
                            if(jsonContainer.getOrdersDetails()!=null) {

                                //lvData.setAdapter(new CartListAdapter(OrderActivity.this, repository.getListOrderDetails()));
                                lvData.setAdapter(new CartListAdapter(OrderActivity.this, jsonContainer.getOrdersDetails(),0));
                                //lvData.setAdapter(new OrderDetailsListAdapter(OrderActivity.this, jsonContainer.getOrdersDetails()));
                            }
                            else{
                                Toast.makeText(OrderActivity.this, "Order not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                //else translated.setText("null");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(OrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onClick(View v) {

    }
}
