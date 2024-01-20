package Design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aarenas.syssales.R;

import java.util.List;
import Data.Objects.Customer;

public class CustomersAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater layoutInflater;
    TextView tvBusinessName_CustomersAdapter, tvVendor_CustomersAdapter, tvDocumentNumber_CustomersAdapter;
    List<Customer> list;
    boolean optionVendors;

    public CustomersAdapter()
    {

    }

    public CustomersAdapter(Context context, List<Customer> list)
    {
        this.ctx = context;
        this.layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    public CustomersAdapter(Context context, List<Customer> list, boolean optionVendors)
    {
        this.ctx = context;
        this.layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.optionVendors = optionVendors;
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
        ViewGroup viewGroup = (ViewGroup) this.layoutInflater.inflate(R.layout.custom_customers_adapter, null);

        tvBusinessName_CustomersAdapter = (TextView) viewGroup.findViewById(R.id.tvBusinessName_CustomersAdapter);
        tvVendor_CustomersAdapter = (TextView) viewGroup.findViewById(R.id.tvVendor_CustomersAdapter);
        tvDocumentNumber_CustomersAdapter = (TextView) viewGroup.findViewById(R.id.tvDocumentNumber_CustomersAdapter);

        tvVendor_CustomersAdapter.setVisibility(this.optionVendors ? View.VISIBLE : View.GONE);

        tvBusinessName_CustomersAdapter.setText(String.format("[%s] %s",
                list.get(position).getId(), list.get(position).getPerson().getBusinessName()));
        if (list.get(position).getEmployee() != null)
        {
            tvVendor_CustomersAdapter.setText(String.format("Vendedor: %s", list.get(position).getEmployee().getPerson().getNames()));
        }
        tvDocumentNumber_CustomersAdapter.setText(String.format("N° documento: %s", list.get(position).getPerson().getDocumentNumber()));

        return viewGroup;
    }
}
