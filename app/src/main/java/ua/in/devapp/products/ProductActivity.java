package ua.in.devapp.products;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import ua.in.devapp.products.api.Link;
import ua.in.devapp.products.api.MyPicasso;
import ua.in.devapp.products.models.Product;

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
        ua.in.devapp.products.error.RoboErrorReporter.bindReporter(this);
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
        assert btnCart != null;
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
