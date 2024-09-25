import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Scanner;

class App{
    static Scanner input = new Scanner(System.in);
    static ArrayList<AccountDetailsNew> userAccountsList = new ArrayList<AccountDetailsNew>();
    static Gson gson = new Gson();

    public static void main(String[] args) {
        
        boolean entry;
        do{
            entry = true;
            System.out.println("Enter an option");
            System.out.println("a.Create New Account  \t b.Login \t c.Forgot Password \t e. Exit");
            String mainOption = input.next();
            switch (mainOption){
                case "a" : 
                    AccountDetailsNew account = creatingAccount();
                    userAccountsList.add(account);
                    break;
                case "b" : 
                    AccountDetailsNew logInUser = logIn();
                    mainMenu(logInUser);
                    break;
                case "c" :
                    forgotPassword();
                    break;
                case "e" :
                    System.out.println("You've Exited the code.");
                    entry = false;
                    break;
                default :
                    System.out.println("Invalid Option Try again");
            }
        }while(entry);
        
        
        input.close();
    }

    public static void mainMenu(AccountDetailsNew userDetail){
        boolean mainMenuEntry;
        do{
            mainMenuEntry = true;
            System.out.println("Please Enter an option \na.Show account details\t b.Change Password\t c.Log out");
            String mianMenuOPtion = input.next();
            switch(mianMenuOPtion){
                case "a":{
                    System.out.println("\nAccount number: " + userDetail.userDetails().get("accountNo"));
                    System.out.println("Account holder Name: " + userDetail.userDetails().get("userName"));
                    System.out.println("Age: " + userDetail.userDetails().get("age"));
                    System.out.println("Phone number: " + userDetail.userDetails().get("phoneNumber") + "\n");
                    break;
                }
                case "b":{
                    System.out.println("Enter your Old password");
                    String oldPassword = input.next();
                    if(oldPassword.equals(userDetail.getPassword())){
                        updatePassword(userDetail, oldPassword);
                        mainMenuEntry = false;
                    }
                    else{
                       System.out.println("Old Password is wrong.");
                    }
                    break;
                } 
                case "c":
                    mainMenuEntry = false;
                    break;
                default:
                    System.out.println("Invalid Option, Try again");
            }
        }while(mainMenuEntry); 
    }

    public static void updatePassword(AccountDetailsNew userDetail, String oldPassword){
        System.out.println("Enter your New password");
        String newPassword = input.next();
        System.out.println("Confirm your New password");
        String newConfirmPassword = input.next();
        if(isValidPassword(newPassword) && isValidPassword(newConfirmPassword)){
            if(newPassword.equals(newConfirmPassword)){
                if (!oldPassword.equals(newConfirmPassword)){
                    userDetail.setPassword(newConfirmPassword);
                    System.out.println("New password Set Successfully.");
                }
                else
                    System.out.println("New password should not be Old password");
            }
            else
                System.out.println("New Passwords mismatch");
        }
    }

    public static AccountDetailsNew creatingAccount() {
        boolean check;
        do{
            check = true;
            System.out.println("Enter your Name : ");
            String name = input.next();
            if (isString(name)){
                System.out.println("Enter your age");
                String age = input.next();
                try{
                    int convertedAge = Integer.parseInt(age);
                    if(convertedAge>15 && convertedAge<100){
                        System.out.println("Enter your 10 Digit Phone Number");
                        String phoneNumber = input.next();
                        long convertedPNumber = Long.parseLong(phoneNumber);
                        if (phoneNumber.length()==10){
                            boolean passwordCheck;
                            do{
                                passwordCheck = true;
                                System.out.println("Generate a New Password");
                                System.out.println("Password should contain atleast one alphabet and one number with a length from 10 to 15");
                                String password = input.next();
                                System.out.println("Re-Enter password");
                                String confirmPassword = input.next();                                        
                                if(isValidPassword(password) && isValidPassword(confirmPassword)){
                                    if (password.equals(confirmPassword)){
                                        System.out.println("Account created Successfully");
                                        AccountDetailsNew newAccount = new AccountDetailsNew(name, convertedAge, convertedPNumber, confirmPassword);
                                        System.out.println("Your New account Number is " + newAccount.userDetails().get("accountNo"));
                                        check = false;
                                        passwordCheck = false;
                                        return newAccount;
                                    }
                                    else
                                        System.out.println("Passwords Mismatch");
                                         
                                }                                            
                            }while(passwordCheck);
                        }
                        else
                            System.out.println("Phone Number should be 10 digits");                            
                    }
                    else
                        System.out.println("Age should be between 15 and 100");
                }
                catch(NumberFormatException e){    
                    System.out.println("Age and Phone number should be numbers");
                }
            }
            else{
                System.out.println("Name must contain Alphabets only");
            }
        }while(check);
        return null;
    }

    public static void forgotPassword() {
        if(userAccountsList.size()!=0){
        System.out.println("Enter your Account number");
        String accNumber = input.next();
        try{
            long convertedAccNumber = Long.parseLong(accNumber);
            if(convertedAccNumber>1000 && convertedAccNumber<1100){
                System.out.println("Enter your Phone number");
                String phoneNumber = input.next();
                long convertedPNumber = Long.parseLong(phoneNumber);
                if(phoneNumber.length() == 10){
                    for(int i = 0 ; i<userAccountsList.size() ; i++){
                        AccountDetailsNew userDetail = userAccountsList.get(i);
                        if (userDetail.userDetails().get("accountNo").getAsString().equals(accNumber) &&
                            userDetail.userDetails().get("phoneNumber").getAsString().equals(phoneNumber)){
                            updatePassword(userDetail, userDetail.getPassword());
                        }
                    }
                }
                else
                    System.out.println("Invalid Phone Number");
                
            }
            else 
                System.out.println("Invalid Account Number");
        }
        catch(NumberFormatException E){
            System.out.println("Account number or Phone number is Invalid");
        }
        }
        else
            System.out.println("There is no Account in our database, So you need to create an account first");
    }

    public static AccountDetailsNew logIn(){
        if(userAccountsList.size()!=0){
        boolean entry;
        do{
            entry = true;
            System.out.println("Enter your Account Number");
            String accNumber = input.next();
            try{
                long convertedAccNumber = Long.parseLong(accNumber);
                System.out.println("Enter your password");
                String password = input.next();
                boolean loggedInSuccessfully = false;
                for(AccountDetailsNew object : userAccountsList){
                    if(object.userDetails().get("accountNo").getAsString().equals(accNumber) &&
                       object.getPassword().equals(password)){
                        System.out.println("You've LoggedIn Successfully");
                        entry = false;
                        loggedInSuccessfully = true;
                        return object;
                    }
                }
                if(!loggedInSuccessfully){
                    System.out.println("Account number or password is wrong");
                }
            }
            catch(Exception E){
                System.out.println("You've Entered an Invalid Account Number");
            }
        }while(entry);
        }
        else
            System.out.println("There is no Account in our database, So you need to create an account first");
        return null;
    }

    public static boolean isString(String name){
        boolean input = name.matches("[a-z,A-Z]+");
        return input;
    }

    public static boolean isValidPassword(String password){
        if(password.length()<10 || password.length()>15){
            System.out.println("Password must be between 10 and 15 characters");
            return false;
        }
        boolean hasAlphabet = false;
        boolean hasDigit = false;
        for(char c : password.toCharArray()){
            if(Character.isDigit(c)){
                hasDigit = true;
            }else if(Character.isLetter(c)){
                hasAlphabet = true;
            }else{
                System.out.println("Password must be an alphabet or numbers");
                return false;
            }
        }
        if (hasAlphabet && hasDigit){
            return true;
        }
        else{
            System.out.println("Password must contain both alphabet and numbers");
            return false;
        }
    }
}