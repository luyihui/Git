package test;

import User.repository.UserRepositoryFileImp;
import User.service.UserService;
import User.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTest {
    UserRepositoryFileImp userRepositoryFileImp = new UserRepositoryFileImp();
    UserService userService = new UserService();

    @Test
    public void user_should_be_same_as_created(){
        User newUser = userService.createUser();
        userRepositoryFileImp.SaveUser(newUser);
        User actualUser = userRepositoryFileImp.QueryUser(newUser.getUserId());

        assertEquals(newUser.getUserId(),actualUser.getUserId());
        assertEquals(newUser.toString(),actualUser.toString());
    }

}
