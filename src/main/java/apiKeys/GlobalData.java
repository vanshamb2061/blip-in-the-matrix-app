package apiKeys;

public class GlobalData {
    public static String userId;

    public static void setUserId(String userID){
        userId = userID;
    }
    public static String getUserId(){

        return userId;
    }
}
