import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.aydar.petstore.object.UserObject;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;
import static ru.aydar.petstore.request.UserRequests.*;

public class UserTest {
    ArrayList<UserObject> users = new ArrayList<>();
    @BeforeTest
    static void beforeAll() {
        baseURI = "https://petstore3.swagger.io/api/v3/user";
    }

    @Test
    public void UserCreationTest() {
        users.add(new UserObject("IIvanov","Ivan","Ivanov","iivanov@pup.ru","badpassword","555321321"));
        users.add(new UserObject("JDoe","John","Doe","jdoe@aol.com","rofllmaoxd","911"));
        users.add(new UserObject("VPupkin","Vasya","Pupkin","vpupkin@pup.ru","goodpassword","555123123"));
        CreateUser(users);
        for (UserObject user : users)
        {
            user.CheckIfExists();
            user.CheckInfo();
        }
    }

    @Test(dependsOnMethods = "UserCreationTest")
    public void UserAuthTest() {
        //В Петсторе реальной аутентификации (сейчас) нет, судя по методам входа/выхода в контроллере:
        //https://github.com/swagger-api/swagger-petstore/blob/master/src/main/java/io/swagger/petstore/controller/UserController.java
        for (UserObject user : users)
        {
            user.Login();
            LogOut();
        }
    }

    @Test(dependsOnMethods = "UserAuthTest")
    public void UserDeletionTest() {
        for (UserObject user : users)
        {
            user.Delete();
            user.CheckIfDoesNotExist();
        }
    }
}
