package ua.in.devapp.products.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ua.in.devapp.products.Api.MyPicasso;
import ua.in.devapp.products.R;
import ua.in.devapp.products.Repository;
import ua.in.devapp.products.models.Product;

public class CustomListAdapter extends BaseAdapter {
    private List<Product> listData;
    private LayoutInflater layoutInflater;
    private MyPicasso myPicasso;
    Context context;
    Repository repository;

    public CustomListAdapter(Context context, List<Product> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        myPicasso = new MyPicasso(context);
        this.context = context;
        if (repository ==null) repository = Repository.getInstance();
        for (Product item: listData) {
            repository.addMapProduct(item);
        }
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_product_row, null);
            holder = new ViewHolder();
            //holder.textViewId = (TextView) convertView.findViewById(R.id.textViewId);
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            holder.textViewDicsription = (TextView) convertView.findViewById(R.id.textViewDicsription);
            holder.textViewPrice = (TextView) convertView.findViewById(R.id.textViewPrice);
            //holder.textViewUrlImg = (TextView) convertView.findViewById(R.id.textViewUrlImg);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.btnCart = (Button) convertView.findViewById(R.id.btnCart);
            holder.btnCart.setOnClickListener((View.OnClickListener) context);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.btnCart.setText(Integer.toString(listData.get(position).getId()));
        //holder.textViewId.setText(Integer.toString(listData.get(position).getId()));
        holder.textViewTitle.setText(listData.get(position).getTitle());
        holder.textViewDicsription.setText(listData.get(position).getDiscription());
        holder.textViewPrice.setText(Integer.toString(listData.get(position).getPrice()));
        //holder.textViewUrlImg.setText(listData.get(position).getImage());

        myPicasso.loadImg(listData.get(position).getImage(), holder.imageView);

        return convertView;
    }

    static class ViewHolder {
        TextView textViewId;
        TextView textViewTitle;
        TextView textViewDicsription;
        TextView textViewPrice;
        TextView textViewUrlImg;
        ImageView imageView;
        Button btnCart;
    }
}