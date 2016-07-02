package ua.in.devapp.products;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import ua.in.devapp.products.models.Customer;
import ua.in.devapp.products.models.CustomerContainer;

public class CustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edLogin;
    private EditText edPassword;
    private EditText edAdress;
    private EditText edName;
    private EditText edEmail;
    private EditText edPhone;
    private EditText edSname;
    private Button btnSave;
    private Repository repository;
    private Customer customer;
    private Gson gson;
    private Link intf;
    private Call<ResponseBody> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        repository = Repository.getInstance();

        customer = repository.getCustomer();

        edLogin = (EditText) findViewById(R.id.editTextLogin);
        edPassword = (EditText) findViewById(R.id.editTextPassword);
        edAdress = (EditText) findViewById(R.id.editTextAdress);
        edName = (EditText) findViewById(R.id.editTextname);
        edEmail = (EditText) findViewById(R.id.editTextEmail);
        edPhone = (EditText) findViewById(R.id.editTextPhone);
        edSname = (EditText) findViewById(R.id.editTextSname);

        btnSave = (Button) findViewById(R.id.btnSaveCusromer);
        btnSave.setOnClickListener(this);
        edLogin.setText(customer.getLogin());
        edPassword.setText(customer.getPassword());
        edAdress.setText(customer.getAdress());
        edName.setText(customer.getName());
        edEmail.setText(customer.getEmail());
        edPhone.setText(customer.getPhone());
        edSname.setText(customer.getS_name());

        gson = MyRetrofit.getGson();
        //myPicasso = new MyPicasso(this);
        try {
            intf = MyRetrofit.getLink(this);
        } catch (CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }

//        ScrollView sv = new ScrollView(this);
//        LinearLayout ll = new LinearLayout(this);
//        ll.setOrientation(LinearLayout.VERTICAL);
//        sv.addView(ll);

        /*
        LinearLayout ll = (LinearLayout) findViewById(R.id.customer_form);
        TextView tv = new TextView(this);
        tv.setText("Name");
        ll.addView(tv);
        EditText et = new EditText(this);
        ll.addView(et);
        Button b = new Button(this);
        b.setText("Ok");
        ll.addView(b);

        Class c = Customer.class;
        Field[] publicFields = c.getFields();
        for (Field field : publicFields) {

            tv = new TextView(this);
            tv.setText(field.getName());
            ll.addView(tv);
            new EditText(this);
            ll.addView(et);

//            Class fieldType = field.getType();
//            System.out.println("Имя: " + field.getName());
//            System.out.println("Тип: " + fieldType.getName());
        }
        */
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaveCusromer:
                editCustomer();
                break;
            default:
                break;
        }
    }

    private void editCustomer() {
        customer.setLogin(edLogin.getText().toString());
        customer.setPassword(edPassword.getText().toString());
        customer.setAdress(edAdress.getText().toString());
        customer.setName(edName.getText().toString());
        customer.setEmail(edEmail.getText().toString());
        customer.setPhone(edPhone.getText().toString());
        customer.setS_name(edSname.getText().toString());

        call = intf.update_customer(customer);
        //Executing Call
        call.enqueue(getCallbackEnqueueCustomer());
    }

    @NonNull
    private Callback<ResponseBody> getCallbackEnqueueCustomer() {
        return new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        String respBody = response.body().string();
                        CustomerContainer resp = gson.fromJson(respBody, CustomerContainer.class);//асинхронный вызов
                        if (resp.getSuccess() == 1) {
                            Repository.setCustomer(resp.getCustomer());
                        } else {
                            Toast.makeText(CustomerActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(CustomerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();//e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CustomerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }
}
