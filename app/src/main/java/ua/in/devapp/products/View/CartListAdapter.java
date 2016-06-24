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
import ua.in.devapp.products.models.OrderDetails;
import ua.in.devapp.products.models.Product;

public class CartListAdapter extends BaseAdapter {
    private List<OrderDetails> listData;
    private LayoutInflater layoutInflater;
    private MyPicasso myPicasso;
    Context context;
    Repository repository;
    Integer type;

    public CartListAdapter(Context context, List<OrderDetails> listData,Integer type) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        myPicasso = new MyPicasso(context);
        this.context = context;
        this.type = type;
        if (repository ==null) repository = Repository.getInstance();
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
            convertView = layoutInflater.inflate(R.layout.layout_cart_row, null);
            holder = new ViewHolder();
            //holder.textViewId = (TextView) convertView.findViewById(R.id.textViewId);
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            holder.textViewCount = (TextView) convertView.findViewById(R.id.textViewCount);
            holder.textViewPrice = (TextView) convertView.findViewById(R.id.textViewPrice);
            holder.textViewSum = (TextView) convertView.findViewById(R.id.textViewSum);
            //holder.textViewUrlImg = (TextView) convertView.findViewById(R.id.textViewUrlImg);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.btnCart = (Button) convertView.findViewById(R.id.btnCart);
//            holder.btnCart.setOnClickListener((View.OnClickListener) context);

            if (type == 0){
                holder.btnCart.setVisibility(View.INVISIBLE);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderDetails ordDet = listData.get(position);
        String idInt = Integer.toString(ordDet.getId());

        //holder.btnCart.setText(idInt);
        //holder.textViewId.setText(idInt);

        Product prod = repository.getProduct(ordDet.getProd_id());
        holder.textViewTitle.setText(prod.getTitle());
        holder.textViewCount.setText(Integer.toString(ordDet.getQty()));
        holder.textViewPrice.setText(Integer.toString(ordDet.getPrice()));
        holder.textViewSum.setText(Integer.toString(ordDet.getSum()));
        //holder.textViewUrlImg.setText(prod.getImage());

        myPicasso.loadImg(prod.getImage(), holder.imageView);

        return convertView;
    }

    static class ViewHolder {
        //TextView textViewId;
        TextView textViewTitle;
        TextView textViewCount;
        TextView textViewPrice;
        TextView textViewSum;
        //TextView textViewUrlImg;
        ImageView imageView;
        Button btnCart;
    }
}