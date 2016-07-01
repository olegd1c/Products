package ua.in.devapp.products;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.in.devapp.products.api.Link;
import ua.in.devapp.products.api.MyRetrofit;
import ua.in.devapp.products.models.OrdersContainer;
import ua.in.devapp.products.view.CartListAdapter;

public class CartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Gson gson;
    private Link intf;
    private Call<ResponseBody> call;
    private ListView lvData;
    private ListView lvDataCartTotal;
    //private SimpleCursorAdapter scAdapter;
    private Repository repository;
    //private TextView textViewTotal;
    private Button btnSentOrder;
    View footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ua.in.devapp.products.error.RoboErrorReporter.bindReporter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);

        repository = Repository.getInstance();

        lvData = (ListView) findViewById(R.id.lvDataCart);
        lvDataCartTotal = (ListView) findViewById(R.id.lvDataCart);
        //textViewTotal = (TextView) findViewById(R.id.textViewTotal);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_cart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        //Button btnCart = (Button) findViewById(R.id.btnCart);


        gson = MyRetrofit.getGson();
        //myPicasso = new MyPicasso(this);
        try {
            intf = MyRetrofit.getLink(this);
        } catch (CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
        getCart(1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.main) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getCart(int addFooter) {
        if (addFooter == 1) {
            footer = createFooter("Total sum: " + String.valueOf(repository.getTotalSum()));
            lvData.addFooterView(footer);
        }

        lvData.setAdapter(new CartListAdapter(CartActivity.this, repository.getListOrderDetails(),1));
        //textViewTotal.setText("Total sum: " + String.valueOf(repository.getTotalSum()));

        //lvData.addFooterView();

        // определяем массив типа String
        final String[] cartTotal = new String[] {
                "Total sum: " + String.valueOf(repository.getTotalSum())
        };

        // используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, cartTotal);

        //lvDataCartTotal.setAdapter(adapter);

        //lvDataCartTotal.addView(textViewTotal);
//        if(intf == null) return;
//
//        call = intf.getCart();
//        //Executing Call
//        call.enqueue(getCallbackEnqueue());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCart:
                addToCart(v);
                break;
            case R.id.btnSentOrder:
                sentOrder();
                break;

            default:
                break;

        }
    }

    private void sentOrder() {
        if(intf == null) return;

        //call = intf.sentOrder(gson.toJson(repository.getCart()));

        //call = intf.postWithJson(repository.getCart());
        call = intf.sentOrder(Repository.getCart());
        //call = intf.sentOrder(gson.toJson(repository.getCart()));

//        Map<String, String> param = new HashMap<String, String>();
//        StringReader reader = new StringReader(gson.toJson(repository.getCart()));
//        JsonReader jsonReader = new JsonReader(reader);
//        jsonReader.setLenient(true);

        //JsonElement my_json=jsonParser.parse(jsonReader);
        //gson.newJsonReader(repository.getCart()).setLenient(true);
//        String toJson = gson.toJson(repository.getCart());
//        toJson = "{\"orderDetails\":[{\"count\":1,\"id\":1,\"price\":200,\"sum\":200}],\"totalSum\":200}";
//        param.put("param", toJson);

//        Map<String, Cart> param = new HashMap<String, Cart>();
//        param.put("pp1", repository.getCart());

        //call = intf.postObjectStr(gson.toJson(repository.getCart()));

        //call = intf.postObjectJson(gson.toJson(repository.getCart()));
        //call = intf.postObjectJson(param);

        //Executing Call
        //call.enqueue(getCallbackEnqueue());
        call.enqueue(getCallbackEnqueueSentOrder());
    }

    @NonNull
    private Callback<ResponseBody> getCallbackEnqueueSentOrder() {
        return new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        String respBody = response.body().string();
                        OrdersContainer resp = gson.fromJson(respBody,OrdersContainer.class);//асинхронный вызов
                        if (resp.getSuccess() == 1 ) {
                            Repository.clearOrderDetails();
                            getCart(0);
                            //CartActivity.this.recreate();
                        }

                        //{"success":1,"message":"OK"}
                        //String resp = response.body().toString();
                        //
                        //int respCode = response.code();
                        //String resp1="";
//                        JsonContainer jsonContainer = gson.fromJson(response.body().toString(), JsonContainer.class);// из Json в Java
//
//                        //matrixCursor.addRow(new Object[]{1, "тест", 50, 30});
//                        //for (Map.Entry e: map.entrySet()){
//                        if (jsonContainer.getSuccess() == 1) {
//                            lvData.setAdapter(new CustomListAdapter(MainActivity.this, jsonContainer.getProducts()));
                        //}
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                //else translated.setText("null");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @NonNull
    private Callback<ResponseBody> getCallbackEnqueue() {
        return new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    //Map<String,String> map = gson.fromJson(response.body().toString(),Map.class);//асинхронный вызов
                    try {
                        String resp = response.body().toString();
//                        JsonContainer jsonContainer = gson.fromJson(response.body().toString(), JsonContainer.class);// из Json в Java
//
//                        //matrixCursor.addRow(new Object[]{1, "тест", 50, 30});
//                        //for (Map.Entry e: map.entrySet()){
//                        if (jsonContainer.getSuccess() == 1) {
//                            lvData.setAdapter(new CustomListAdapter(MainActivity.this, jsonContainer.getProducts()));
                        //}
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                //else translated.setText("null");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void addToCart(View v) {
        repository.addOrderDetails((String) ((AppCompatButton) v).getText());
    }

    // создание Footer
    View createFooter(String text) {
        View v = getLayoutInflater().inflate(R.layout.footer_cart, null);
        ((TextView)v.findViewById(R.id.tvText)).setText(text);
        v.findViewById(R.id.btnSentOrder).setOnClickListener(this);

        return v;
    }
}
