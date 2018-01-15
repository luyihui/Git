package Order.repository;

import Order.domain.Order;
import Order.domain.OrderStatus;
import repository.OrderRepository;

import java.io.*;
import java.util.UUID;

public class OrderRepositoryFileImp implements OrderRepository{
    File filename = new File("C:/Users/luyih/IdeaProjects/DmallDemo/orders.txt");
    @Override
    public void SaveOrder(Order o){
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
    public Order QueryOrder(UUID Id, OrderStatus orderStatus) {
        try {
            FileInputStream in = new FileInputStream(filename);
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
            BufferedReader bufReader = new BufferedReader(inReader);
            String output = null;
            int i = 1;
            while((output = bufReader.readLine()) != null){
                if(output.contains(Id.toString()) && output.contains(orderStatus.toString())){
                    break;
                }
                i++;
            }
            bufReader.close();
            inReader.close();
            in.close();
            Order o = new Order(output);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取" + filename + "出错！");
        }
        return null;
    }
}
