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
import Data.Objects.SaleDetail;

public class SaleDetailsAdapter<T> extends BaseAdapter {

    Context ctx;
    LayoutInflater layoutInflater;
    TextView tvDescription_SaleDetails, tvUnitPrice_SaleDetails, tvLimitPrice_SaleDetails,
            tvQuantity_SaleDetails, tvTotal_SaleDetails;
    List<T> list;

    public SaleDetailsAdapter()
    {

    }

    public SaleDetailsAdapter(Context context, List<T> list)
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
        ViewGroup viewGroup = (ViewGroup) this.layoutInflater.inflate(R.layout.custom_sale_details_adapter, null);

        tvDescription_SaleDetails = (TextView) viewGroup.findViewById(R.id.tvDescription_SaleDetails);
        tvUnitPrice_SaleDetails = (TextView) viewGroup.findViewById(R.id.tvUnitPrice_SaleDetails);
        tvLimitPrice_SaleDetails = (TextView) viewGroup.findViewById(R.id.tvLimitPrice_SaleDetails);
        tvQuantity_SaleDetails = (TextView) viewGroup.findViewById(R.id.tvQuantity_SaleDetails);
        tvTotal_SaleDetails = (TextView) viewGroup.findViewById(R.id.tvTotal_SaleDetails);

        if (list != null && list.size() > 0)
        {
            if (list.get(position) instanceof SaleDetail)
            {
                tvDescription_SaleDetails.setText(String.format("[%s] %s",
                        ((SaleDetail)list.get(position)).getProduct().getId(),
                        ((SaleDetail)list.get(position)).getProduct().getDescription().trim()));
                tvUnitPrice_SaleDetails.setText(String.format("Prec. Unit: S/ %s",
                        MyMath.toDecimal(((SaleDetail)list.get(position)).getProduct().getPrice(), 2)));
                tvQuantity_SaleDetails.setText(String.format("Cantidad: %s", MyMath.toRoundNumber(((SaleDetail)list.get(position)).getQuantity())));
                tvTotal_SaleDetails.setText(String.format("Importe: S/ %s", MyMath.toDecimal(((SaleDetail)list.get(position)).getTotal(), 2)));
            }
        }

        return viewGroup;
    }
}
