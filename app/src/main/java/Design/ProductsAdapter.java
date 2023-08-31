package Design;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aarenas.syssales.R;

import java.util.List;

import Data.MyMath;
import Data.Objects.Product;

public class ProductsAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater layoutInflater;
    LinearLayout lytItem_ProductsAdapter;
    TextView tvDescription_ProductsAdapter, tvStock_ProductsAdapter,
            tvPrice_ProductsAdapter, tvLimitPrice_ProductsAdapter;
    List<Product> list;

    public ProductsAdapter()
    {

    }

    public ProductsAdapter(Context context, List<Product> list)
    {
        this.ctx = context;
        this.layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup viewGroup = (ViewGroup) this.layoutInflater.inflate(R.layout.custom_products_adapter, null);

        lytItem_ProductsAdapter = (LinearLayout) viewGroup.findViewById(R.id.lytItem_ProductsAdapter);
        tvDescription_ProductsAdapter = (TextView) viewGroup.findViewById(R.id.tvDescription_ProductsAdapter);
        tvStock_ProductsAdapter = (TextView) viewGroup.findViewById(R.id.tvStock_ProductsAdapter);
        tvPrice_ProductsAdapter = (TextView) viewGroup.findViewById(R.id.tvPrice_ProductsAdapter);
        tvLimitPrice_ProductsAdapter = (TextView) viewGroup.findViewById(R.id.tvLimitPrice_ProductsAdapter);

        if (list.get(position).getStock() > 0F) {
            if (list.get(position).getStock() <= list.get(position).getMinimumStock()) {
                tvStock_ProductsAdapter.setBackgroundColor(Color.YELLOW);
            }
        } else {
            tvStock_ProductsAdapter.setBackgroundColor(Color.RED);
        }

        tvDescription_ProductsAdapter.setText(String.format("[%s] %s",
                list.get(position).getId(), list.get(position).getDescription()));
        tvStock_ProductsAdapter.setText(String.format("Stock: %s",
                MyMath.toRoundNumber(list.get(position).getStock())));
        tvPrice_ProductsAdapter.setText(String.format("Prec. Venta: %s",
                MyMath.toDecimal(list.get(position).getPrice(), 2)));
        tvLimitPrice_ProductsAdapter.setText(String.format("Prec. Límite: %s",
                MyMath.toDecimal(list.get(position).getLimitPrice(), 2)));

        return viewGroup;
    }
}
