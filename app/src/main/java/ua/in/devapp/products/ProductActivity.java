package ua.in.devapp.products;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
import ua.in.devapp.products.Api.Link;
import ua.in.devapp.products.Api.MyPicasso;
import ua.in.devapp.products.Api.MyRetrofit;
import ua.in.devapp.products.View.CartListAdapter;
import ua.in.devapp.products.models.Order;
import ua.in.devapp.products.models.OrderDetailsContainer;
import ua.in.devapp.products.models.Product;

/**
 * Created by o.dikhtyaruk on 16.06.2016.
 */
public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

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
    private MyPicasso myPicasso;

    TextView textViewTitle;
    TextView textViewDicsription;
    TextView textViewPrice;
    TextView textViewUrlImg;
    ImageView imageView;
    Button btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ua.in.devapp.products.Error.RoboErrorReporter.bindReporter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        repository = Repository.getInstance();
        myPicasso = new MyPicasso(this);

        idOrder = getIntent().getIntExtra("idProduct",0);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewDicsription = (TextView) findViewById(R.id.textViewDicsription);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        //holder.textViewUrlImg = (TextView) convertView.findViewById(R.id.textViewUrlImg);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnCart = (Button) findViewById(R.id.btnCart);
        btnCart.setOnClickListener(this);

        getProduct();
    }

    private void getProduct() {
        Product prod = repository.getProduct(idOrder);
        btnCart.setText(Integer.toString(idOrder));
        //holder.textViewId.setText(Integer.toString(listData.get(position).getId()));
        textViewTitle.setText(prod.getTitle());
        textViewDicsription.setText(prod.getDiscription());
        textViewPrice.setText(Integer.toString(prod.getPrice()));
        //holder.textViewUrlImg.setText(listData.get(position).getImage());

        myPicasso.loadImg(prod.getImage(), imageView);
    }

    @Override
    public void onClick(View v) {

    }
}
