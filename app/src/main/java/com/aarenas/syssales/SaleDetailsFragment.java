package com.aarenas.syssales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.Objects.Company;
import Data.Objects.Product;
import Data.Objects.Sale;
import Data.Objects.SaleDetail;
import Data.Objects.User;
import Data.Utilities;
import Design.SaleDetailsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaleDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaleDetailsFragment extends Fragment implements WebServices.OnResult {

    private OnFragmentInteractionListener mListener;

    public SaleDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param params Parameter 1.
     * @return A new instance of fragment SaleDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaleDetailsFragment newInstance(Bundle params) {
        SaleDetailsFragment fragment = new SaleDetailsFragment();
        fragment.setArguments(params);
        return fragment;
    }

    //Controls:
    private TextInputEditText etProductId_SaleDetailsFragment;
    private ImageButton btnFindProduct_SaleDetailsFragment, btnProducts_SaleDetailsFragment;
    private TextView tvItemsCount_SaleDetailsFragment;
    private GridView gvData_SaleDetailsFragment;

    //Parameters:
    private Bundle parameters;
    private User objUser;
    private Company objCompany;
    Sale objSale = new Sale();

    //Asynchronous data:
    List<SaleDetail> list;

    //Variables:
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parameters = getArguments();
        LoadParameters(parameters);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sale_details, container, false);

        etProductId_SaleDetailsFragment = rootView.findViewById(R.id.etProductId_SaleDetailsFragment);
        btnFindProduct_SaleDetailsFragment = rootView.findViewById(R.id.btnFindProduct_SaleDetailsFragment);
        btnProducts_SaleDetailsFragment = rootView.findViewById(R.id.btnProducts_SaleDetailsFragment);
        tvItemsCount_SaleDetailsFragment = rootView.findViewById(R.id.tvItemsCount_SaleDetailsFragment);
        gvData_SaleDetailsFragment = rootView.findViewById(R.id.gvData_SaleDetailsFragment);

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            WebMethods objWebMethods = new WebMethods(getActivity(), SaleDetailsFragment.this);
                            objWebMethods.getTempSaleDetails(objSale.getId(), objCompany.getId());
                        }
                    }
                });

        gvData_SaleDetailsFragment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                SaleDetail objSaleDetail = (SaleDetail) adapterView.getItemAtPosition(i);

                if (objSaleDetail != null)
                {
                    PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), view);
                    popupMenu.inflate(R.menu.custom_product_sale_detail);
                    popupMenu.getMenu().findItem(R.id.action_edit_item).setVisible(true);
                    popupMenu.getMenu().findItem(R.id.action_delete_item).setVisible(true);
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            final int itemEdit = R.id.action_edit_item;
                            final int itemDelete = R.id.action_delete_item;

                            switch (menuItem.getItemId())
                            {
                                case itemEdit:
                                    Intent intent = new Intent(getActivity().getApplicationContext(), SaleDetailActivity.class);
                                    intent.putExtra("sale", objSale);
                                    intent.putExtra("product", objSaleDetail.getProduct());
                                    intent.putExtra("quantity", objSaleDetail.getQuantity());
                                    intent.putExtra(SaleDetailActivity.CONST_RESULT, SaleDetailActivity.RESULT_MODIFY);
                                    resultLauncher.launch(intent);
                                    break;
                                case itemDelete:
                                    WebMethods objWebMethods = new WebMethods(getActivity(), SaleDetailsFragment.this);
                                    objWebMethods.deleteItem(objSale.getId(), objSaleDetail.getProduct().getDecimalUnit(),
                                            (String)objSale.getVoucherType().getId(), objSaleDetail.getQuantity());
                                    break;
                                default:
                                    break;
                            }
                            return true;
                        }
                    });

                    return true;
                }

                return false;
            }
        });

        btnFindProduct_SaleDetailsFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.clearFocus(getActivity());

                String productId = etProductId_SaleDetailsFragment.getText().toString();
                if (productId.length() == 6)
                {
                    WebMethods objWebMethods = new WebMethods(getActivity(), SaleDetailsFragment.this);
                    objWebMethods.getProductById(productId, objCompany.getId(), objUser.getEmployee().getOutlet().getId());
                }
            }
        });

        btnProducts_SaleDetailsFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.clearFocus(getActivity());

                if (objSale != null && objSale.getEmployee() != null && objSale.getVoucherType() != null
                    && objSale.getPaymentCondition() != null)
                {
                    Intent intent = new Intent(getActivity(), ProductsActivity.class);
                    intent.putExtra("user", objUser);
                    intent.putExtra("company", objCompany);
                    intent.putExtra("sale", objSale);
                    intent.putExtra(ProductsActivity.CONST_RESULT, ProductsActivity.RESULT_PICKER);
                    resultLauncher.launch(intent);
                }
            }
        });

        WebMethods objWebMethods = new WebMethods(getActivity(), this);
        objWebMethods.getTempSaleDetails(objSale.getId(), objCompany.getId());

        return rootView;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_LIST_TEMP_SALE_DETAILS)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        list = SaleDetail.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            SaleDetailsAdapter<SaleDetail> adapter =
                                    new SaleDetailsAdapter<>(getActivity().getApplicationContext(), list);

                            gvData_SaleDetailsFragment.setAdapter(adapter);
                            tvItemsCount_SaleDetailsFragment.setText(String.format("%s item(s)", list.size()));
                        }

                        InteractionFragment();
                    }
                    else if (processId == WebMethods.TYPE_FIND_PRODUCT_BY_ID)
                    {
                        JSONObject jsonObject = new JSONObject(result.getResult().toString());
                        Product objProduct = Product.getItem(jsonObject);
                        if (objProduct != null)
                        {
                            if (objSale != null && objSale.getEmployee() != null && objSale.getVoucherType() != null)
                            {
                                Intent intent = new Intent(getActivity().getApplicationContext(), SaleDetailActivity.class);
                                intent.putExtra("sale", objSale);
                                intent.putExtra("product", objProduct);
                                intent.putExtra(SaleDetailActivity.CONST_RESULT, SaleDetailActivity.RESULT_ADD);
                                resultLauncher.launch(intent);

                                etProductId_SaleDetailsFragment.getText().clear();
                            }
                        }
                    }
                    else if (processId == WebMethods.TYPE_DELETE_ITEM_SALE)
                    {
                        boolean reply = Boolean.parseBoolean(result.getResult().toString());
                        if (reply)
                        {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    R.string.message_changes_performed_successfully, Toast.LENGTH_SHORT).show();

                            WebMethods objWebMethods = new WebMethods(getActivity(), SaleDetailsFragment.this);
                            objWebMethods.getTempSaleDetails(objSale.getId(), objCompany.getId());
                        }
                    }
                }
                else
                {
                    if (processId == WebMethods.TYPE_LIST_TEMP_SALE_DETAILS)
                    {
                        list = null;
                        gvData_SaleDetailsFragment.setAdapter(null);
                        tvItemsCount_SaleDetailsFragment.setText(String.format("%s item(s)", 0));

                        InteractionFragment();
                    }
                    else if (processId == WebMethods.TYPE_FIND_PRODUCT_BY_ID)
                    {
                        Toast.makeText(getActivity().getApplicationContext(), R.string.message_no_data, Toast.LENGTH_SHORT).show();
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
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            String error_message = preferences.getBoolean("depuration", false) ? e.getMessage() : getString(R.string.message_web_services_error);

            Toast.makeText(getActivity().getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
            Log.e("WS_SaleDetailsFr", String.format("%s (processId: %s)", error_message, processId));
        }
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(HashMap<String, Object> objects, int id);
    }

    private void LoadParameters(Bundle data)
    {
        if (data != null)
        {
            if (data.get("user") != null)
            {
                this.objUser = data.getParcelable("user");
                this.objSale.setEmployee(this.objUser.getEmployee());
            }
            if (data.get("company") != null)
            {
                this.objCompany = data.getParcelable("company");
            }
            if (data.get("saleId") != null)
            {
                this.objSale.setId(data.getString("saleId"));
            }
        }
    }

    public void UpdateData(HashMap<String, Object> objects, int id)
    {
        final int fragment_1 = R.layout.fragment_sale_data;

        if ((objects != null ? objects.size() : 0) > 0)
        {
            if (objects.get("sale") != null)
            {
                Sale _sale = (Sale)objects.get("sale");
                if (_sale != null)
                {
                    if (id == fragment_1)
                    {
                        if (_sale.getVoucherType() != null)
                        {
                            this.objSale.setVoucherType(_sale.getVoucherType());
                        }
                        if (_sale.getPaymentCondition() != null)
                        {
                            this.objSale.setPaymentCondition(_sale.getPaymentCondition());
                        }
                        if (_sale.getIssueDate() != null)
                        {
                            this.objSale.setIssueDate(_sale.getIssueDate());
                        }
                        if (_sale.getExpirationDate() != null)
                        {
                            this.objSale.setExpirationDate(_sale.getExpirationDate());
                        }
                    }
                }
            }
        }
    }

    private void InteractionFragment()
    {
        HashMap<String, Object> objects = new HashMap<>();

        final int fragment_2 = R.layout.fragment_sale_details;

        if (objSale != null)
        {
            float saleValue = 0F, subTotal = 0F, tax = 0F, total = 0F;
            if (list != null && list.size() > 0)
            {
                for (int i=0; i < list.size(); i++)
                {
                    saleValue += (list.get(i).getProduct().getPrice() * list.get(i).getQuantity());
                    subTotal += list.get(i).getSubTotal();
                    tax += list.get(i).getTax();
                    total += list.get(i).getTotal();
                }
            }
            objSale.setSaleValue(saleValue);
            objSale.setSubTotal(subTotal);
            objSale.setTax(tax);
            objSale.setTotal(total);

            objects.put("sale", objSale);

            mListener.onFragmentInteraction(objects, fragment_2);
        }
    }
}