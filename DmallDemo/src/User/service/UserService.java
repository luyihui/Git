package User.service;

import Order.domain.Address;
import User.domain.Gender;
import User.domain.User;
import User.repository.UserRepositoryFileImp;

public class UserService {
    private UserRepositoryFileImp userRepositoryFileImp = new UserRepositoryFileImp();
    public User createUser() {
        Address address = new Address("China","Shanghai","Shanghai","Pudong","Zhangdong","1588");
        User user = new User(1000,"lyh",3776, Gender.Male,address);
        return user;
    }

    public void modifyUserAddressInfo(int userId,Address address){
        User user = userRepositoryFileImp.QueryUser(userId);
        user.setUserAddress(address);
        userRepositoryFileImp.SaveUser(user);
    }
}
