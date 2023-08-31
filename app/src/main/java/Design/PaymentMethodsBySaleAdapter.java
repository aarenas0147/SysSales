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
import Data.Objects.SalePaymentMethod;

public class PaymentMethodsBySaleAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater layoutInflater;
    TextView tvPaymentMethod_PaymentMethodsBySaleAdapter, tvAmount_PaymentMethodsBySaleAdapter,
            tvPercentage_PaymentMethodsBySaleAdapter, tvTotal_PaymentMethodsBySaleAdapter;
    List<SalePaymentMethod> list;

    public PaymentMethodsBySaleAdapter()
    {

    }

    public PaymentMethodsBySaleAdapter(Context context, List<SalePaymentMethod> list)
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
        ViewGroup viewGroup = (ViewGroup) this.layoutInflater.inflate(R.layout.custom_sale_payment_methods_adapter, null);

        tvPaymentMethod_PaymentMethodsBySaleAdapter = (TextView) viewGroup.findViewById(R.id.tvPaymentMethod_PaymentMethodsBySaleAdapter);
        tvAmount_PaymentMethodsBySaleAdapter = (TextView) viewGroup.findViewById(R.id.tvAmount_PaymentMethodsBySaleAdapter);
        tvPercentage_PaymentMethodsBySaleAdapter = (TextView) viewGroup.findViewById(R.id.tvPercentage_PaymentMethodsBySaleAdapter);
        tvTotal_PaymentMethodsBySaleAdapter = (TextView) viewGroup.findViewById(R.id.tvTotal_PaymentMethodsBySaleAdapter);

        tvPaymentMethod_PaymentMethodsBySaleAdapter.setText(String.format("%s", list.get(position).getPaymentMethod().getDescription().toUpperCase()));
        tvAmount_PaymentMethodsBySaleAdapter.setText(String.format("Monto: S/.%s", MyMath.toDecimal(list.get(position).getAmount(), 2)));
        tvPercentage_PaymentMethodsBySaleAdapter.setText(String.format("Comisión: %s%%", MyMath.toRoundNumber(list.get(position).getCommissionPercentage())));
        tvTotal_PaymentMethodsBySaleAdapter.setText(String.format("Importe: S/.%s", MyMath.toDecimal(list.get(position).getTotal(), 2)));

        return viewGroup;
    }
}
