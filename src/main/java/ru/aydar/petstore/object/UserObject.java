package ru.aydar.petstore.object;

import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static io.restassured.RestAssured.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class UserObject {
        public Integer id;
        public String username;
        public String firstName;
        public String lastName;
        public String email;
        public String password;
        public String phone;
        public Integer userStatus;

        public UserObject(String username, String firstName, String lastName, String email, String password, String phone)
        {
                this.username = username;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.password = password;
                this.phone = phone;
        }

        public void Create()
        {
                JSONObject user = new JSONObject();
                user.put("username", username);
                user.put("firstName", firstName);
                user.put("lastName", lastName);
                user.put("email", email);
                user.put("password", password);
                user.put("phone", phone);
                System.out.println("Отправляем запрос на создание пользователя:");
                given()
                .contentType("application/json")
                .body("["+user+"]")
                .when().post("/createWithList").then()
                .log().body()
                .statusCode(200);
        }

        public void CheckIfExists()
        {
                System.out.println("Проверяем, что пользователь "+ username + " существует");
                get("/"+username).then().statusCode(200);
        }

        public void CheckInfo()
        {
                JSONObject objectInfo = new JSONObject();
                JSONObject responseInfo = new JSONObject(get("/"+username).getBody().asString());
                objectInfo.put("username", username);
                objectInfo.put("firstName", firstName);
                objectInfo.put("lastName", lastName);
                objectInfo.put("email", email);
                objectInfo.put("password", password);
                objectInfo.put("phone", phone);
                System.out.println("Сравниваем переданные нами данные пользователя "+firstName+" "+lastName+":\n"+ objectInfo);
                System.out.println("С данными, пришедшими в ответ от API:\n"+ responseInfo);
                assertEquals(objectInfo, responseInfo, JSONCompareMode.LENIENT);
        }

        public void CheckIfDoesNotExist()
        {
                System.out.println("Проверяем, что пользователя "+ username + " не существует:");
                get("/"+username).then().log().body().statusCode(404);
        }

        public void Login()
        {
                System.out.println("Вход под пользователем "+ username + ":");
                given()
                .contentType("application/json")
                .when().get("/login?username="+username+"&password="+password).then()
                .log().body()
                .statusCode(200);
        }

        public void Delete()
        {
                System.out.println("Удаляем пользователя "+ username);
                delete("/"+username).then().statusCode(200);
        }
}
