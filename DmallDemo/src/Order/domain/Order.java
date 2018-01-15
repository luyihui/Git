package Order.domain;

import Order.event.DeliveryEvent;
import Order.event.PaymentEvent;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

public class Order {
    private UUID Id;
    private Customer customer;
    private CargoReceiver cargoReceiver;
    private OrderItem orderItem;
    private DeliveryInfo deliveryInfo;
    private Payment payment;
    private OrderStatus orderStatus;

    public Order(Customer customer, CargoReceiver cargoReceiver, OrderItem orderItem, DeliveryInfo deliveryInfo, Payment payment) {
        this.orderItem = orderItem;
        this.deliveryInfo = deliveryInfo;
        this.payment = payment;
        this.orderStatus = OrderStatus.Ini;
        this.Id = UUID.randomUUID();
        this.customer = customer;
        this.cargoReceiver = cargoReceiver;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public Order(String s) {
        String[] sArray = s.split(",");
        int i = 0;
        this.Id = UUID.fromString(sArray[i++]);
        this.customer = new Customer(Integer.parseInt(sArray[i++]),sArray[i++]);
        this.cargoReceiver = new CargoReceiver(sArray[i++],
                new Address(sArray[i++],sArray[i++],sArray[i++],sArray[i++],sArray[i++],sArray[i++]),
                Integer.parseInt(sArray[i++]));
        this.orderItem = new OrderItem(
                new Product(sArray[i++],
                        new Price(Double.parseDouble(sArray[i++]),sArray[i++]),
                        Integer.parseInt(sArray[i++])),
                Integer.parseInt(sArray[i++]));
        this.deliveryInfo = new DeliveryInfo(Enum.valueOf(DeliveryMethod.class,sArray[i++].trim()), ZonedDateTime.parse(sArray[i++]));
        if (!(sArray[sArray.length-1].equals("Ini"))){
            this.payment = new Payment(UUID.fromString(sArray[i++]),Enum.valueOf(PaymentMethod.class,sArray[i++].trim()));
        }
        else{

        }
        this.orderStatus = Enum.valueOf(OrderStatus.class,sArray[i++].trim());

    }

    public UUID getId() {
        return Id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CargoReceiver getCargoReceiver() {
        return cargoReceiver;
    }

    public String toString(){
        if (this.orderStatus != OrderStatus.Ini) {
            return this.getId().toString() + "," +
                    this.customer.getId() + "," +
                    this.customer.getName() + "," +
                    this.cargoReceiver.getName() + "," +
                    this.cargoReceiver.getAddress().getCountry() + "," +
                    this.cargoReceiver.getAddress().getProvince() + "," +
                    this.cargoReceiver.getAddress().getCity() + "," +
                    this.cargoReceiver.getAddress().getDistrict() + "," +
                    this.cargoReceiver.getAddress().getRoad() + "," +
                    this.cargoReceiver.getAddress().getBuilding() + "," +
                    this.cargoReceiver.getTelnumber() + "," +
                    this.orderItem.getProduct().getName() + "," +
                    this.orderItem.getProduct().getPrice().getAmnt() + "," +
                    this.orderItem.getProduct().getPrice().getCurrency() + "," +
                    this.orderItem.getProduct().getId() + "," +
                    this.orderItem.getAmnt() + "," +
                    this.deliveryInfo.getDeliveryMethod() + "," +
                    this.deliveryInfo.getDeliveryDate() + "," +
                    this.payment.getPaymentId().toString() + "," +
                    this.payment.getPaymentMethod() + "," +
                    this.orderStatus.toString();
        }
        else{
            return this.getId().toString()+","+
                    this.customer.getId()+ ","+
                    this.customer.getName()+","+
                    this.cargoReceiver.getName()+ ","+
                    this.cargoReceiver.getAddress().getCountry()+","+
                    this.cargoReceiver.getAddress().getProvince()+","+
                    this.cargoReceiver.getAddress().getCity()+","+
                    this.cargoReceiver.getAddress().getDistrict()+","+
                    this.cargoReceiver.getAddress().getRoad()+","+
                    this.cargoReceiver.getAddress().getBuilding()+ ","+
                    this.cargoReceiver.getTelnumber()+ ","+
                    this.orderItem.getProduct().getName()+ ","+
                    this.orderItem.getProduct().getPrice().getAmnt()+ ","+
                    this.orderItem.getProduct().getPrice().getCurrency()+ ","+
                    this.orderItem.getProduct().getId()+ ","+
                    this.orderItem.getAmnt()+ ","+
                    this.deliveryInfo.getDeliveryMethod()+ ","+
                    this.deliveryInfo.getDeliveryDate()+ ","+
                    this.orderStatus.toString();
        }
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void onPay(PaymentEvent paymentEvent) {
        this.orderStatus = OrderStatus.Paied;
        this.payment = paymentEvent.getPayment();
    }

    public void onDelivery(DeliveryEvent deliveryEvent) {
        this.orderStatus = OrderStatus.Deliveried;
    }
}
