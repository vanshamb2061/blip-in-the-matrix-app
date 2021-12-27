package apiKeys;

public class GlobalData {
    public static String userId;
    public static Integer userAge;

    public static void setUserId(String userID){
        userId = userID;
    }
    public static String getUserId(){
        return userId;
    }

    public static void setUserAge(String userAge){
        userAge = userAge;
    }
    public static Integer getUserAge(){
        return userAge;
    }

}
