package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Data.MyDateTime;

public class Sale implements Parcelable {

    private Object Id;
    private VoucherType VoucherType;
    private Customer Client;
    private Employee Employee;
    private Date IssueDate, ExpirationDate, CreationDate;
    private PaymentCondition PaymentCondition;
    private PaymentMethod PaymentMethod;
    private float SaleValue, Interest, Discount, SubTotal, Tax, Total;
    private Boolean State;

    public Sale() {
    }

    protected Sale(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        VoucherType = in.readParcelable(Data.Objects.VoucherType.class.getClassLoader());
        Client = in.readParcelable(Customer.class.getClassLoader());
        Employee = in.readParcelable(Employee.class.getClassLoader());
        long tmpIssueDate = in.readLong();
        IssueDate = tmpIssueDate != 0 ? new Date(tmpIssueDate) : null;
        long tmpExpirationDate = in.readLong();
        ExpirationDate = tmpExpirationDate != 0 ? new Date(tmpExpirationDate) : null;
        long tmpCreationDate = in.readLong();
        CreationDate = tmpCreationDate != 0 ? new Date(tmpCreationDate) : null;
        PaymentCondition = in.readParcelable(Data.Objects.PaymentCondition.class.getClassLoader());
        PaymentMethod = in.readParcelable(Data.Objects.PaymentMethod.class.getClassLoader());
        SaleValue = in.readFloat();
        Interest = in.readFloat();
        Discount = in.readFloat();
        SubTotal = in.readFloat();
        Tax = in.readFloat();
        Total = in.readFloat();
        byte tmpState = in.readByte();
        State = tmpState == 0 ? null : tmpState == 1;
    }

    public static final Creator<Sale> CREATOR = new Creator<Sale>() {
        @Override
        public Sale createFromParcel(Parcel in) {
            return new Sale(in);
        }

        @Override
        public Sale[] newArray(int size) {
            return new Sale[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public Data.Objects.VoucherType getVoucherType() {
        return VoucherType;
    }

    public void setVoucherType(Data.Objects.VoucherType voucherType) {
        VoucherType = voucherType;
    }

    public Customer getClient() {
        return Client;
    }

    public void setClient(Customer client) {
        Client = client;
    }

    public Employee getEmployee() {
        return Employee;
    }

    public void setEmployee(Employee employee) {
        Employee = employee;
    }

    public Date getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(Date issueDate) {
        IssueDate = issueDate;
    }

    public Date getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        ExpirationDate = expirationDate;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }

    public PaymentCondition getPaymentCondition() {
        return PaymentCondition;
    }

    public void setPaymentCondition(PaymentCondition paymentCondition) {
        PaymentCondition = paymentCondition;
    }

    public PaymentMethod getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public float getSaleValue() {
        return SaleValue;
    }

    public void setSaleValue(float saleValue) {
        SaleValue = saleValue;
    }

    public float getInterest() {
        return Interest;
    }

    public void setInterest(float interest) {
        Interest = interest;
    }

    public float getDiscount() {
        return Discount;
    }

    public void setDiscount(float discount) {
        Discount = discount;
    }

    public float getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(float subTotal) {
        SubTotal = subTotal;
    }

    public float getTax() {
        return Tax;
    }

    public void setTax(float tax) {
        Tax = tax;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
    }

    public Boolean getState() {
        return State;
    }

    public void setState(Boolean state) {
        State = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(Id);
        parcel.writeParcelable(VoucherType, i);
        parcel.writeParcelable(Client, i);
        parcel.writeParcelable(Employee, i);
        parcel.writeLong(IssueDate != null ? IssueDate.getTime() : 0);
        parcel.writeLong(ExpirationDate != null ? ExpirationDate.getTime() : 0);
        parcel.writeLong(CreationDate != null ? CreationDate.getTime() : 0);
        parcel.writeParcelable(PaymentCondition, i);
        parcel.writeParcelable(PaymentMethod, i);
        parcel.writeFloat(SaleValue);
        parcel.writeFloat(Interest);
        parcel.writeFloat(Discount);
        parcel.writeFloat(SubTotal);
        parcel.writeFloat(Tax);
        parcel.writeFloat(Total);
        parcel.writeByte((byte) (State == null ? 0 : State ? 1 : 2));
    }

    public static Sale getItem(JSONObject result)
    {
        try
        {
            Sale objSale = new Sale();

            objSale.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objSale.setVoucherType(result.get("VoucherType") != JSONObject.NULL ?
                    Data.Objects.VoucherType.getItem(new JSONObject(result.get("VoucherType").toString())) : null);
            objSale.setClient(result.get("Client") != JSONObject.NULL ?
                    Data.Objects.Customer.getItem(new JSONObject(result.get("Client").toString())) : null);
            objSale.setEmployee(result.get("Employee") != JSONObject.NULL ?
                    Data.Objects.Employee.getItem(new JSONObject(result.get("Employee").toString())) : null);
            objSale.setIssueDate(result.get("IssueDate") != JSONObject.NULL ? MyDateTime.parseNet(result.get("IssueDate").toString()) : null);
            objSale.setExpirationDate(result.get("ExpirationDate") != JSONObject.NULL ? MyDateTime.parseNet(result.get("ExpirationDate").toString()) : null);
            objSale.setCreationDate(result.get("CreationDate") != JSONObject.NULL ? MyDateTime.parseNet(result.get("CreationDate").toString()) : null);
            objSale.setPaymentCondition(result.get("PaymentCondition") != JSONObject.NULL ?
                    Data.Objects.PaymentCondition.getItem(new JSONObject(result.get("PaymentCondition").toString())) : null);
            objSale.setPaymentMethod(result.get("PaymentMethod") != JSONObject.NULL ?
                    Data.Objects.PaymentMethod.getItem(new JSONObject(result.get("PaymentMethod").toString())) : null);
            objSale.setSaleValue(result.get("SaleValue") != JSONObject.NULL ? Float.parseFloat(result.get("SaleValue").toString()) : 0F);
            objSale.setInterest(result.get("Interest") != JSONObject.NULL ? Float.parseFloat(result.get("Interest").toString()) : 0F);
            objSale.setDiscount(result.get("Discount") != JSONObject.NULL ? Float.parseFloat(result.get("Discount").toString()) : 0F);
            objSale.setSubTotal(result.get("SubTotal") != JSONObject.NULL ? Float.parseFloat(result.get("SubTotal").toString()) : 0F);
            objSale.setTax(result.get("Tax") != JSONObject.NULL ? Float.parseFloat(result.get("Tax").toString()) : 0F);
            objSale.setTotal(result.get("Total") != JSONObject.NULL ? Float.parseFloat(result.get("Total").toString()) : 0F);
            objSale.setState(result.get("State") != JSONObject.NULL ? result.getBoolean("State") : null);

            return objSale;
        }
        catch (Exception e)
        {
            Log.e("json_sale", e.getMessage());
            return null;
        }
    }

    public static List<Sale> getList(JSONArray result)
    {
        List<Sale> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                Sale objSale;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objSale = getItem(array);

                    list.add(objSale);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("json_sale", e.getMessage());
            return null;
        }
        return list;
    }
}
