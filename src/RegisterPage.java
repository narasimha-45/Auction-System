import java.util.*;

public class RegisterPage {
    String name;
    String username;
    String password;
    String securityAnswer;
    static Scanner in = new Scanner(System.in);
    void getRegistered(){
        
        while(true){
             System.out.println("Registration form");
            System.out.print("Enter name:");
            name = in.next();
            System.out.print("create username:");
            username = in.next();
            System.out.print("create password:");
            password = in.next();
            System.out.println("Security Question.Enter your favourite book?");
            securityAnswer = in.next();
            DataBase dataBase = new DataBase();
            boolean isRegistered = dataBase.Registration(name, username, password,securityAnswer);
            if(isRegistered){
                System.out.println("Registration succesfull");
                
                try {
                    MainPage.main(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            else{
                System.out.println("username already exist");
                System.out.println("Try again");
            }
        }
       
    }
}
