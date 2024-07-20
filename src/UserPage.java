import java.util.*;
import java.sql.*;

public class UserPage {
    static Scanner in = new Scanner(System.in);
    final String username;
    private static DataBase dataBase = new DataBase(); 

    public UserPage(String username){
        this.username = username;
    }

    void displayOptions() throws Exception {
        int option;
        do{
        System.out.println("Menu:"+
                            "\n1.To add products to sell."+
                            "\n2.To Bid on products"+
                            "\n3.products you bought"+
                            "\n4.To see bids on your products"+
                            "\n5.sold products"+
                            "\n6.logout"+
                            "\nEnter choice:"
                            );
        option = in.nextInt();
        switch(option){
            case 1:
                addProductsToSell();
                break;
            case 2:
                bidOnProducts();
                break;
            case 3:
                productsYouBought();
                break;
            case 4:
                myProductsAreInSale();
                break;
            case 5:
                soldProducts();
                break;
            case 6:
                break;
        }
        }while(option!=6);
        try {
            MainPage.main(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addProductsToSell() throws Exception{
        String product;
        int minPrice;
        System.out.print("Enter product name:");
        product = in.next();
        System.out.print("Enter minimum price for product:");
        minPrice = in.nextInt();
        dataBase.addProduct(username,product, minPrice);
        System.out.println("Your product is in sales now");
    }

    void bidOnProducts() throws Exception{
        ResultSet resultSet = dataBase.showAllAvailableProducts(username);
        if(resultSet==null){
            System.out.println("No products are in sale");
            return;
        }
        System.out.println("The products in sale:");
        int c=1;
        do{
            String product_id = resultSet.getString("product_id");
            String product = resultSet.getString("product");
            int min_price = resultSet.getInt("min_price");
            String seller = resultSet.getString("seller_id");
            System.out.println("ITEM-"+c);
            System.out.println("Product_id:"+product_id);
            System.out.println("Product:"+product);
            System.out.println("seller:"+seller);
            System.out.println("min_price:"+min_price);
            c++;
        }while(resultSet.next());

        while(true){
            String product_id;
            System.out.print("Enter the product_id of the product which you want to bid:");
            product_id = in.next();
            System.out.print("Enter your bid amount:");
            int amount = in.nextInt();
            int  isBidAccepted = dataBase.bidOnProducts(username, product_id, amount);
            System.out.println(isBidAccepted);
            if(isBidAccepted>0){
                System.out.println("Congratulations your bid is placed");
                break;
            }
            else{
                System.out.println("Invalid product_id.Try again");
            }
        }
        
    }

    void productsYouBought() throws Exception{
        ResultSet resultSet =dataBase.showBoughtProducts(username);
        if(resultSet==null){
            System.out.println("You haven't bought any products");
            return;
        }
        int c=1;
        do{
            String product_id = resultSet.getString("product_id");
            String product = resultSet.getString("product");
            String seller = resultSet.getString("seller");
            int amount = resultSet.getInt("amount");
            System.out.println("ITEM-"+c);
            System.out.println("Product_id:"+product_id);
            System.out.println("Product:"+product);
            System.out.println("seller:"+seller);
            System.out.println("Amount:"+amount);
            c++;
        }while(resultSet.next());
    }

    void soldProducts() throws Exception{
        ResultSet resultSet =dataBase.showSoldProducts(username);
        if(resultSet==null){
            System.out.println("You haven't sold any products");
            return;
        }
        int c=1;
        do{
            String product_id = resultSet.getString("product_id");
            String product = resultSet.getString("product");
            String buyer = resultSet.getString("buyer");
            int amount = resultSet.getInt("amount");
            System.out.println("product-"+c);
            System.out.println("Product_id:"+product_id);
            System.out.println("Product:"+product);
            System.out.println("seller:"+buyer);
            System.out.println("Amount:"+amount);
            c++;
        }while(resultSet.next());
    }

    void myProductsAreInSale() throws Exception{
        ResultSet resultSet = dataBase.mySellingProducts(username);
        if(resultSet==null){
            System.out.println("None of your products are in sale (or) No bids on your products yet");
            return;
        }
        System.out.println("Your products in sale with their highest bids:");
        int c=1;
        do{
            System.out.println("Item:"+c);
            String product_id = resultSet.getString("product_id");
            String product = resultSet.getString("product");
            String buyer_id = resultSet.getString("buyer_id");
            int amount = resultSet.getInt("max_amount");
            int basePrice = resultSet.getInt("min_price");
            System.out.println("product_id:"+product_id);
            System.out.println("product:"+product);
            System.out.println("buyer_id:"+buyer_id);
            System.out.println("min_amount:"+basePrice);
            System.out.println("Amount_offered:"+amount);
            System.out.println("Do you want to sell it to him or wait for some other time\nOptions: \n1-sell\n2-wait");
            System.out.print("Enter option:");
            int choice = in.nextInt();
            if(choice == 1){
                dataBase.sellProducts(product_id, product, username, buyer_id, amount);
                System.out.println("Your "+product +" is sold to the "+buyer_id+" at "+amount);
                break;
            }
        }while(resultSet.next());
    }
}
