package ua.in.devapp.products.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ua.in.devapp.products.Api.MyPicasso;
import ua.in.devapp.products.R;
import ua.in.devapp.products.Repository;
import ua.in.devapp.products.models.Order;
import ua.in.devapp.products.models.OrderDetails;

public class OrderDetailsListAdapter extends BaseAdapter {
    private List<OrderDetails> listData;
    private LayoutInflater layoutInflater;
    private MyPicasso myPicasso;
    Context context;
    Repository repository;

    public OrderDetailsListAdapter(Context context, List<OrderDetails> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        myPicasso = new MyPicasso(context);
        this.context = context;
        if (repository ==null) repository = Repository.getInstance();
//        for (Order item: listData) {
//            repository.addMapProduct(item);
//        }
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
            convertView = layoutInflater.inflate(R.layout.layout_order_details_row, null);
            holder = new ViewHolder();
            //holder.textViewId = (TextView) convertView.findViewById(R.id.textViewId);
            holder.textViewNameProd = (TextView) convertView.findViewById(R.id.textViewNameProd);
            holder.textViewPrice = (TextView) convertView.findViewById(R.id.textViewPrice);
            holder.textViewQty = (TextView) convertView.findViewById(R.id.textViewQty);
            //holder.textViewUrlImg = (TextView) convertView.findViewById(R.id.textViewUrlImg);
            holder.textViewSum = (TextView) convertView.findViewById(R.id.textViewSum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Integer prod_id = listData.get(position).getProd_id();
        String nameProd = Integer.toString(prod_id);
        holder.textViewNameProd.setText(nameProd);
        holder.textViewQty.setText(Integer.toString(listData.get(position).getQty()));
        holder.textViewPrice.setText(Integer.toString(listData.get(position).getPrice()));
        holder.textViewSum.setText(Integer.toString(listData.get(position).getSum()));

        return convertView;
    }

    static class ViewHolder {
        TextView textViewNameProd,textViewPrice,textViewQty,textViewSum;
    }
}