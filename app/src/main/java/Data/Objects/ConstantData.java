package Data.Objects;

public class ConstantData {

    public static class CustomerStatus {

        /** No se ha definido el estado del cliente */
        public static final int UNDEFINED = 0;
        /** El cliente se encuentra en plena actividad y relación con la empresa. Realiza transacciones, compras o utiliza los servicios de manera regular y continua. */
        public static final int ACTIVE = 1;
        /** El cliente no ha realizado transacciones, compras o utilizado servicios en un período significativo. La relación activa con la empresa se ha detenido. */
        public static final int INACTIVE = 2;
        /** Para clientes que están en proceso de registro, activación o algún otro procedimiento administrativo. */
        public static final int IN_PROCESS = 3;
        /** Para clientes que han sido suspendidos temporalmente debido a algún motivo, como infracciones de políticas, comportamiento inapropiado, etc. */
        public static final int SUSPENDED = 4;
        /** Indicando que el cliente ha solicitado la cancelación de su cuenta o contrato con tu empresa. */
        public static final int CANCELLED = 5;
        /** Para clientes cuyas cuentas han sido bloqueadas temporalmente debido a razones de seguridad u otras cuestiones. */
        public static final int BLOCKED = 6;
        /** Para clientes que han cerrado permanentemente su cuenta o contrato. */
        public static final int CLOSED = 7;
        /** Para clientes cuyas cuentas están en espera debido a problemas técnicos u otras razones, pero no necesariamente por falta de pago. */
        public static final int ON_HOLD = 8;
        /** Para clientes cuyas solicitudes han sido rechazadas por algún motivo, como problemas de verificación, crédito insuficiente, etc. */
        public static final int REJECTED = 9;
        /** Indica que el cliente tiene pagos vencidos o pendientes por un período prolongado. */
        public static final int DELINQUENT = 10;
    }

    public static class PaymentCondition {

        /** El cliente realiza el pago de la totalidad del importe en el momento de la compra o de la recepción de los bienes o servicios. */
        public static final int CASH = 1;
        /** El cliente realiza el pago posteriormente, en un plazo acordado por el vendedor, tras recibir los bienes o servicios. */
        public static final int CREDIT = 2;
        /** El cliente paga en el momento de recibir los bienes o servicios. */
        public static final int CASH_ON_DELIVERY = 3;
        /** El cliente realiza el pago por adelantado antes de recibir los bienes o servicios. */
        public static final int CASH_IN_ADVANCE = 4;
        /** El cliente puede optar por realizar pagos parciales en cuotas a lo largo de un período acordado. */
        public static final int PARTIAL_PAYMENT = 5;
        /** El cliente utiliza letras de cambio o pagarés como compromisos de pago. */
        public static final int LETTER_OF_CREDIT = 6;
        /** El cliente puede optar por financiamiento externo a través de instituciones financieras, que les permiten realizar el pago en cuotas a lo largo del tiempo. */
        public static final int BANK_FINANCING = 7;
        /** El cliente realiza pago personalizado */
        public static final int CUSTOMIZED = 8;
    }
}
