package ua.in.devapp.products;

import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Handler;
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
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import ua.in.devapp.products.api.Link;
import ua.in.devapp.products.api.MyRetrofit;
import ua.in.devapp.products.models.Cart;
import ua.in.devapp.products.models.Customer;
import ua.in.devapp.products.models.OrdersContainer;
import ua.in.devapp.products.models.Product;
import ua.in.devapp.products.models.ProductsContainer;
import ua.in.devapp.products.view.CartListAdapter;
import ua.in.devapp.products.view.CustomListAdapter;
import ua.in.devapp.products.view.OrdersListAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final int REQUEST_CODE_CUSTOMER = 1;
    private Gson gson;
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
    private boolean backPressToExit;

    private NavigationView navigationView;
    private View headerLayout;
    TextView textUserName;
    TextView textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ua.in.devapp.products.error.RoboErrorReporter.bindReporter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       repository = Repository.getInstance();

        lvData = (ListView) findViewById(R.id.lvData);
        assert lvData != null;
        lvData.setOnItemClickListener(this);
        lvData.setOnItemLongClickListener(this);

        //imageViewCart = (ImageView) findViewById(R.id.imageViewCart);
        //imageViewCart.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setOnClickListener(this);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
        headerLayout = navigationView.getHeaderView(0);
        textUserName = (TextView) headerLayout.findViewById(R.id.textUserName);
        textEmail = (TextView) headerLayout.findViewById(R.id.textEmail);

        textUserName.setOnClickListener(this);
        textEmail.setOnClickListener(this);

        Button btnCart = (Button) findViewById(R.id.btnCart);
        //btnCart.setOnClickListener(this);

        gson = MyRetrofit.getGson();
        //myPicasso = new MyPicasso(this);
        try {
            intf = MyRetrofit.getLink(this);
        } catch (CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }

        //getProducts();
        getAdapter(R.id.main);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCustomerName();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (currentBlok != R.id.main) {
            getAdapter(R.id.main);
        }
        else {

            if (backPressToExit) {
                super.onBackPressed();
            }
            else{
                Toast.makeText(this, "Press again to close app", Toast.LENGTH_SHORT).show();
                this.backPressToExit = true;

                new Handler().postDelayed(new Runnable() {


                    @Override


                    public void run() {


                        backPressToExit = false;
                    }
                }, 2000);
            }

        }
    }

    private void setCustomerName() {
        Customer customer = Repository.getCustomer();
        //View navHeader = getLayoutInflater().inflate(R.layout.nav_header_main, null);

        String userName = "";
        String userEmail = "";
        if (customer != null && customer.getId() != null) {
            userName = customer.getName() + " " + customer.getS_name();
            userEmail = customer.getEmail();
        }
        //((TextView)navHeader.findViewById(R.id.textUserName)).setText(userName);
        //View headerLayout = mNavigationView.inflateHeaderView(R.layout.nav_header_main);

//        // Now you can update the views in your header as you want :
        String htmlTaggedString = "<u>" + userName + "</u>";
        Spanned textSpan = android.text.Html.fromHtml(htmlTaggedString);
        textUserName.setText(textSpan);
        textEmail.setText(userEmail);
    }

    private void getAdapter(int id) {
        if (id == R.id.main) {
            getProducts();
        }
        else if (id == R.id.history) {
            getOrders();
        }else if (id == R.id.cart) {
            getCart(1);
            //Intent intent = new Intent(this, CartActivity.class);
            //startActivity(intent);
        } else if (id == R.id.signin) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
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

        getAdapter(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getProducts() {
        currentBlok = R.id.main;
        createFooter("",0);
        createHeader(0);
        if(intf == null) return;

        call = intf.getProducts();
        //Executing Call
        call.enqueue(getCallbackEnqueueProducts());
    }

    @NonNull
    private Callback<Object> getCallbackEnqueueProducts() {
        return new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body() != null) {
                    //Map<String,String> map = gson.fromJson(response.body().toString(),Map.class);//асинхронный вызов

                    try {
                        ProductsContainer jsonContainer = gson.fromJson(response.body().toString(), ProductsContainer.class);// из Json в Java

                        //matrixCursor.addRow(new Object[]{1, "тест", 50, 30});
                        //for (Map.Entry e: map.entrySet()){
                        if (jsonContainer.getSuccess() == 1) {
                            lvData.setAdapter(new CustomListAdapter(MainActivity.this, jsonContainer.getProducts()));
                            lvData.setOnItemClickListener(MainActivity.this);
                            lvData.setOnItemLongClickListener(MainActivity.this);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                //else translated.setText("null");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void getOrders() {
        currentBlok = R.id.history;
        createFooter("",0);
        if(intf == null) return;
        Map<String, Integer> map = new HashMap<>();
        Customer customer = repository.getCustomer();
        Integer idCustomer = 0;
        if (customer != null) idCustomer = customer.getId();
        map.put("idCustomer", idCustomer);
        call = intf.getOrders(map);
        //call = intf.getOrders();
        //Executing Call
        call.enqueue(getCallbackEnqueueOrders());
    }

    @NonNull
    private Callback<Object> getCallbackEnqueueOrders() {
        return new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body() != null) {
                    //Map<String,String> map = gson.fromJson(response.body().toString(),Map.class);//асинхронный вызов
                    lvData.setAdapter(null);

                    try {
                        OrdersContainer jsonContainer = gson.fromJson(response.body().toString(), OrdersContainer.class);// из Json в Java

                        //matrixCursor.addRow(new Object[]{1, "тест", 50, 30});
                        //for (Map.Entry e: map.entrySet()){
                        if (jsonContainer.getSuccess() == 1) {
                            createHeader(1);
                            //header = createHeader("Total sum: " + String.valueOf(repository.getTotalSum()));
//                            //lvData.removeAllViews();
//                            lvData.removeHeaderView(header);
//                            lvData.addHeaderView(header);
                            repository.setOrders(jsonContainer.getOrders());
                            lvData.setAdapter(new OrdersListAdapter(MainActivity.this, repository.getOrders()));
                            lvData.setOnItemClickListener(MainActivity.this);
                            lvData.setOnItemLongClickListener(MainActivity.this);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                //else translated.setText("null");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void getCart(Integer i) {
        currentBlok = R.id.cart;
        createFooter("Total sum: " + String.valueOf(repository.getTotalSum()),1);
            createHeader(0);
            //footer = createFooter("Total sum: " + String.valueOf(repository.getTotalSum()));
//            lvData.removeFooterView(footer);
//            //lvData.removeAllViews();
//            lvData.addFooterView(footer);

        lvData.setAdapter(new CartListAdapter(MainActivity.this, repository.getListOrderDetails(),1));
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

    @NonNull
    private Callback<Object> getCallbackEnqueue() {
        return new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body() != null) {
                    //Map<String,String> map = gson.fromJson(response.body().toString(),Map.class);//асинхронный вызов
                    String[] columns = new String[]{"_id", "title", "discription", "price","imageUrl"};

                    MatrixCursor matrixCursor = new MatrixCursor(columns);
                    startManagingCursor(matrixCursor);
                    try {
                        ProductsContainer jsonContainer = gson.fromJson(response.body().toString(), ProductsContainer.class);// из Json в Java

                        //matrixCursor.addRow(new Object[]{1, "тест", 50, 30});
                        //for (Map.Entry e: map.entrySet()){
                        if (jsonContainer.getSuccess() == 1) {
                            for (Product e : jsonContainer.getProducts()) {
                                matrixCursor.addRow(new Object[]{e.getId(), e.getTitle(), e.getDiscription(), e.getPrice(),e.getImage()});
                            }
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    // формируем столбцы сопоставления
                    String[] from = new String[]{"_id", "title", "discription", "price","imageUrl"};
//                    int[] to = new int[]{R.id.textViewId, R.id.textViewTitle, R.id.textViewDicsription, R.id.textViewPrice, R.id.textViewUrlImg};
                    int[] to = new int[]{R.id.textViewTitle, R.id.textViewTitle, R.id.textViewDicsription, R.id.textViewPrice, R.id.textViewTitle};

                    // создааем адаптер и настраиваем список
                    scAdapter = new SimpleCursorAdapter(MainActivity.this, R.layout.layout_product_row, matrixCursor, from, to);
                    lvData.setAdapter(scAdapter);

                    //myPicasso.loadImg("fon.jpg", imageView);

                }
                //else translated.setText("null");

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
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
            case R.id.textEmail:
            case R.id.textUserName:
                editCustomer();
                break;
            default:
                break;

        }
    }

    private void editCustomer() {
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CUSTOMER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            setCustomerName();

            switch (requestCode) {
                case REQUEST_CODE_CUSTOMER:

                    break;
                default:
                    break;
            }

            // если вернулось не ОК
        } else {
            Toast.makeText(this, R.string.error_update_customer, Toast.LENGTH_SHORT).show();
        }
    }

    private void addToCart(View v) {
        repository.addOrderDetails((String) ((AppCompatButton) v).getText());
    }

    // создание Header
    private View createHeader(int visible) {
        //View v = getLayoutInflater().inflate(R.layout.layout_orders_row, null);
        int countHeader = lvData.getHeaderViewsCount();

        if (header == null) {
            header = getLayoutInflater().inflate(R.layout.layout_orders_row, null);
        }

        if (visible == 0){
                header.setVisibility(View.INVISIBLE);
        }
        else{
            ((TextView)header.findViewById(R.id.textViewId)).setText("Id");
            ((TextView)header.findViewById(R.id.textViewNumber)).setText("Number");
            ((TextView)header.findViewById(R.id.textViewDate)).setText("Date");
            ((TextView)header.findViewById(R.id.textViewSum)).setText("Sum");
            ((TextView)header.findViewById(R.id.textViewStatus)).setText("Status");

            header.setVisibility(View.VISIBLE);
        }
        //lvData.removeFooterView(footer);
        //lvData.removeAllViews();
        if (visible > 0) {
            lvData.addHeaderView(header);
        }else{
            lvData.removeHeaderView(header);
        }

        return header;
    }

    // создание Footer
    View createFooter(String text, int visible) {
        int countFooter = lvData.getFooterViewsCount();

        if (footer == null) {
            footer = getLayoutInflater().inflate(R.layout.footer_cart, null);
        }

        ((TextView)footer.findViewById(R.id.tvText)).setText(text);
        footer.findViewById(R.id.btnSentOrder).setOnClickListener(this);

        if (visible ==0){
            footer.setVisibility(View.INVISIBLE);
        }
        else{
            footer.setVisibility(View.VISIBLE);
        }
        //lvData.removeFooterView(footer);
        //lvData.removeAllViews();
        if (visible > 0) {
            lvData.addFooterView(footer);
        }else{
            lvData.removeFooterView(footer);
        }

        return footer;
    }

    private void sentOrder() {
        if(intf == null) return;

        //call = intf.sentOrder(gson.toJson(repository.getCart()));

        //call = intf.postWithJson(repository.getCart());
        Cart cart = Repository.getCart();
        if (cart.getOrderDetails().size() > 0) {
            callRB = intf.sentOrder(cart);
            callRB.enqueue(getCallbackEnqueueSentOrder());
        }
    }

    @NonNull
    public Callback<ResponseBody> getCallbackEnqueueSentOrder() {
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

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                //else translated.setText("null");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (currentBlok == R.id.history){
            Intent i=new Intent(this,OrderActivity.class);
            i.putExtra("idOrder", (int) id);
            startActivity(i);
        }
        else{
            Intent i=new Intent(this,ProductActivity.class);
            i.putExtra("idProduct", (int) id);
            startActivity(i);
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
