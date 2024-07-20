import java.sql.*;

public class DataBase {

    final String url = "jdbc:mysql://localhost:3306/JavaCourseProject";
    final String user = "root";
    final String password = "Narasimha@45";
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;

    public DataBase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement(); 
        } catch (Exception e) {
            
        }
    }

    String getLoginDetails(String username) throws SQLException{
        String sql = "select password from user_details where username='"+username+"'";
        ResultSet r = statement.executeQuery(sql);
        if(r.next()){
            return r.getString(1);
        }
        else return null;
    }

    String getSecurityAnswer(String username) throws Exception{
        String sql = "select security_answer from user_details where username='"+username+"'";
        ResultSet r = statement.executeQuery(sql);
        if(r.next()){
            return r.getString(1);
        }
        else return null;
    }

    boolean Registration(String user,String username,String password,String securityAnswer){
        String sql = "insert into user_details values('"+user+"','"+username+"','"+password+"','"+securityAnswer+"')";
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    void addProduct(String username,String product,int minPrice) throws Exception{
        String sql = "insert into available_products values(?,?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username+"-"+product);
        preparedStatement.setString(2, username);
        preparedStatement.setString(3, product);
        preparedStatement.setInt(4, minPrice);
        preparedStatement.executeUpdate();
    }

    ResultSet showBoughtProducts(String username) throws Exception{
        String sql = "Select product_id,product,seller,amount from sold_products where buyer = '"+username+"'";
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            return resultSet;
        }
        else{
            return null;
        }
    } 

    ResultSet showSoldProducts(String username) throws Exception{
        String sql = "Select product_id,product,buyer,amount from sold_products where seller = '"+username+"'";
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            return resultSet;
        }
        else{
            return null;
        }
    }

    ResultSet showAllAvailableProducts(String username) throws Exception{
        String sql = "Select * from available_products where seller_id<>'"+username+"'";
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            return resultSet;
        }
        else{
            return null;
        }
    }

    int bidOnProducts(String buyerId,String product_id,int bid_amount) throws Exception{
        String sql = "insert into buyers values(?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, product_id);
        preparedStatement.setString(2, buyerId);
        preparedStatement.setInt(3, bid_amount);
        try {

            int x = preparedStatement.executeUpdate();
            System.out.println(x);
            return x;
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
        
    }

    ResultSet mySellingProducts(String username) throws Exception{
        String sql = "select a.product_id,product,buyer_id,b.max_amount,min_price "+
                        "from available_products a ,(SELECT product_id,max(amount) as 'max_amount' from buyers group by product_id) b,(select * from buyers) c where seller_id='"+username+
                        "' and a.product_id = b.product_id"+ " and b.product_id = c.product_id";   ;
        
        ResultSet resultSet = statement.executeQuery(sql);

        if(resultSet.next()){
            return resultSet;
        }
        return null;
    }

    void sellProducts(String product_id, String product,String seller,String buyer,int amount) throws Exception{

        String sql = "delete from buyers where product_id='"+product_id+"'";
        statement.executeUpdate(sql);
        sql = "delete from available_products where product_id='"+product_id+"'";
        statement.executeUpdate(sql);
        sql = "insert into sold_products values(?,?,?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, product_id);
        preparedStatement.setString(2, product);
        preparedStatement.setString(3, seller);
        preparedStatement.setString(4, buyer);
        preparedStatement.setInt(5, amount);
        preparedStatement.executeUpdate();
    }
}
