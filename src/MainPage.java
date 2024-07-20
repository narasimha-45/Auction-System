import java.util.*;

public class MainPage{
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws Exception{
        System.out.println("Welcome to Auction S1haruystem");
        System.out.println("1-Login\n2-Register");
        System.out.print("Enter Choice:");
        int c = in.nextInt();
        if(c==1){
            LoginPage adminPage = new LoginPage();
            adminPage.login();
        }
        else{
            RegisterPage registerPage = new RegisterPage();
            registerPage.getRegistered();
        }
    }
}
