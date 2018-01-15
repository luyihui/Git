package Order.service;

import Order.domain.*;
import Order.event.DeliveryEvent;
import Order.event.PaymentEvent;
import Order.repository.OrderRepositoryFileImp;

import java.time.ZonedDateTime;

public class OrderService {
    private OrderRepositoryFileImp orders = new OrderRepositoryFileImp();
    public Order CreateOrder(){
        Product product = new Product("领域驱动设计", new Price(89.8,"人民币"), 2);
        Customer customer = new Customer(1,"Joy");
        Address address = new Address("China","Shanghai","Shanghai","Pudong","Zhangdong","1588");
        CargoReceiver cargoreceiver = new CargoReceiver("Jxj", address, 110);
        OrderItem orderItem = new OrderItem(product,2);
        DeliveryInfo deliveryInfo = new DeliveryInfo(DeliveryMethod.EMS, ZonedDateTime.now());
        Payment payment = new Payment(PaymentMethod.Alipay);
        Order order = new Order(customer,cargoreceiver, orderItem, deliveryInfo, payment);
       // orders.SaveOrder(order);
        return order;
    }
    public Order CreateOrderByString(){
        String s = "10d7dd28-d222-4524-9ff6-d0fabc03eab9,1,Joy,Jxj,China,Shanghai,Shanghai,Pudong,Zhangdong,1588,120,实现领域驱动设计,89.8,USD,2,2,EMS,2018-01-14T11:55:28.275009+08:00[Asia/Shanghai],Ini";
        Order order = new Order(s);
        return order;
    }

    public Order onPay(PaymentEvent paymentEvent){
        Order paiedOrder = orders.QueryOrder(paymentEvent.getOrderId(),OrderStatus.Ini);
        paiedOrder.onPay(paymentEvent);
        return paiedOrder;
    }

    public Order onDelivery(DeliveryEvent deliveryEvent){
        Order deliveryOrder = orders.QueryOrder(deliveryEvent.getOrderId(),OrderStatus.Paied);
        deliveryOrder.onDelivery(deliveryEvent);
        return deliveryOrder;
    }

}
