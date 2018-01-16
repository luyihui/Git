package User.repository;

import User.domain.User;
import repository.UserRepository;

import java.io.*;

public class UserRepositoryFileImp implements UserRepository {
    File filename = new File("C:/Users/luyih/IdeaProjects/DmallDemo/userInfo.txt");
    @Override
    public void SaveUser(User user) {
        if(!filename.exists()){
            try {
                filename.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String useroutput = user.toString()+"\r\n";
        //System.out.println(useroutput);
        try {
            FileOutputStream fos = new FileOutputStream(filename,true);
            fos.write(useroutput.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User QueryUser(int UserId) {
        try {
            FileInputStream in = new FileInputStream(filename);
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
            BufferedReader bufReader = new BufferedReader(inReader);
            String output = null;
            int i = 1;
            while((output = bufReader.readLine()) != null){
                if(output.contains(String.valueOf(UserId))){
                    break;
                }
                i++;
            }
            bufReader.close();
            inReader.close();
            in.close();
            User o = new User(output);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取" + filename + "出错！");
        }
        return null;
    }
}
