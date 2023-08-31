package Design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aarenas.syssales.R;

import java.util.List;

import Data.MyMath;
import Data.Objects.Sale;

public class SalesAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater layoutInflater;
    TextView tvSaleId_SalesAdapter, tvVoucherDescription_SalesAdapter, tvCustomerName_SalesAdapter,
            tvCustomerId_SalesAdapter, tvTotal_SalesAdapter;
    List<Sale> list;

    public SalesAdapter()
    {

    }

    public SalesAdapter(Context context, List<Sale> list)
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
        ViewGroup viewGroup = (ViewGroup) this.layoutInflater.inflate(R.layout.custom_sales_adapter, null);
        tvSaleId_SalesAdapter = (TextView) viewGroup.findViewById(R.id.tvSaleId_SalesAdapter);
        tvCustomerName_SalesAdapter = (TextView) viewGroup.findViewById(R.id.tvCustomerName_SalesAdapter);
        tvVoucherDescription_SalesAdapter = (TextView) viewGroup.findViewById(R.id.tvVoucherDescription_SalesAdapter);
        tvCustomerId_SalesAdapter = (TextView) viewGroup.findViewById(R.id.tvCustomerId_SalesAdapter);
        tvTotal_SalesAdapter = (TextView) viewGroup.findViewById(R.id.tvTotal_SalesAdapter);

        tvSaleId_SalesAdapter.setText(String.format("N° Pedido: %s", list.get(position).getId()));
        tvCustomerName_SalesAdapter.setText(String.format("Cliente: %s", list.get(position).getClient().getBusinessName()));
        tvVoucherDescription_SalesAdapter.setText(String.format("Tipo de documento: %s", list.get(position).getVoucherType().getDescription()));
        tvCustomerId_SalesAdapter.setText(String.format("Cod. cliente: %s", list.get(position).getClient().getId()));
        tvTotal_SalesAdapter.setText(String.format("Total: %s", MyMath.toDecimal(list.get(position).getTotal(), 2)));
        return viewGroup;
    }
}
