import java.util.Scanner;

public class LoginPage {
    static Scanner in = new Scanner(System.in);
    private static DataBase dataBase = new DataBase();
    void login() throws Exception{
        String username;
        while(true){
            System.out.print("Enter username:");
            username = in.next();
            String r = dataBase.getLoginDetails(username);
            if(r == null){
                System.out.println("Invalid username.Try again");
                continue;
            }
            System.out.print("Enter password:");
            String password;
            password= in.next();
            if(r.equals(password)){
                System.out.println("Logged in succesfully");
                break;
            }
            else{
                boolean loggedin = false;
                while(true){
                    System.out.print("Wrong password.\nOptions:\n 1- To Enter password again\n2-Forget password\nEnter Option:");
                    int c = in.nextInt();
                    boolean isPasswordGot = false;
                    if(c==1)
                    {
                        System.out.print("Enter password:");
                        password = in.next();
                        if(r.equals(password)){
                            System.out.println("Logged in succesfully");
                            loggedin = true;
                            break;
                        }
                    }
                    else{
                        
                        while(true){
                            System.out.println("Security Question.\nWhich is your favourite book?");
                            String securityAnswer = in.next();
                            if(securityAnswer.equals(dataBase.getSecurityAnswer(username))){
                                System.out.println("Your password is "+r);
                                System.out.println("Now login again");
                                isPasswordGot = true;
                                break;
                            }
                            else{
                                System.out.println("Try again.");
                            }
                        }

                    }
                    if(isPasswordGot){
                        break;
                    }
                }
                if(loggedin){
                    break;
                }
            }
        }
        UserPage userPage = new UserPage(username);
        userPage.displayOptions();
    }
}
