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

public class OrdersListAdapter extends BaseAdapter {
    private List<Order> listData;
    private LayoutInflater layoutInflater;
    private MyPicasso myPicasso;
    Context context;
    Repository repository;

    public OrdersListAdapter(Context context, List<Order> listData) {
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
            convertView = layoutInflater.inflate(R.layout.layout_orders_row, null);
            holder = new ViewHolder();
            //holder.textViewId = (TextView) convertView.findViewById(R.id.textViewId);
            holder.textViewId = (TextView) convertView.findViewById(R.id.textViewId);
            holder.textViewNumber = (TextView) convertView.findViewById(R.id.textViewNumber);
            holder.textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
            //holder.textViewUrlImg = (TextView) convertView.findViewById(R.id.textViewUrlImg);
            holder.textViewSum = (TextView) convertView.findViewById(R.id.textViewSum);
            holder.textViewStatus = (TextView) convertView.findViewById(R.id.textViewStatus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewId.setText(Integer.toString(listData.get(position).getId()));
        holder.textViewNumber.setText(listData.get(position).getNumber());
        holder.textViewDate.setText((CharSequence) listData.get(position).getDate());
        holder.textViewSum.setText(Integer.toString(listData.get(position).getSum()));
        holder.textViewStatus.setText(listData.get(position).getStatus());

        return convertView;
    }

    static class ViewHolder {
        TextView textViewId;
        TextView textViewNumber;
        TextView textViewDate;
        TextView textViewSum;
        TextView textViewStatus;
    }
}