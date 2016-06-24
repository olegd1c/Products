
package ua.in.devapp.products.Api;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ua.in.devapp.products.R;

public class MyPicasso {

    String url = URL.URL_IMG.getValue();
    Picasso mPicasso;
    public MyPicasso(Context context) {
        mPicasso = Picasso.with(context);
    }

    //Picasso.with(this).load("http://devapp.in.ua/img/fon.jpg").into(imageView);

    // из ресурсов
    //        Picasso.with(context).load(R.drawable.landing_screen).into(imageView1);
    // из внешнего накопителя
    //        Picasso.with(context).load(new File(...)).into(imageView2);

    public void loadImg(String img, ImageView imageView) {
        mPicasso.load(url.concat(img))
                .placeholder(R.drawable.warning)
                .error(R.drawable.img_error)
                .fit().centerCrop()
                //.resize(200, 200)
                .into(imageView);
    }
}
