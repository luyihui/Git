package Order.service;

import Order.domain.*;
import Order.event.DeliveryEvent;
import Order.event.ModifyOrderEvent;
import Order.event.PaymentEvent;
import Order.repository.OrderRepositoryFileImp;
import User.domain.User;
import User.repository.UserRepositoryFileImp;
import User.service.UserService;

import java.time.ZonedDateTime;

public class OrderService {
    private OrderRepositoryFileImp orders = new OrderRepositoryFileImp();
    private UserRepositoryFileImp userRepositoryFileImp = new UserRepositoryFileImp();
    UserService userService = new UserService();
    public Order CreateOrder(){
        Product product = new Product("领域驱动设计", new Price(89.8,"人民币"), 2);
        User user = userService.createUser();
        Customer customer = new Customer(1,"Joy");
        CargoReceiver cargoreceiver = new CargoReceiver(user.getUserName(),user.getUserAddress(),user.getUserTel());
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

    public void onModifyCargoInfo(ModifyOrderEvent modifyOrderEvent){
        Order deliveryOrder = null;
        if (orders.QueryOrder(modifyOrderEvent.getOrderId(),OrderStatus.Deliveried) != null){
            deliveryOrder = orders.QueryOrder(modifyOrderEvent.getOrderId(),OrderStatus.Deliveried);
        }
        else if (orders.QueryOrder(modifyOrderEvent.getOrderId(),OrderStatus.Paied) != null){
            deliveryOrder = orders.QueryOrder(modifyOrderEvent.getOrderId(),OrderStatus.Paied);
        }
        else if (orders.QueryOrder(modifyOrderEvent.getOrderId(),OrderStatus.Ini) != null){
            deliveryOrder = orders.QueryOrder(modifyOrderEvent.getOrderId(),OrderStatus.Ini);
        }

        if (deliveryOrder != null){
            userService.modifyUserAddressInfo(modifyOrderEvent.getUserId(),modifyOrderEvent.getAddress());
            deliveryOrder.onModifyCargo(modifyOrderEvent);
            orders.SaveOrder(deliveryOrder);
        }
        else {
            System.out.println("Cannot find the order!");
        }
    }

}
