package Order.repository;

import Order.event.PaymentEvent;
import repository.PaymentEventRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class PaymentEventRepositoryFileImp implements PaymentEventRepository {
    File filename = new File("C:/Users/luyih/IdeaProjects/DmallDemo/paymentEvent.txt");
    @Override
    public void SavePaymentEvent(PaymentEvent o) {
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
    public PaymentEvent QueryPaymentEvent(UUID Id) {
        return null;
    }
}
