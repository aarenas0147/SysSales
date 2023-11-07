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
import Data.Objects.CollectionSheetDetail;

public class CollectionSheetAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater layoutInflater;
    TextView tvId_CollectionSheetAdapter, tvSaleId_CollectionSheetAdapter,
            tvBusinessName_CollectionSheetAdapter, tvBalance_CollectionSheetAdapter,
            tvAmortization_CollectionSheetAdapter, tvCondition_CollectionSheetAdapter;
    List<CollectionSheetDetail> list;

    public CollectionSheetAdapter()
    {

    }

    public CollectionSheetAdapter(Context context, List<CollectionSheetDetail> list)
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
        ViewGroup viewGroup = (ViewGroup) this.layoutInflater.inflate(R.layout.custom_collection_sheet_adapter, null);

        tvId_CollectionSheetAdapter = (TextView) viewGroup.findViewById(R.id.tvId_CollectionSheetAdapter);
        tvSaleId_CollectionSheetAdapter = (TextView) viewGroup.findViewById(R.id.tvSaleId_CollectionSheetAdapter);
        tvBusinessName_CollectionSheetAdapter = (TextView) viewGroup.findViewById(R.id.tvBusinessName_CollectionSheetAdapter);
        tvBalance_CollectionSheetAdapter = (TextView) viewGroup.findViewById(R.id.tvBalance_CollectionSheetAdapter);
        tvAmortization_CollectionSheetAdapter = (TextView) viewGroup.findViewById(R.id.tvAmortization_CollectionSheetAdapter);
        tvCondition_CollectionSheetAdapter = (TextView) viewGroup.findViewById(R.id.tvCondition_CollectionSheetAdapter);

        tvId_CollectionSheetAdapter.setText(String.format("ID: %s", list.get(position).getCollectionSheet().getId()));
        tvSaleId_CollectionSheetAdapter.setText(String.format("N° Pedido: %s", list.get(position).getSale().getId()));
        tvBusinessName_CollectionSheetAdapter.setText(String.format("Razón social: %s", list.get(position).getSale().getClient().getPerson().getBusinessName()));
        tvBalance_CollectionSheetAdapter.setText(String.format("Saldo: S/.%s", MyMath.toDecimal(list.get(position).getBalance(), 2)));
        tvAmortization_CollectionSheetAdapter.setText(String.format("Amortización: S/.%s", MyMath.toDecimal(list.get(position).getAmortization(), 2)));
        tvCondition_CollectionSheetAdapter.setText(String.format("Condición: %s", list.get(position).getBalance() == 0.0F ? "CANCELADO" : "ADEUDA"));

        return viewGroup;
    }
}
