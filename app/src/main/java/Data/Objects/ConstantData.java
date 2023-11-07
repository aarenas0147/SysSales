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
}
