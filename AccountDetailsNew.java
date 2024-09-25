import com.google.gson.JsonObject;

public class AccountDetailsNew{
    private String userName;
    private int age;
    private long phoneNumber;
    private long accountNo;
    private String password;
    public static long accountNumberCreator = 1001;
    
    public AccountDetailsNew(String name, int age, long phoneNumber, String password){
        this.userName = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.password = password;
        accountNo = accountNumberCreator++;
    }
    public JsonObject userDetails(){
        JsonObject user = new JsonObject();
        user.addProperty("accountNo", accountNo);
        user.addProperty("userName", userName);
        user.addProperty("age", age);
        user.addProperty("phoneNumber", phoneNumber);
        return user;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}