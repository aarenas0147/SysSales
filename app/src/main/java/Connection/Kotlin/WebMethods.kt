package Connection.Kotlin

import android.content.Context

class WebMethods(private val context: Context, private val listener: WebServices.OnResult) {

    //Variables:
    private val config: WebConfig?
    
    fun findUser(user: String?, password: String?) {
        if (config != null && config.server != "") {
            val methodName = "findUser"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Login.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_USER)
            webServices.addParameter("user", user)
            webServices.addParameter("password", password)
            webServices.execute()
        }
    }

    fun getNotifications() {
        if (config != null && config.server != "") {
            val methodName = "getNotifications"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Main.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_NOTIFICATIONS)
            webServices.execute()
        }    
    }
    
    fun getConfiguration() {
        if (config != null && config.server != "") {
            val methodName = "getConfiguration"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Main.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_CONFIGURATIONS)
            webServices.execute()
        }
    }

    fun getConfigurationXUser(userId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getConfigurationXUser"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Main.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_CONFIGURATIONS_X_USER)
            webServices.addParameter("userId", userId)
            webServices.execute()
        }
    }

    fun getCompany() {
        if (config != null && config.server != "") {
            val methodName = "getCompany"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Main.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_PRINCIPAL_COMPANY)
            webServices.execute()
        }
    }

    fun openSale(personId: Int, userId: Int) {
        if (config != null && config.server != "") {
            val methodName = "openSale"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_OPEN_SALE)
            webServices.addParameter("personId", personId)
            webServices.addParameter("userId", userId)
            webServices.execute()
        }
    }

    fun getVoucherTypes() {
        if (config != null && config.server != "") {
            val methodName = "getVoucherTypes"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_VOUCHER_TYPES)
            webServices.execute()
        }
    }
    
    fun getPaymentConditions() {
        if (config != null && config.server != "") {
            val methodName = "getPaymentConditions"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_PAYMENT_CONDITIONS)
            webServices.execute()
        }
    }

    fun getPaymentMethods() {
        if (config != null && config.server != "") {
            val methodName = "getPaymentMethods"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_PAYMENT_METHODS)
            webServices.execute()
        }
    }

    fun getPaymentMethodsDetailBySale(id: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getPaymentMethodsDetailBySale"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_PAYMENT_METHODS_BY_SALE)
            webServices.addParameter("id", id)
            webServices.execute()
        }
    }

    fun addPaymentMethodBySale(id: Any?, paymentMethod: Any?, amount: Float) {
        if (config != null && config.server != "") {
            val methodName = "addPaymentMethodBySale"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_ADD_PAYMENT_METHOD_BY_SALE)
            webServices.addParameter("id", id)
            webServices.addParameter("paymentMethod", paymentMethod)
            webServices.addParameter("amount", String.format("%s", amount))
            webServices.execute()
        }
    }

    fun modifyPaymentMethodBySale(id: Any?, paymentMethod: Any?, amount: Float) {
        if (config != null && config.server != "") {
            val methodName = "modifyPaymentMethodBySale"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_MODIFY_PAYMENT_METHOD_BY_SALE)
            webServices.addParameter("id", id)
            webServices.addParameter("paymentMethod", paymentMethod)
            webServices.addParameter("amount", String.format("%s", amount))
            webServices.execute()
        }
    }

    fun deletePaymentMethodBySale(id: Any?, paymentMethod: Any?) {
        if (config != null && config.server != "") {
            val methodName = "deletePaymentMethodBySale"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_DELETE_PAYMENT_METHOD_BY_SALE)
            webServices.addParameter("id", id)
            webServices.addParameter("paymentMethod", paymentMethod)
            webServices.execute()
        }
    }

    fun getTempSaleHeader(id: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getTempSaleHeader"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_TEMP_SALE_HEADER)
            webServices.addParameter("id", id)
            webServices.execute()
        }
    }

    fun getTempSaleDetails(id: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getTempSaleDetails"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_TEMP_SALE_DETAILS)
            webServices.addParameter("id", id)
            webServices.execute()
        }
    }

    fun addItem(id: Any?, productId: Any?, personId: Int, voucherTypeId: String?, paymentCondition: Any?, quantity: Float, price: Float) {
        if (config != null && config.server != "") {
            val methodName = "addItem"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_ADD_ITEM_SALE)
            webServices.addParameter("id", id)
            webServices.addParameter("productId", productId)
            webServices.addParameter("personId", personId)
            webServices.addParameter("voucherTypeId", voucherTypeId)
            webServices.addParameter("paymentCondition", paymentCondition)
            webServices.addParameter("quantity", String.format("%s", quantity))
            webServices.addParameter("price", String.format("%s", price))
            webServices.execute()
        }
    }

    fun modifyItem(id: Any?, productId: Any?, personId: Int, voucherTypeId: String?, paymentCondition: Any?,
                   quantity: Float, newQuantity: Float, price: Float) {
        if (config != null && config.server != "") {
            val methodName = "modifyItem"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_MODIFY_ITEM_SALE)
            webServices.addParameter("id", id)
            webServices.addParameter("productId", productId)
            webServices.addParameter("personId", personId)
            webServices.addParameter("voucherTypeId", voucherTypeId)
            webServices.addParameter("paymentCondition", paymentCondition)
            webServices.addParameter("quantity", String.format("%s", quantity))
            webServices.addParameter("newQuantity", String.format("%s", newQuantity))
            webServices.addParameter("price", String.format("%s", price))
            webServices.execute()
        }
    }

    fun deleteItem(id: Any?, productId: Any?, voucherTypeId: String?, quantity: Float) {
        if (config != null && config.server != "") {
            val methodName = "deleteItem"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_DELETE_ITEM_SALE)
            webServices.addParameter("id", id)
            webServices.addParameter("productId", productId)
            webServices.addParameter("voucherTypeId", voucherTypeId)
            webServices.addParameter("quantity", String.format("%s", quantity))
            webServices.execute()
        }
    }

    fun saveSale(id: Any?, voucherTypeId: String?, paymentCondition: Any?, paymentMethod: Any?, issueDate: String?, expirationDate: String?,
                 currentDateTime: String?, customerId: String?, personId: Int, userId: Int) {
        if (config != null && config.server != "") {
            val methodName = "saveSale"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "NewSale.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_SAVE_SALE)
            webServices.addParameter("id", id)
            webServices.addParameter("voucherTypeId", voucherTypeId)
            webServices.addParameter("paymentCondition", paymentCondition)
            webServices.addParameter("paymentMethod", paymentMethod)
            webServices.addParameter("issueDate", issueDate)
            webServices.addParameter("expirationDate", expirationDate)
            webServices.addParameter("currentDateTime", currentDateTime)
            webServices.addParameter("customerId", customerId)
            webServices.addParameter("personId", personId)
            webServices.addParameter("userId", userId)
            webServices.execute()
        }
    }

    fun cancelSale(saleId: Any?, date: String?, vendorId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "cancelSale"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Sales.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_CANCEL_SALE)
            webServices.addParameter("saleId", saleId)
            webServices.addParameter("date", date)
            webServices.addParameter("vendorId", vendorId)
            webServices.execute()
        }
    }

    fun getCustomers() {
        if (config != null && config.server != "") {
            val methodName = "getCustomers"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Customers.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_CUSTOMERS)
            webServices.execute()
        }
    }

    fun getCustomerById(id: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getCustomerById"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Customers.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_FIND_CUSTOMER_BY_ID)
            webServices.addParameter("id", id)
            webServices.execute()
        }
    }

    fun getCustomersByBusinessName(businessName: String?) {
        if (config != null && config.server != "") {
            val methodName = "getCustomersByBusinessName"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Customers.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_CUSTOMERS_BY_BUSINESS_NAME)
            webServices.addParameter("businessName", businessName)
            webServices.execute()
        }
    }

    fun getCustomersByVendor(vendorId: Int) {
        if (config != null && config.server != "") {
            val methodName = "getCustomersByVendor"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Customers.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_CUSTOMERS_BY_VENDOR)
            webServices.addParameter("vendorId", vendorId)
            webServices.execute()
        }
    }

    fun getCustomerDefault() {
        if (config != null && config.server != "") {
            val methodName = "getCustomerDefault"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Customers.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_GET_CUSTOMER_DEFAULT)
            webServices.execute()
        }
    }

    fun findPersonOnlineByDocumentNumber(id: String?) {
        if (config != null && config.server != "") {
            val methodName = "findPersonOnlineByDocumentNumber"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Persons.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_FIND_PERSON_ONLINE)
            webServices.addParameter("id", id)
            webServices.execute()
        }
    }

    fun getProductById(id: Any?, outletId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getProductById"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Products.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_FIND_PRODUCT_BY_ID)
            webServices.addParameter("id", id)
            webServices.addParameter("outletId", outletId)
            webServices.execute()
        }
    }

    fun getProductsByDescription(description: String?, outletId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getProductsByDescription"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Products.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_PRODUCTS_BY_DESCRIPTION)
            webServices.addParameter("description", description)
            webServices.addParameter("outletId", outletId)
            webServices.execute()
        }
    }

    fun getPresentations(id: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getPresentations"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Products.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_PRESENTATIONS)
            webServices.addParameter("id", id)
            webServices.execute()
        }
    }

    fun getStockByPresentation(presentationId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getStockByPresentation"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Products.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_GET_STOCK_BY_PRESENTATION)
            webServices.addParameter("presentationId", presentationId)
            webServices.execute()
        }
    }

    fun newCustomer() {
        if (config != null && config.server != "") {
            val methodName = "newCustomer"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Customers.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_NEW_CUSTOMER)
            webServices.execute()
        }
    }

    fun getBusinessLine() {
        if (config != null && config.server != "") {
            val methodName = "getBusinessLine"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Customers.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_BUSINESS_LINE)
            webServices.execute()
        }
    }

    fun getZones() {
        if (config != null && config.server != "") {
            val methodName = "getZones"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Customers.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_ZONES)
            webServices.execute()
        }
    }

    fun getRoutesByZone(id: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getRoutesByZone"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Customers.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_ROUTES_BY_ZONE)
            webServices.addParameter("id", id)
            webServices.execute()
        }
    }

    fun findVendorByRoute(id: Any?) {
        if (config != null && config.server != "") {
            val methodName = "findVendorByRoute"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Customers.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_FIND_VENDOR_BY_ROUTE)
            webServices.addParameter("id", id)
            webServices.execute()
        }
    }

    fun insertCustomer(id: Any?, documentNumber: String?, businessName: String?, businessLine: String?,
                       paternalSurname: String?, maternalSurname: String?, names: String?,
                       documentType: String?, address: String?, reference: String?, phone: String?, email: String?,
                       zoneId: Any?, routeId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "insertCustomer"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Customers.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_INSERT_CUSTOMER)
            webServices.addParameter("id", id)
            webServices.addParameter("documentNumber", documentNumber)
            webServices.addParameter("businessName", businessName)
            webServices.addParameter("businessLine", businessLine)
            webServices.addParameter("paternalSurname", paternalSurname)
            webServices.addParameter("maternalSurname", maternalSurname)
            webServices.addParameter("names", names)
            webServices.addParameter("documentType", documentType)
            webServices.addParameter("address", address)
            webServices.addParameter("reference", reference)
            webServices.addParameter("phone", phone)
            webServices.addParameter("email", email)
            webServices.addParameter("zoneId", zoneId)
            webServices.addParameter("routeId", routeId)
            webServices.execute()
        }
    }

    fun modifyCustomer(id: Any?, documentNumber: String?, businessName: String?, businessLine: String?,
                       paternalSurname: String?, maternalSurname: String?, names: String?,
                       documentType: String?, address: String?, reference: String?, phone: String?, email: String?,
                       zoneId: Any?, routeId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "modifyCustomer"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Customers.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_MODIFY_CUSTOMER)
            webServices.addParameter("id", id)
            webServices.addParameter("documentNumber", documentNumber)
            webServices.addParameter("businessName", businessName)
            webServices.addParameter("businessLine", businessLine)
            webServices.addParameter("paternalSurname", paternalSurname)
            webServices.addParameter("maternalSurname", maternalSurname)
            webServices.addParameter("names", names)
            webServices.addParameter("documentType", documentType)
            webServices.addParameter("address", address)
            webServices.addParameter("reference", reference)
            webServices.addParameter("phone", phone)
            webServices.addParameter("email", email)
            webServices.addParameter("zoneId", zoneId)
            webServices.addParameter("routeId", routeId)
            webServices.execute()
        }
    }

    fun getSheet(date: String?, vendorId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getSheet"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "CollectionSheet.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_COLLECTION_SHEET)
            webServices.addParameter("date", date)
            webServices.addParameter("vendorId", vendorId)
            webServices.execute()
        }
    }

    fun getVendors() {
        if (config != null && config.server != "") {
            val methodName = "getVendors"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Sales.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_VENDORS)
            webServices.execute()
        }
    }

    fun getSalesByVendorHeader(date: String?, vendorId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getSalesByVendorHeader"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Sales.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_SALES_BY_VENDOR_HEADER)
            webServices.addParameter("date", date)
            webServices.addParameter("vendorId", vendorId)
            webServices.execute()
        }
    }

    fun getSalesByVendorDetails(date: String?, vendorId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getSalesByVendorDetails"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Sales.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_SALES_BY_VENDOR_DETAILS)
            webServices.addParameter("date", date)
            webServices.addParameter("vendorId", vendorId)
            webServices.execute()
        }
    }

    fun getSaleInPDF(saleId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getSaleInPDF"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Sales.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_GET_SALE_IN_PDF)
            webServices.addParameter("saleId", saleId)
            webServices.execute()
        }
    }

    fun getSaleDetailsBySale(saleId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getSaleDetailsBySale"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "Sales.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_SALE_DETAILS)
            webServices.addParameter("saleId", saleId)
            webServices.execute()
        }
    }

    fun getTotalAmountCreditSales(vendorId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getTotalAmountCreditSales"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "AccountsReceivable.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_TOTAL_AMOUNT_CREDIT_SALES)
            webServices.addParameter("vendorId", vendorId)
            webServices.execute()
        }
    }

    fun getCreditSales(vendorId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getCreditSales"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "AccountsReceivable.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_CREDIT_SALES)
            webServices.addParameter("vendorId", vendorId)
            webServices.execute()
        }
    }

    fun getCreditSaleQuotes(creditSaleId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "getCreditSaleQuotes"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "AccountsReceivable.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_LIST_CREDIT_SALE_QUOTES)
            webServices.addParameter("creditSaleId", creditSaleId)
            webServices.execute()
        }
    }

    fun newCreditSaleQuote(creditSaleId: Any?) {
        if (config != null && config.server != "") {
            val methodName = "newCreditSaleQuote"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "AccountsReceivable.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_NEW_CREDIT_SALE_QUOTE)
            webServices.addParameter("creditSaleId", creditSaleId)
            webServices.execute()
        }
    }

    fun saveCreditSaleQuote(creditSaleId: Any?, creditSaleQuoteNumber: Int, customerId: Any?,
                            payment: Float, amortizationDate: String?) {
        if (config != null && config.server != "") {
            val methodName = "saveCreditSaleQuote"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "AccountsReceivable.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_SAVE_CREDIT_SALE_QUOTE)
            webServices.addParameter("creditSaleId", creditSaleId)
            webServices.addParameter("creditSaleQuoteNumber", creditSaleQuoteNumber)
            webServices.addParameter("customerId", customerId)
            webServices.addParameter("payment", String.format("%s", payment))
            webServices.addParameter("amortizationDate", amortizationDate)
            webServices.execute()
        }
    }

    fun deleteCreditSaleQuote(creditSaleId: Any?, creditSaleQuoteNumber: Int) {
        if (config != null && config.server != "") {
            val methodName = "deleteCreditSaleQuote"
            val webServices = WebServices(config.namespace, String.format("http://%s/%s", config.server, "AccountsReceivable.asmx"),
                    methodName, String.format("%s%s", config.namespace, methodName),
                    context, listener,
                    TYPE_DELETE_CREDIT_SALE_QUOTE)
            webServices.addParameter("creditSaleId", creditSaleId)
            webServices.addParameter("creditSaleQuoteNumber", creditSaleQuoteNumber)
            webServices.execute()
        }
    }

    companion object {
        //Constants:
        const val TYPE_LIST_USER = 1
        const val TYPE_LIST_CONFIGURATIONS = 2
        const val TYPE_LIST_CONFIGURATIONS_X_USER = 3
        const val TYPE_LIST_NOTIFICATIONS = 4
        const val TYPE_LIST_PRINCIPAL_COMPANY = 5
        const val TYPE_OPEN_SALE = 6
        const val TYPE_LIST_VOUCHER_TYPES = 7
        const val TYPE_LIST_PAYMENT_CONDITIONS = 8
        const val TYPE_LIST_PAYMENT_METHODS = 9
        const val TYPE_LIST_PAYMENT_METHODS_BY_SALE = 10
        const val TYPE_ADD_PAYMENT_METHOD_BY_SALE = 11
        const val TYPE_MODIFY_PAYMENT_METHOD_BY_SALE = 12
        const val TYPE_DELETE_PAYMENT_METHOD_BY_SALE = 13
        const val TYPE_LIST_TEMP_SALE_HEADER = 14
        const val TYPE_LIST_TEMP_SALE_DETAILS = 15
        const val TYPE_ADD_ITEM_SALE = 16
        const val TYPE_MODIFY_ITEM_SALE = 17
        const val TYPE_DELETE_ITEM_SALE = 18
        const val TYPE_SAVE_SALE = 19
        const val TYPE_CANCEL_SALE = 20
        const val TYPE_FIND_CUSTOMER_BY_ID = 21
        const val TYPE_LIST_CUSTOMERS = 22
        const val TYPE_LIST_CUSTOMERS_BY_BUSINESS_NAME = 23
        const val TYPE_LIST_CUSTOMERS_BY_VENDOR = 24
        const val TYPE_GET_CUSTOMER_DEFAULT = 25
        const val TYPE_FIND_PERSON_ONLINE = 26
        const val TYPE_FIND_PRODUCT_BY_ID = 27
        const val TYPE_LIST_PRODUCTS_BY_DESCRIPTION = 28
        const val TYPE_LIST_PRESENTATIONS = 29
        const val TYPE_GET_STOCK_BY_PRESENTATION = 30
        const val TYPE_NEW_CUSTOMER = 31
        const val TYPE_LIST_BUSINESS_LINE = 32
        const val TYPE_LIST_ZONES = 33
        const val TYPE_LIST_ROUTES_BY_ZONE = 34
        const val TYPE_FIND_VENDOR_BY_ROUTE = 35
        const val TYPE_INSERT_CUSTOMER = 36
        const val TYPE_MODIFY_CUSTOMER = 37
        const val TYPE_LIST_COLLECTION_SHEET = 38
        const val TYPE_LIST_VENDORS = 39
        const val TYPE_LIST_SALES_BY_VENDOR_HEADER = 40
        const val TYPE_LIST_SALES_BY_VENDOR_DETAILS = 41
        const val TYPE_GET_SALE_IN_PDF = 42
        const val TYPE_LIST_SALE_DETAILS = 43
        const val TYPE_TOTAL_AMOUNT_CREDIT_SALES = 44
        const val TYPE_LIST_CREDIT_SALES = 45
        const val TYPE_LIST_CREDIT_SALE_QUOTES = 46
        const val TYPE_NEW_CREDIT_SALE_QUOTE = 47
        const val TYPE_SAVE_CREDIT_SALE_QUOTE = 48
        const val TYPE_DELETE_CREDIT_SALE_QUOTE = 49
    }

    init {
        config = WebConfig(context)
    }
}