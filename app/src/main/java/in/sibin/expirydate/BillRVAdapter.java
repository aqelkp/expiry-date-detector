package in.sibin.expirydate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ahammad on 06/04/16.
 */
public class BillRVAdapter extends RecyclerView.Adapter<BillRVAdapter.ViewHolder> {

    private static final String LOG_TAG = "BillRVAdapter" ;
    ArrayList<Bill> items;
    Context context;

    public BillRVAdapter(Context context, ArrayList<Bill> items) {
        this.items = items;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Bill bill = items.get(position);
        holder.tvName.setText(bill.getName());
        holder.tvDesc.setText(bill.getDescription());

        Glide.with(context)
                .load(new File(bill.getImage())) // Uri of the picture
                .into(holder.ivPic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDesc;
        ImageView ivPic;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            ivPic = (ImageView) itemView.findViewById(R.id.ivPic);


        }
    }
}
