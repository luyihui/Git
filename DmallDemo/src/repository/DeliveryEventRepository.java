package repository;

import Order.event.DeliveryEvent;

import java.util.UUID;

public interface DeliveryEventRepository {
    void SaveDeliveryEvent(DeliveryEvent o);
    DeliveryEvent QueryPaymentEvent(UUID Id);
}
