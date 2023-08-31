package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Data.MyDateTime;

public class SaleDetail implements Parcelable {

    private Object Id;
    private Sale Sale;
    private Product Product;
    private Float Quantity, SubTotal, Tax, Total;

    public SaleDetail() {
    }

    protected SaleDetail(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Sale = in.readParcelable(Data.Objects.Sale.class.getClassLoader());
        Product = in.readParcelable(Data.Objects.Product.class.getClassLoader());
        if (in.readByte() == 0) {
            Quantity = null;
        } else {
            Quantity = in.readFloat();
        }
        if (in.readByte() == 0) {
            SubTotal = null;
        } else {
            SubTotal = in.readFloat();
        }
        if (in.readByte() == 0) {
            Tax = null;
        } else {
            Tax = in.readFloat();
        }
        if (in.readByte() == 0) {
            Total = null;
        } else {
            Total = in.readFloat();
        }
    }

    public static final Creator<SaleDetail> CREATOR = new Creator<SaleDetail>() {
        @Override
        public SaleDetail createFromParcel(Parcel in) {
            return new SaleDetail(in);
        }

        @Override
        public SaleDetail[] newArray(int size) {
            return new SaleDetail[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public Data.Objects.Sale getSale() {
        return Sale;
    }

    public void setSale(Data.Objects.Sale sale) {
        Sale = sale;
    }

    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product product) {
        Product = product;
    }

    public Float getQuantity() {
        return Quantity;
    }

    public void setQuantity(Float quantity) {
        Quantity = quantity;
    }

    public Float getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(Float subTotal) {
        SubTotal = subTotal;
    }

    public Float getTax() {
        return Tax;
    }

    public void setTax(Float tax) {
        Tax = tax;
    }

    public Float getTotal() {
        return Total;
    }

    public void setTotal(Float total) {
        Total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(Id);
        parcel.writeParcelable(Sale, i);
        parcel.writeParcelable(Product, i);
        if (Quantity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(Quantity);
        }
        if (SubTotal == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(SubTotal);
        }
        if (Tax == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(Tax);
        }
        if (Total == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(Total);
        }
    }

    public static SaleDetail getItem(JSONObject result)
    {
        try
        {
            SaleDetail objSaleDetail = new SaleDetail();

            objSaleDetail.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objSaleDetail.setSale(result.get("Sale") != JSONObject.NULL ?
                    Data.Objects.Sale.getItem(new JSONObject(result.get("Sale").toString())) : null);
            objSaleDetail.setProduct(result.get("Product") != JSONObject.NULL ?
                    Data.Objects.Product.getItem(new JSONObject(result.get("Product").toString())) : null);
            objSaleDetail.setQuantity(result.get("Quantity") != JSONObject.NULL ? Float.parseFloat(result.get("Quantity").toString()) : 0F);
            objSaleDetail.setSubTotal(result.get("SubTotal") != JSONObject.NULL ? Float.parseFloat(result.get("SubTotal").toString()) : 0F);
            objSaleDetail.setTax(result.get("Tax") != JSONObject.NULL ? Float.parseFloat(result.get("Tax").toString()) : 0F);
            objSaleDetail.setTotal(result.get("Total") != JSONObject.NULL ? Float.parseFloat(result.get("Total").toString()) : 0F);

            return objSaleDetail;
        }
        catch (Exception e)
        {
            Log.e("json_sale_detail", e.getMessage());
            return null;
        }
    }

    public static List<SaleDetail> getList(JSONArray result)
    {
        List<SaleDetail> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                SaleDetail objSaleDetail;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objSaleDetail = getItem(array);

                    list.add(objSaleDetail);
                }
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return list;
    }
}
