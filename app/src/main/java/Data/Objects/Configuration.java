package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Configuration implements Parcelable {

    private Object Id;
    private boolean OptionShowNotifications, OptionNewSale, OptionSales, OptionAccountReceivable,
            OptionCollectionSheet, OptionNewCustomer, OptionEditCustomer, OptionFindPersonOnline,
            OptionNewProduct, OptionTimeLimit, OptionSalesByVendor, OptionVendors, OptionVendorByCustomer,
            OptionIssueDate, OptionExpiryDate, OptionCustomPaymentMethod, OptionCreditLine,
            OptionPrintSale, OptionPrintSalePdf;

    public Configuration() {
    }

    protected Configuration(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        OptionShowNotifications = in.readByte() != 0;
        OptionNewSale = in.readByte() != 0;
        OptionSales = in.readByte() != 0;
        OptionAccountReceivable = in.readByte() != 0;
        OptionCollectionSheet = in.readByte() != 0;
        OptionNewCustomer = in.readByte() != 0;
        OptionEditCustomer = in.readByte() != 0;
        OptionFindPersonOnline = in.readByte() != 0;
        OptionNewProduct = in.readByte() != 0;
        OptionTimeLimit = in.readByte() != 0;
        OptionSalesByVendor = in.readByte() != 0;
        OptionVendors = in.readByte() != 0;
        OptionVendorByCustomer = in.readByte() != 0;
        OptionIssueDate = in.readByte() != 0;
        OptionExpiryDate = in.readByte() != 0;
        OptionCustomPaymentMethod = in.readByte() != 0;
        OptionCreditLine = in.readByte() != 0;
        OptionPrintSale = in.readByte() != 0;
        OptionPrintSalePdf = in.readByte() != 0;
    }

    public static final Creator<Configuration> CREATOR = new Creator<Configuration>() {
        @Override
        public Configuration createFromParcel(Parcel in) {
            return new Configuration(in);
        }

        @Override
        public Configuration[] newArray(int size) {
            return new Configuration[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public boolean isOptionShowNotifications() {
        return OptionShowNotifications;
    }

    public void setOptionShowNotifications(boolean optionShowNotifications) {
        OptionShowNotifications = optionShowNotifications;
    }

    public boolean isOptionNewSale() {
        return OptionNewSale;
    }

    public void setOptionNewSale(boolean optionNewSale) {
        OptionNewSale = optionNewSale;
    }

    public boolean isOptionSales() {
        return OptionSales;
    }

    public void setOptionSales(boolean optionSales) {
        OptionSales = optionSales;
    }

    public boolean isOptionAccountReceivable() {
        return OptionAccountReceivable;
    }

    public void setOptionAccountReceivable(boolean optionAccountReceivable) {
        OptionAccountReceivable = optionAccountReceivable;
    }

    public boolean isOptionCollectionSheet() {
        return OptionCollectionSheet;
    }

    public void setOptionCollectionSheet(boolean optionCollectionSheet) {
        OptionCollectionSheet = optionCollectionSheet;
    }

    public boolean isOptionNewCustomer() {
        return OptionNewCustomer;
    }

    public void setOptionNewCustomer(boolean optionNewCustomer) {
        OptionNewCustomer = optionNewCustomer;
    }

    public boolean isOptionEditCustomer() {
        return OptionEditCustomer;
    }

    public void setOptionEditCustomer(boolean optionEditCustomer) {
        OptionEditCustomer = optionEditCustomer;
    }

    public boolean isOptionFindPersonOnline() {
        return OptionFindPersonOnline;
    }

    public void setOptionFindPersonOnline(boolean optionFindPersonOnline) {
        OptionFindPersonOnline = optionFindPersonOnline;
    }

    public boolean isOptionNewProduct() {
        return OptionNewProduct;
    }

    public void setOptionNewProduct(boolean optionNewProduct) {
        OptionNewProduct = optionNewProduct;
    }

    public boolean isOptionTimeLimit() {
        return OptionTimeLimit;
    }

    public void setOptionTimeLimit(boolean optionTimeLimit) {
        OptionTimeLimit = optionTimeLimit;
    }

    public boolean isOptionSalesByVendor() {
        return OptionSalesByVendor;
    }

    public void setOptionSalesByVendor(boolean optionSalesByVendor) {
        OptionSalesByVendor = optionSalesByVendor;
    }

    public boolean isOptionVendors() {
        return OptionVendors;
    }

    public void setOptionVendors(boolean optionVendors) {
        OptionVendors = optionVendors;
    }

    public boolean isOptionVendorByCustomer() {
        return OptionVendorByCustomer;
    }

    public void setOptionVendorByCustomer(boolean optionVendorByCustomer) {
        OptionVendorByCustomer = optionVendorByCustomer;
    }

    public boolean isOptionIssueDate() {
        return OptionIssueDate;
    }

    public void setOptionIssueDate(boolean optionIssueDate) {
        OptionIssueDate = optionIssueDate;
    }

    public boolean isOptionExpiryDate() {
        return OptionExpiryDate;
    }

    public void setOptionExpiryDate(boolean optionExpiryDate) {
        OptionExpiryDate = optionExpiryDate;
    }

    public boolean isOptionCustomPaymentMethod() {
        return OptionCustomPaymentMethod;
    }

    public void setOptionCustomPaymentMethod(boolean optionCustomPaymentMethod) {
        OptionCustomPaymentMethod = optionCustomPaymentMethod;
    }

    public boolean isOptionCreditLine() {
        return OptionCreditLine;
    }

    public void setOptionCreditLine(boolean optionCreditLine) {
        OptionCreditLine = optionCreditLine;
    }

    public boolean isOptionPrintSale() {
        return OptionPrintSale;
    }

    public void setOptionPrintSale(boolean optionPrintSale) {
        OptionPrintSale = optionPrintSale;
    }

    public boolean isOptionPrintSalePdf() {
        return OptionPrintSalePdf;
    }

    public void setOptionPrintSalePdf(boolean optionPrintSalePdf) {
        OptionPrintSalePdf = optionPrintSalePdf;
    }

    public static Configuration getItem(JSONObject result)
    {
        try
        {
            Configuration objConfiguration = new Configuration();

            objConfiguration.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objConfiguration.setOptionShowNotifications(result.get("OptionShowNotifications") != JSONObject.NULL
                    && result.getBoolean("OptionShowNotifications"));
            objConfiguration.setOptionNewSale(result.get("OptionNewSale") != JSONObject.NULL
                    && result.getBoolean("OptionNewSale"));
            objConfiguration.setOptionSales(result.get("OptionSales") != JSONObject.NULL
                    && result.getBoolean("OptionSales"));
            objConfiguration.setOptionAccountReceivable(result.get("OptionAccountReceivable") != JSONObject.NULL
                    && result.getBoolean("OptionAccountReceivable"));
            objConfiguration.setOptionCollectionSheet(result.get("OptionCollectionSheet") != JSONObject.NULL
                    && result.getBoolean("OptionCollectionSheet"));
            objConfiguration.setOptionNewCustomer(result.get("OptionNewCustomer") != JSONObject.NULL
                    && result.getBoolean("OptionNewCustomer"));
            objConfiguration.setOptionEditCustomer(result.get("OptionEditCustomer") != JSONObject.NULL
                    && result.getBoolean("OptionEditCustomer"));
            objConfiguration.setOptionFindPersonOnline(result.get("OptionFindPersonOnline") != JSONObject.NULL
                    && result.getBoolean("OptionFindPersonOnline"));
            objConfiguration.setOptionNewProduct(result.get("OptionNewProduct") != JSONObject.NULL
                    && result.getBoolean("OptionNewProduct"));
            objConfiguration.setOptionTimeLimit(result.get("OptionTimeLimit") != JSONObject.NULL
                    && result.getBoolean("OptionTimeLimit"));
            objConfiguration.setOptionSalesByVendor(result.get("OptionSalesByVendor") != JSONObject.NULL
                    && result.getBoolean("OptionSalesByVendor"));
            objConfiguration.setOptionVendors(result.get("OptionVendors") != JSONObject.NULL
                    && result.getBoolean("OptionVendors"));
            objConfiguration.setOptionVendorByCustomer(result.get("OptionVendorByCustomer") != JSONObject.NULL
                    && result.getBoolean("OptionVendorByCustomer"));
            objConfiguration.setOptionIssueDate(result.get("OptionIssueDate") != JSONObject.NULL
                    && result.getBoolean("OptionIssueDate"));
            objConfiguration.setOptionExpiryDate(result.get("OptionExpiryDate") != JSONObject.NULL
                    && result.getBoolean("OptionExpiryDate"));
            objConfiguration.setOptionCustomPaymentMethod(result.get("OptionCustomPaymentMethod") != JSONObject.NULL
                    && result.getBoolean("OptionCustomPaymentMethod"));
            objConfiguration.setOptionPrintSale(result.get("OptionPrintSale") != JSONObject.NULL
                    && result.getBoolean("OptionPrintSale"));
            objConfiguration.setOptionCreditLine(result.get("OptionCreditLine") != JSONObject.NULL
                    && result.getBoolean("OptionCreditLine"));
            objConfiguration.setOptionPrintSalePdf(result.get("OptionPrintSalePdf") != JSONObject.NULL
                    && result.getBoolean("OptionPrintSalePdf"));

            return objConfiguration;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(Id);
        parcel.writeByte((byte) (OptionShowNotifications ? 1 : 0));
        parcel.writeByte((byte) (OptionNewSale ? 1 : 0));
        parcel.writeByte((byte) (OptionSales ? 1 : 0));
        parcel.writeByte((byte) (OptionAccountReceivable ? 1 : 0));
        parcel.writeByte((byte) (OptionCollectionSheet ? 1 : 0));
        parcel.writeByte((byte) (OptionNewCustomer ? 1 : 0));
        parcel.writeByte((byte) (OptionEditCustomer ? 1 : 0));
        parcel.writeByte((byte) (OptionFindPersonOnline ? 1 : 0));
        parcel.writeByte((byte) (OptionNewProduct ? 1 : 0));
        parcel.writeByte((byte) (OptionTimeLimit ? 1 : 0));
        parcel.writeByte((byte) (OptionSalesByVendor ? 1 : 0));
        parcel.writeByte((byte) (OptionVendors ? 1 : 0));
        parcel.writeByte((byte) (OptionVendorByCustomer ? 1 : 0));
        parcel.writeByte((byte) (OptionIssueDate ? 1 : 0));
        parcel.writeByte((byte) (OptionExpiryDate ? 1 : 0));
        parcel.writeByte((byte) (OptionCustomPaymentMethod ? 1 : 0));
        parcel.writeByte((byte) (OptionCreditLine ? 1 : 0));
        parcel.writeByte((byte) (OptionPrintSale ? 1 : 0));
        parcel.writeByte((byte) (OptionPrintSalePdf ? 1 : 0));
    }
}
