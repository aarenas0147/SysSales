package Connection;

import android.content.Context;

public class WebMethods {

    //Constants:
    public static final int TYPE_LIST_USER = 1;
    public static final int TYPE_LIST_CONFIGURATIONS = 2;
    public static final int TYPE_LIST_CONFIGURATIONS_X_USER = 3;
    public static final int TYPE_LIST_NOTIFICATIONS = 4;
    public static final int TYPE_LIST_PRINCIPAL_COMPANY = 5;
    public static final int TYPE_OPEN_SALE = 6;
    public static final int TYPE_LIST_VOUCHER_TYPES = 7;
    public static final int TYPE_LIST_PAYMENT_CONDITIONS = 8;
    public static final int TYPE_LIST_PAYMENT_METHODS = 9;
    public static final int TYPE_LIST_PAYMENT_METHOD_DETAILS = 51;
    public static final int TYPE_LIST_SALE_PAYMENT_METHODS = 10;
    public static final int TYPE_ADD_PAYMENT_METHOD_BY_SALE = 11;
    public static final int TYPE_MODIFY_PAYMENT_METHOD_BY_SALE = 12;
    public static final int TYPE_DELETE_PAYMENT_METHOD_BY_SALE = 13;
    public static final int TYPE_LIST_TEMP_SALE_HEADER = 14;
    public static final int TYPE_LIST_TEMP_SALE_DETAILS = 15;
    public static final int TYPE_ADD_ITEM_SALE = 16;
    public static final int TYPE_MODIFY_ITEM_SALE = 17;
    public static final int TYPE_DELETE_ITEM_SALE = 18;
    public static final int TYPE_SAVE_SALE = 19;
    public static final int TYPE_CANCEL_SALE = 20;
    public static final int TYPE_PRINT_SALE = 21;
    public static final int TYPE_PRINT_SALE_IN_PDF = 22;
    public static final int TYPE_FIND_CUSTOMER_BY_ID = 23;
    public static final int TYPE_LIST_CUSTOMERS = 24;
    public static final int TYPE_LIST_CUSTOMERS_BY_BUSINESS_NAME = 25;
    public static final int TYPE_LIST_CUSTOMERS_BY_VENDOR = 26;
    public static final int TYPE_GET_CUSTOMER_DEFAULT = 27;
    public static final int TYPE_FIND_PERSON_ONLINE = 28;
    public static final int TYPE_FIND_PRODUCT_BY_ID = 29;
    public static final int TYPE_LIST_PRODUCTS_BY_DESCRIPTION = 30;
    public static final int TYPE_LIST_PRESENTATIONS = 31;
    public static final int TYPE_GET_STOCK_BY_PRESENTATION = 32;
    public static final int TYPE_NEW_CUSTOMER = 33;
    public static final int TYPE_LIST_BUSINESS_LINE = 34;
    public static final int TYPE_LIST_ZONES = 35;
    public static final int TYPE_LIST_ROUTES_BY_ZONE = 36;
    public static final int TYPE_FIND_VENDOR_BY_ROUTE = 37;
    public static final int TYPE_INSERT_CUSTOMER = 38;
    public static final int TYPE_MODIFY_CUSTOMER = 39;
    public static final int TYPE_LIST_COLLECTION_SHEET = 40;
    public static final int TYPE_LIST_VENDORS = 41;
    public static final int TYPE_LIST_SALES_BY_VENDOR_HEADER = 42;
    public static final int TYPE_LIST_SALES_BY_VENDOR_DETAILS = 43;
    public static final int TYPE_LIST_SALE_DETAILS = 44;
    public static final int TYPE_TOTAL_AMOUNT_CREDIT_SALES = 45;
    public static final int TYPE_LIST_CREDIT_SALES = 46;
    public static final int TYPE_LIST_CREDIT_SALES_PENDING_BY_CUSTOMER = 47;
    public static final int TYPE_LIST_CREDIT_SALE_QUOTES = 48;
    public static final int TYPE_NEW_CREDIT_SALE_QUOTE = 49;
    public static final int TYPE_SAVE_CREDIT_SALE_QUOTE = 50;
    public static final int TYPE_DELETE_CREDIT_SALE_QUOTE = 51;

    //Variables:
    private Context context;
    private WebServices.OnResult listener;
    private WebConfig config;

    public WebMethods(Context context, WebServices.OnResult listener) {
        this.context = context;
        this.listener = listener;
        
        this.config = new WebConfig(context);
    }

    public void findUser(String user, String password)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "findUser";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Login.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_USER);

            webServices.addParameter("user", user);
            webServices.addParameter("password", password);
            webServices.execute();
        }
    }

    public void getNotifications()
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getNotifications";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Main.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_NOTIFICATIONS);

            webServices.execute();
        }
    }

    public void getConfiguration()
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getConfiguration";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Main.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_CONFIGURATIONS);

            webServices.execute();
        }
    }

    public void getConfigurationXUser(Object userId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getConfigurationXUser";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Main.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_CONFIGURATIONS_X_USER);

            webServices.addParameter("userId", userId);
            webServices.execute();
        }
    }

    public void getCompany()
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getCompany";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Main.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_PRINCIPAL_COMPANY);

            webServices.execute();
        }
    }

    public void openSale(int personId, int userId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "openSale";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_OPEN_SALE);

            webServices.addParameter("personId", personId);
            webServices.addParameter("userId", userId);
            webServices.execute();
        }
    }

    public void getVoucherTypes()
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getVoucherTypes";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_VOUCHER_TYPES);

            webServices.execute();
        }
    }

    public void getPaymentConditions()
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getPaymentConditions";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_PAYMENT_CONDITIONS);

            webServices.execute();
        }
    }

    public void getPaymentMethods()
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getPaymentMethods";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_PAYMENT_METHODS);

            webServices.execute();
        }
    }

    public void getPaymentMethodDetails(Object paymentMethod)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getPaymentMethodDetails";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_PAYMENT_METHOD_DETAILS);

            webServices.addParameter("paymentMethod", paymentMethod);
            webServices.execute();
        }
    }

    public void getSalePaymentMethods(Object id)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getSalePaymentMethods";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_SALE_PAYMENT_METHODS);

            webServices.addParameter("id", id);
            webServices.execute();
        }
    }

    public void addSalePaymentMethod(Object id, Object paymentMethod, Object paymentMethodDetail, float amount)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "addSalePaymentMethod";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_ADD_PAYMENT_METHOD_BY_SALE);

            webServices.addParameter("id", id);
            webServices.addParameter("paymentMethod", paymentMethod);
            webServices.addParameter("paymentMethodDetail", paymentMethodDetail);
            webServices.addParameter("amount", String.format("%s", amount));
            webServices.execute();
        }
    }

    public void modifySalePaymentMethod(Object id, Object paymentMethod, Object paymentMethodDetail, float amount)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "modifySalePaymentMethod";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_MODIFY_PAYMENT_METHOD_BY_SALE);

            webServices.addParameter("id", id);
            webServices.addParameter("paymentMethod", paymentMethod);
            webServices.addParameter("paymentMethodDetail", paymentMethodDetail);
            webServices.addParameter("amount", String.format("%s", amount));
            webServices.execute();
        }
    }

    public void deleteSalePaymentMethod(Object id, Object paymentMethod)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "deleteSalePaymentMethod";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_DELETE_PAYMENT_METHOD_BY_SALE);

            webServices.addParameter("id", id);
            webServices.addParameter("paymentMethod", paymentMethod);
            webServices.execute();
        }
    }

    public void getTempSaleHeader(Object id)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getTempSaleHeader";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_TEMP_SALE_HEADER);

            webServices.addParameter("id", id);
            webServices.execute();
        }
    }

    public void getTempSaleDetails(Object id)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getTempSaleDetails";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_TEMP_SALE_DETAILS);

            webServices.addParameter("id", id);
            webServices.execute();
        }
    }

    public void addItem(Object id, Object productId, int personId, String voucherTypeId, Object paymentCondition, float quantity, float price)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "addItem";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_ADD_ITEM_SALE);

            webServices.addParameter("id", id);
            webServices.addParameter("productId", productId);
            webServices.addParameter("personId", personId);
            webServices.addParameter("voucherTypeId", voucherTypeId);
            webServices.addParameter("paymentCondition", paymentCondition);
            webServices.addParameter("quantity", String.format("%s", quantity));
            webServices.addParameter("price", String.format("%s", price));
            webServices.execute();
        }
    }

    public void modifyItem(Object id, Object productId, int personId, String voucherTypeId, Object paymentCondition,
                           float quantity, float newQuantity,float price)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "modifyItem";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_MODIFY_ITEM_SALE);

            webServices.addParameter("id", id);
            webServices.addParameter("productId", productId);
            webServices.addParameter("personId", personId);
            webServices.addParameter("voucherTypeId", voucherTypeId);
            webServices.addParameter("paymentCondition", paymentCondition);
            webServices.addParameter("quantity", String.format("%s", quantity));
            webServices.addParameter("newQuantity", String.format("%s", newQuantity));
            webServices.addParameter("price", String.format("%s", price));
            webServices.execute();
        }
    }

    public void deleteItem(Object id, Object productId, String voucherTypeId, float quantity)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "deleteItem";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_DELETE_ITEM_SALE);

            webServices.addParameter("id", id);
            webServices.addParameter("productId", productId);
            webServices.addParameter("voucherTypeId", voucherTypeId);
            webServices.addParameter("quantity", String.format("%s", quantity));
            webServices.execute();
        }
    }

    public void saveSale(Object id, String voucherTypeId, Object paymentCondition, Object paymentMethod, String issueDate, String expirationDate,
                         String currentDateTime, String customerId, int personId, int userId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "saveSale";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "NewSale.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_SAVE_SALE);

            webServices.addParameter("id", id);
            webServices.addParameter("voucherTypeId", voucherTypeId);
            webServices.addParameter("paymentCondition", paymentCondition);
            webServices.addParameter("paymentMethod", paymentMethod);
            webServices.addParameter("issueDate", issueDate);
            webServices.addParameter("expirationDate", expirationDate);
            webServices.addParameter("currentDateTime", currentDateTime);
            webServices.addParameter("customerId", customerId);
            webServices.addParameter("personId", personId);
            webServices.addParameter("userId", userId);
            webServices.execute();
        }
    }

    public void cancelSale(Object id, Object vendorId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "cancelSale";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Sales.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_CANCEL_SALE);

            webServices.addParameter("id", id);
            webServices.addParameter("vendorId", vendorId);
            webServices.execute();
        }
    }

    public void printSale(Object id, int userId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "printSale";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Sales.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_PRINT_SALE);

            webServices.addParameter("id", id);
            webServices.addParameter("userId", userId);
            webServices.execute();
        }
    }

    public void printSaleInPDF(Object id)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "printSaleInPDF";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Sales.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_PRINT_SALE_IN_PDF);

            webServices.addParameter("id", id);
            webServices.execute();
        }
    }

    public void getCustomers()
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getCustomers";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_CUSTOMERS);

            webServices.execute();
        }
    }

    public void getCustomerById(Object id)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getCustomerById";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_FIND_CUSTOMER_BY_ID);

            webServices.addParameter("id", id);
            webServices.execute();
        }
    }

    public void getCustomersByBusinessName(String businessName)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getCustomersByBusinessName";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_CUSTOMERS_BY_BUSINESS_NAME);

            webServices.addParameter("businessName", businessName);
            webServices.execute();
        }
    }

    public void getCustomersByVendor(int vendorId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getCustomersByVendor";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_CUSTOMERS_BY_VENDOR);

            webServices.addParameter("vendorId", vendorId);
            webServices.execute();
        }
    }

    public void getCustomerDefault()
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getCustomerDefault";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_GET_CUSTOMER_DEFAULT);

            webServices.execute();
        }
    }

    public void findPersonOnlineByDocumentNumber(String id)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "findPersonOnlineByDocumentNumber";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Persons.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_FIND_PERSON_ONLINE);

            webServices.addParameter("id", id);
            webServices.execute();
        }
    }

    public void getProductById(Object id, Object outletId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getProductById";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Products.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_FIND_PRODUCT_BY_ID);

            webServices.addParameter("id", id);
            webServices.addParameter("outletId", outletId);
            webServices.execute();
        }
    }

    public void getProductsByDescription(String description, Object outletId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getProductsByDescription";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Products.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_PRODUCTS_BY_DESCRIPTION);

            webServices.addParameter("description", description);
            webServices.addParameter("outletId", outletId);
            webServices.execute();
        }
    }

    public void getPresentations(Object id, Object outletId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getPresentations";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Products.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_PRESENTATIONS);

            webServices.addParameter("id", id);
            webServices.addParameter("outletId", outletId);
            webServices.execute();
        }
    }

    public void getStockByPresentation(Object presentationId, Object outletId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getStockByPresentation";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Products.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_GET_STOCK_BY_PRESENTATION);

            webServices.addParameter("presentationId", presentationId);
            webServices.addParameter("outletId", outletId);
            webServices.execute();
        }
    }

    public void newCustomer()
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "newCustomer";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_NEW_CUSTOMER);

            webServices.execute();
        }
    }

    public void getBusinessLine()
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getBusinessLine";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_BUSINESS_LINE);

            webServices.execute();
        }
    }

    public void getZones()
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getZones";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_ZONES);

            webServices.execute();
        }
    }

    public void getRoutesByZone(Object id)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getRoutesByZone";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_ROUTES_BY_ZONE);

            webServices.addParameter("id", id);
            webServices.execute();
        }
    }

    public void findVendorByRoute(Object id)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "findVendorByRoute";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_FIND_VENDOR_BY_ROUTE);

            webServices.addParameter("id", id);
            webServices.execute();
        }
    }

    public void insertCustomer(Object id, String documentNumber, String businessName, Object businessLine,
                               String paternalSurname, String maternalSurname, String names,
                               String documentType, String address, String reference, String phone, String email,
                               Object zoneId, Object routeId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "insertCustomer";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_INSERT_CUSTOMER);

            webServices.addParameter("id", id);
            webServices.addParameter("documentNumber", documentNumber);
            webServices.addParameter("businessName", businessName);
            webServices.addParameter("businessLine", businessLine);
            webServices.addParameter("paternalSurname", paternalSurname);
            webServices.addParameter("maternalSurname", maternalSurname);
            webServices.addParameter("names", names);
            webServices.addParameter("documentType", documentType);
            webServices.addParameter("address", address);
            webServices.addParameter("reference", reference);
            webServices.addParameter("phone", phone);
            webServices.addParameter("email", email);
            webServices.addParameter("zoneId", zoneId);
            webServices.addParameter("routeId", routeId);
            webServices.execute();
        }
    }

    public void modifyCustomer(Object id, String documentNumber, String businessName, Object businessLine,
                               String paternalSurname, String maternalSurname, String names,
                               String documentType, String address, String reference, String phone, String email,
                               Object zoneId, Object routeId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "modifyCustomer";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_MODIFY_CUSTOMER);

            webServices.addParameter("id", id);
            webServices.addParameter("documentNumber", documentNumber);
            webServices.addParameter("businessName", businessName);
            webServices.addParameter("businessLine", businessLine);
            webServices.addParameter("paternalSurname", paternalSurname);
            webServices.addParameter("maternalSurname", maternalSurname);
            webServices.addParameter("names", names);
            webServices.addParameter("documentType", documentType);
            webServices.addParameter("address", address);
            webServices.addParameter("reference", reference);
            webServices.addParameter("phone", phone);
            webServices.addParameter("email", email);
            webServices.addParameter("zoneId", zoneId);
            webServices.addParameter("routeId", routeId);
            webServices.execute();
        }
    }

    public void getSheet(String date, Object vendorId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getSheet";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "CollectionSheet.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_COLLECTION_SHEET);

            webServices.addParameter("date", date);
            webServices.addParameter("vendorId", vendorId);
            webServices.execute();
        }
    }

    public void getVendors()
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getVendors";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Sales.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_VENDORS);

            webServices.execute();
        }
    }

    public void getSalesByVendorHeader(String date, Object vendorId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getSalesByVendorHeader";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Sales.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_SALES_BY_VENDOR_HEADER);

            webServices.addParameter("date", date);
            webServices.addParameter("vendorId", vendorId);
            webServices.execute();
        }
    }

    public void getSalesByVendorDetails(String date, Object vendorId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getSalesByVendorDetails";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Sales.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_SALES_BY_VENDOR_DETAILS);

            webServices.addParameter("date", date);
            webServices.addParameter("vendorId", vendorId);
            webServices.execute();
        }
    }

    public void getSaleDetailsBySale(Object id)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getSaleDetailsBySale";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Sales.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_SALE_DETAILS);

            webServices.addParameter("id", id);
            webServices.execute();
        }
    }

    public void getTotalAmountCreditSales(Object vendorId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getTotalAmountCreditSales";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "AccountsReceivable.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_TOTAL_AMOUNT_CREDIT_SALES);

            webServices.addParameter("vendorId", vendorId);
            webServices.execute();
        }
    }

    public void getCreditSales(Object vendorId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getCreditSales";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "AccountsReceivable.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_CREDIT_SALES);

            webServices.addParameter("vendorId", vendorId);
            webServices.execute();
        }
    }

    public void getCreditSaleQuotes(Object creditSaleId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getCreditSaleQuotes";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "AccountsReceivable.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_CREDIT_SALE_QUOTES);

            webServices.addParameter("creditSaleId", creditSaleId);
            webServices.execute();
        }
    }

    public void getCreditSalesPendingByCustomer(Object customerId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "getCreditSalesPendingByCustomer";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "Customers.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_LIST_CREDIT_SALES_PENDING_BY_CUSTOMER);

            webServices.addParameter("customerId", customerId);
            webServices.execute();
        }
    }

    public void newCreditSaleQuote(Object creditSaleId)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "newCreditSaleQuote";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "AccountsReceivable.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_NEW_CREDIT_SALE_QUOTE);

            webServices.addParameter("creditSaleId", creditSaleId);
            webServices.execute();
        }
    }

    public void saveCreditSaleQuote(Object creditSaleId, int creditSaleQuoteNumber, Object customerId,
                                    float payment, String amortizationDate)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "saveCreditSaleQuote";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "AccountsReceivable.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_SAVE_CREDIT_SALE_QUOTE);

            webServices.addParameter("creditSaleId", creditSaleId);
            webServices.addParameter("creditSaleQuoteNumber", creditSaleQuoteNumber);
            webServices.addParameter("customerId", customerId);
            webServices.addParameter("payment", String.format("%s", payment));
            webServices.addParameter("amortizationDate", amortizationDate);
            webServices.execute();
        }
    }

    public void deleteCreditSaleQuote(Object creditSaleId, int creditSaleQuoteNumber)
    {
        if (config != null && !config.getServer().equals(""))
        {
            final String methodName = "deleteCreditSaleQuote";

            WebServices webServices = new WebServices(config.getNamespace(),
                    String.format("http://%s/%s", config.getServer(), "AccountsReceivable.asmx"), methodName,
                    String.format("%s%s", config.getNamespace(), methodName),
                    this.context, this.listener,
                    TYPE_DELETE_CREDIT_SALE_QUOTE);

            webServices.addParameter("creditSaleId", creditSaleId);
            webServices.addParameter("creditSaleQuoteNumber", creditSaleQuoteNumber);
            webServices.execute();
        }
    }
}
