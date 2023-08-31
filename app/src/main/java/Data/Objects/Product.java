package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Product implements Parcelable {

    private Object Id;
    private String Description;
    private Object DecimalUnit;
    private MeasureUnit MeasureUnit;
    private String Image;
    private Float Price, LimitPrice, CreditPrice, Stock, MinimumStock;
    private Boolean Fractionary, Enabled;

    public Product() {
    }

    protected Product(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Description = in.readString();
        DecimalUnit = in.readValue(getClass().getClassLoader());
        MeasureUnit = in.readParcelable(Data.Objects.MeasureUnit.class.getClassLoader());
        Image = in.readString();
        if (in.readByte() == 0) {
            Price = null;
        } else {
            Price = in.readFloat();
        }
        if (in.readByte() == 0) {
            LimitPrice = null;
        } else {
            LimitPrice = in.readFloat();
        }
        if (in.readByte() == 0) {
            CreditPrice = null;
        } else {
            CreditPrice = in.readFloat();
        }
        if (in.readByte() == 0) {
            Stock = null;
        } else {
            Stock = in.readFloat();
        }
        if (in.readByte() == 0) {
            MinimumStock = null;
        } else {
            MinimumStock = in.readFloat();
        }
        byte tmpFractionary = in.readByte();
        Fractionary = tmpFractionary == 0 ? null : tmpFractionary == 1;
        byte tmpEnabled = in.readByte();
        Enabled = tmpEnabled == 0 ? null : tmpEnabled == 1;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Object getDecimalUnit() {
        return DecimalUnit;
    }

    public void setDecimalUnit(Object decimalUnit) {
        DecimalUnit = decimalUnit;
    }

    public MeasureUnit getMeasureUnit() {
        return MeasureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        MeasureUnit = measureUnit;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public Float getLimitPrice() {
        return LimitPrice;
    }

    public void setLimitPrice(Float limitPrice) {
        LimitPrice = limitPrice;
    }

    public Float getCreditPrice() {
        return CreditPrice;
    }

    public void setCreditPrice(Float creditPrice) {
        CreditPrice = creditPrice;
    }

    public Float getStock() {
        return Stock;
    }

    public void setStock(Float stock) {
        Stock = stock;
    }

    public Float getMinimumStock() {
        return MinimumStock;
    }

    public void setMinimumStock(Float minimumStock) {
        MinimumStock = minimumStock;
    }

    public Boolean getFractionary() {
        return Fractionary;
    }

    public void setFractionary(Boolean fractionary) {
        Fractionary = fractionary;
    }

    public Boolean getEnabled() {
        return Enabled;
    }

    public void setEnabled(Boolean enabled) {
        Enabled = enabled;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(Id);
        parcel.writeString(Description);
        parcel.writeValue(DecimalUnit);
        parcel.writeParcelable(MeasureUnit, i);
        parcel.writeString(Image);
        if (Price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(Price);
        }
        if (LimitPrice == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(LimitPrice);
        }
        if (CreditPrice == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(CreditPrice);
        }
        if (Stock == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(Stock);
        }
        if (MinimumStock == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(MinimumStock);
        }
        parcel.writeByte((byte) (Fractionary == null ? 0 : Fractionary ? 1 : 2));
        parcel.writeByte((byte) (Enabled == null ? 0 : Enabled ? 1 : 2));
    }

    public static Product getItem(JSONObject result)
    {
        try
        {
            Product objProduct = new Product();

            objProduct.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objProduct.setDescription(result.get("Description") != JSONObject.NULL ? result.getString("Description") : null);
            objProduct.setDecimalUnit(result.get("DecimalUnit") != JSONObject.NULL ? result.get("DecimalUnit") : null);
            objProduct.setMeasureUnit(result.get("MeasureUnit") != JSONObject.NULL ?
                    Data.Objects.MeasureUnit.getItem(new JSONObject(result.get("MeasureUnit").toString())) : null);
            objProduct.setPrice(result.get("Price") != JSONObject.NULL ? Float.parseFloat(result.get("Price").toString()) : 0F);
            objProduct.setLimitPrice(result.get("LimitPrice") != JSONObject.NULL ? Float.parseFloat(result.get("LimitPrice").toString()) : 0F);
            objProduct.setCreditPrice(result.get("CreditPrice") != JSONObject.NULL ? Float.parseFloat(result.get("CreditPrice").toString()) : 0F);
            objProduct.setStock(result.get("Stock") != JSONObject.NULL ? Float.parseFloat(result.get("Stock").toString()) : 0F);
            objProduct.setMinimumStock(result.get("MinimumStock") != JSONObject.NULL ? Float.parseFloat(result.get("MinimumStock").toString()) : 0F);
            objProduct.setImage(result.get("Image") != JSONObject.NULL ? result.getString("Image") : null);
            objProduct.setFractionary(result.get("Fractionary") != JSONObject.NULL ? result.getBoolean("Fractionary") : null);
            objProduct.setEnabled(result.get("Enabled") != JSONObject.NULL ? result.getBoolean("Enabled") : null);

            return objProduct;
        }
        catch (Exception e)
        {
            Log.e("json_product", e.getMessage());
            return null;
        }
    }

    public static List<Product> getList(JSONArray result)
    {
        List<Product> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                Product objProduct;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objProduct = getItem(array);

                    list.add(objProduct);
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
