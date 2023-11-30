package Design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aarenas.syssales.R;

import java.util.List;

import Data.MyDateTime;
import Data.MyMath;
import Data.Objects.CreditSaleQuote;

public class CreditSaleQuotesAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater layoutInflater;
    TextView tvQuoteNumber_CreditSaleQuotesAdapter, tvAmount_CreditSaleQuotesAdapter, tvPayment_CreditSaleQuotesAdapter,
            tvBalance_CreditSaleQuotesAdapter, tvCreationDate_CreditSaleQuotesAdapter;
    List<CreditSaleQuote> list;

    public CreditSaleQuotesAdapter()
    {

    }

    public CreditSaleQuotesAdapter(Context context, List<CreditSaleQuote> list)
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
        ViewGroup viewGroup = (ViewGroup) this.layoutInflater.inflate(R.layout.custom_credit_sale_quotes_adapter, null);

        tvQuoteNumber_CreditSaleQuotesAdapter = (TextView) viewGroup.findViewById(R.id.tvQuoteNumber_CreditSaleQuotesAdapter);
        tvAmount_CreditSaleQuotesAdapter = (TextView) viewGroup.findViewById(R.id.tvAmount_CreditSaleQuotesAdapter);
        tvPayment_CreditSaleQuotesAdapter = (TextView) viewGroup.findViewById(R.id.tvPayment_CreditSaleQuotesAdapter);
        tvBalance_CreditSaleQuotesAdapter = (TextView) viewGroup.findViewById(R.id.tvBalance_CreditSaleQuotesAdapter);
        tvCreationDate_CreditSaleQuotesAdapter = (TextView) viewGroup.findViewById(R.id.tvCreationDate_CreditSaleQuotesAdapter);

        tvQuoteNumber_CreditSaleQuotesAdapter.setText(String.format("N° de cuota: %s", list.get(position).getQuoteNumber()));
        tvAmount_CreditSaleQuotesAdapter.setText(String.format("Monto: S/ %s", MyMath.toDecimal(list.get(position).getAmount(), 2)));
        tvPayment_CreditSaleQuotesAdapter.setText(String.format("A cuenta: S/ %s", MyMath.toDecimal(list.get(position).getPayment(), 2)));
        tvBalance_CreditSaleQuotesAdapter.setText(String.format("Saldo: S/ %s", MyMath.toDecimal(list.get(position).getBalance(), 2)));
        tvCreationDate_CreditSaleQuotesAdapter.setText(String.format("Fecha de amortización: %s",
                MyDateTime.format(list.get(position).getDate(), MyDateTime.TYPE_DATE)));

        return viewGroup;
    }
}
