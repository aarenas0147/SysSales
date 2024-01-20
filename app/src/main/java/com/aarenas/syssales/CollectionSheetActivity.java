package com.aarenas.syssales;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.aarenas.syssales.databinding.ActivityCollectionSheetBinding;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;

import java.util.Calendar;
import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.MyDateTime;
import Data.MyMath;
import Data.Objects.CollectionSheetDetail;
import Data.Objects.User;
import Design.CollectionSheetAdapter;

public class CollectionSheetActivity extends AppCompatActivity implements WebServices.OnResult {

    private ActivityCollectionSheetBinding binding;

    //Controls:
    TextInputEditText etCollectionSheetDate_CollectionSheetActivity, etTotalCollectionSales_CollectionSheetActivity;
    TextView tvItemsCount_CollectionSheetActivity;
    GridView gvData_CollectionSheetActivity;

    //Parameters:
    Bundle parameters;
    User objUser;

    //Variables:
    private SharedPreferences preferences;
    private WebMethods objWebMethods;
    Calendar collectionSheetDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        binding = ActivityCollectionSheetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        etCollectionSheetDate_CollectionSheetActivity = findViewById(R.id.etCollectionSheetDate_CollectionSheetActivity);
        etTotalCollectionSales_CollectionSheetActivity = findViewById(R.id.etTotalCollectionSales_CollectionSheetActivity);
        tvItemsCount_CollectionSheetActivity = findViewById(R.id.tvItemsCount_CollectionSheetActivity);
        gvData_CollectionSheetActivity = findViewById(R.id.gvData_CollectionSheetActivity);

        preferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);
        objWebMethods = new WebMethods(this, this);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Recargando...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                startActivity(getIntent());
                finish();
                overridePendingTransition(0, 0);
            }
        });

        etCollectionSheetDate_CollectionSheetActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(CollectionSheetActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        collectionSheetDate.set(year, month, day);

                        String date = MyDateTime.format(collectionSheetDate.getTime(), MyDateTime.TYPE_DATE);
                        etCollectionSheetDate_CollectionSheetActivity.setText(date);

                        objWebMethods.getSheet(date, objUser.getEmployee().getId());
                    }
                }, collectionSheetDate.get(Calendar.YEAR), collectionSheetDate.get(Calendar.MONTH),
                        collectionSheetDate.get(Calendar.DAY_OF_MONTH));

                dialog.show();
            }
        });

        etCollectionSheetDate_CollectionSheetActivity.setText(
                MyDateTime.format(MyDateTime.getCurrentDatetime(), MyDateTime.TYPE_DATE));
        etTotalCollectionSales_CollectionSheetActivity.setText(MyMath.toDecimal(0F, 2));

        objWebMethods.getSheet(etCollectionSheetDate_CollectionSheetActivity.getText().toString(), objUser.getEmployee().getId());
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_LIST_COLLECTION_SHEET)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<CollectionSheetDetail> list = CollectionSheetDetail.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            CollectionSheetAdapter adapter = new CollectionSheetAdapter(getApplicationContext(), list);
                            gvData_CollectionSheetActivity.setAdapter(adapter);

                            float total = 0F;
                            for (int i=0; i < list.size(); i++)
                            {
                                total += list.get(i).getAmortization();
                            }

                            etTotalCollectionSales_CollectionSheetActivity.setText(MyMath.toDecimal(total, 2));
                            tvItemsCount_CollectionSheetActivity.setText(String.format("%s item(s)", list.size()));
                        }
                    }
                }
                else
                {
                    if (processId == WebMethods.TYPE_LIST_COLLECTION_SHEET)
                    {
                        gvData_CollectionSheetActivity.setAdapter(null);
                        Toast.makeText(getApplicationContext(), R.string.message_no_data, Toast.LENGTH_SHORT).show();

                        etTotalCollectionSales_CollectionSheetActivity.setText(MyMath.toDecimal(0F, 2));
                        tvItemsCount_CollectionSheetActivity.setText(String.format("%s item(s)", 0));
                    }
                }
            }
            else if (result.getResultCode().getId() == WebServices.Result.RESULT_OFFLINE)
            {
                throw new Exception(getString(R.string.message_without_connection));
            }
            else if (result.getResultCode().getId() == WebServices.Result.RESULT_ERROR)
            {
                throw new Exception(result.getResult() != null ? result.getResult().toString() : getString(R.string.message_web_services_error));
            }
            else
            {
                throw new Exception(getString(R.string.message_web_services_error));
            }
        }
        catch (Exception e)
        {
            String error_message = preferences.getBoolean("depuration", false) ? e.getMessage() : getString(R.string.message_web_services_error);

            Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
            Log.e("WS_CollectionSheet", String.format("%s (processId: %s)", error_message, processId));
        }
    }

    private void LoadParameters(Bundle data)
    {
        if (data != null)
        {
            if (data.get("user") != null)
            {
                this.objUser = data.getParcelable("user");
            }
        }
    }
}