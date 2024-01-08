package ru.aydar.petstore.request;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.aydar.petstore.object.UserObject;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class UserRequests {
    public static void CreateUser(ArrayList<UserObject> users)
    {
        JSONArray requestArray = new JSONArray();
        for (UserObject user : users)
        {
            JSONObject newUser = new JSONObject();
            newUser.put("username", user.username);
            newUser.put("firstName", user.firstName);
            newUser.put("lastName", user.lastName);
            newUser.put("email", user.email);
            newUser.put("password", user.password);
            newUser.put("phone", user.phone);
            requestArray.put(newUser);
        }
        System.out.println("Отправляем запрос на создание массива пользователей:");
        given()
                .contentType("application/json")
                .body(requestArray.toString())
                .when().post("/createWithList").then()
                .log().body()
                .statusCode(200);
    }
    public static void CreateUser(UserObject user)
    {
        JSONObject newUser = new JSONObject();
        newUser.put("username", user.username);
        newUser.put("firstName", user.firstName);
        newUser.put("lastName", user.lastName);
        newUser.put("email", user.email);
        newUser.put("password", user.password);
        newUser.put("phone", user.phone);
        System.out.println("Отправляем запрос на создание пользователя:");
        given()
                .contentType("application/json")
                .body("["+newUser+"]")
                .when().post("/createWithList").then()
                .log().body()
                .statusCode(200);
    }

    public static void LogOut()
    {
        System.out.println("Выход из текущего пользователя:");
        given()
        .contentType("application/json")
        .when().get("/logout").then()
        .log().body()
        .statusCode(200);
    }
}
