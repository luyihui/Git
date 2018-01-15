package repository;

import Order.event.PaymentEvent;

import java.util.UUID;

public interface PaymentEventRepository {
    void SavePaymentEvent(PaymentEvent o);
    PaymentEvent QueryPaymentEvent(UUID Id);
}
