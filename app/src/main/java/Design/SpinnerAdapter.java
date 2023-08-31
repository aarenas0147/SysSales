package Design;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aarenas.syssales.R;

import java.util.List;

public class SpinnerAdapter<T> extends BaseAdapter{

    Context ctx;
    LayoutInflater layoutInflater;
    TextView tvDescription_SpinnerAdapter;
    List<SimpleClass<T>> list;

    public SpinnerAdapter()
    {

    }

    public SpinnerAdapter(Context context, List<SimpleClass<T>> list)
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
        View view = (View) this.layoutInflater.inflate(R.layout.custom_list_adapter, parent, false);
        tvDescription_SpinnerAdapter = (TextView) view.findViewById(R.id.tvDescription_SpinnerAdapter);
        tvDescription_SpinnerAdapter.setText(list.get(position).getDescription());
        return view;
    }

    /*@Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = (View) this.layoutInflater.inflate(R.layout.custom_list_adapter, null);
        tvDescription_SpinnerAdapter = (TextView) view.findViewById(R.id.tvDescription_SpinnerAdapter);
        tvDescription_SpinnerAdapter.setText(list.get(position).getDescription());
        return view;
    }*/
}
