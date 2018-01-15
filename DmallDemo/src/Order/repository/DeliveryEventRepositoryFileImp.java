package Order.repository;

import Order.event.DeliveryEvent;
import repository.DeliveryEventRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class DeliveryEventRepositoryFileImp implements DeliveryEventRepository {
    File filename = new File("C:/Users/luyih/IdeaProjects/DmallDemo/deliveryEvent.txt");
    @Override
    public void SaveDeliveryEvent(DeliveryEvent o) {
        if(!filename.exists()){
            try {
                filename.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String orderoutput = o.toString()+"\r\n";
        try {
            FileOutputStream fos = new FileOutputStream(filename,true);
            fos.write(orderoutput.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DeliveryEvent QueryPaymentEvent(UUID Id) {
        return null;
    }
}
