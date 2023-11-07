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
import Data.Objects.CreditSale;

public class AccountsReceivableAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater layoutInflater;
    TextView tvId_AccountsReceivableAdapter, tvSaleId_AccountsReceivableAdapter,
            tvBusinessName_AccountsReceivableAdapter, tvAmount_AccountsReceivableAdapter,
            tvCreationDate_AccountsReceivableAdapter;
    List<CreditSale> list;

    public AccountsReceivableAdapter()
    {

    }

    public AccountsReceivableAdapter(Context context, List<CreditSale> list)
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
        ViewGroup viewGroup = (ViewGroup) this.layoutInflater.inflate(R.layout.custom_accounts_receivable_adapter, null);

        tvId_AccountsReceivableAdapter = (TextView) viewGroup.findViewById(R.id.tvId_AccountsReceivableAdapter);
        tvSaleId_AccountsReceivableAdapter = (TextView) viewGroup.findViewById(R.id.tvSaleId_AccountsReceivableAdapter);
        tvBusinessName_AccountsReceivableAdapter = (TextView) viewGroup.findViewById(R.id.tvBusinessName_AccountsReceivableAdapter);
        tvAmount_AccountsReceivableAdapter = (TextView) viewGroup.findViewById(R.id.tvAmount_AccountsReceivableAdapter);
        tvCreationDate_AccountsReceivableAdapter = (TextView) viewGroup.findViewById(R.id.tvCreationDate_AccountsReceivableAdapter);

        tvId_AccountsReceivableAdapter.setText(String.format("ID: %s", list.get(position).getId()));
        tvSaleId_AccountsReceivableAdapter.setText(String.format("N° Pedido: %s", list.get(position).getSale().getId()));
        tvBusinessName_AccountsReceivableAdapter.setText(String.format("Razón social: %s", list.get(position).getSale().getClient().getPerson().getBusinessName()));
        tvAmount_AccountsReceivableAdapter.setText(String.format("Saldo: S/.%s", MyMath.toDecimal(list.get(position).getAmount(), 2)));
        tvCreationDate_AccountsReceivableAdapter.setText(String.format("Fecha de emisión: %s",
                MyDateTime.format(list.get(position).getDate(), MyDateTime.TYPE_DATE)));

        return viewGroup;
    }
}
