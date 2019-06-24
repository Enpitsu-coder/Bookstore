package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
	private static String jdbcURL="jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf-8";
	private static String jdbcUsername="root";
    private static String jdbcPassword="";
    protected static Connection ConnectDB() throws SQLException {
    	Connection jdbcConnection;
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        return jdbcConnection;
    }
     
    public static void insert(Book newbook) throws SQLException {
    	Connection dbConnect = ConnectDB();
    	String sql = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";
        PreparedStatement statement = dbConnect.prepareStatement(sql);
        statement.setString(1, newbook.getTitle());
        statement.setString(2, newbook.getAuthor());
        statement.setFloat(3, newbook.getPrice());
        statement.executeUpdate();
        statement.close(); 
        dbConnect.close();
    }
     
    public static List<Book> getByAll() throws SQLException {
    	List<Book> listBook = new ArrayList<>();
    	Connection dbConnect = ConnectDB();
        String sql = "SELECT * FROM book";
        Statement statement = dbConnect.createStatement();
        ResultSet bangKetQua = statement.executeQuery(sql);
        while (bangKetQua.next())
        {
            int id = bangKetQua.getInt("book_id");
            String title = bangKetQua.getString("title");
            String author = bangKetQua.getString("author");
            float price = bangKetQua.getFloat("price");
            Book book = new Book(id, title, author, price);
            listBook.add(book);
        }
        bangKetQua.close();
        statement.close();
        dbConnect.close();
        return listBook;
    }
     
    public static void delete(int idBook) throws SQLException {
    	Connection dbConnect = ConnectDB();
    	String sql = "DELETE FROM book where book_id = ?";
        PreparedStatement statement = dbConnect.prepareStatement(sql);
        statement.setInt(1, idBook);
        statement.executeUpdate();
        statement.close();
        dbConnect.close();
    }
     
    public static void update(Book bookUpdate) throws SQLException{
    	Connection dbConnect = ConnectDB();
    	String sql = "UPDATE book SET title = ?, author = ?, price = ?";
        sql += " WHERE book_id = ?";
        PreparedStatement statement = dbConnect.prepareStatement(sql);
        statement.setString(1, bookUpdate.getTitle());
        statement.setString(2, bookUpdate.getAuthor());
        statement.setFloat(3, bookUpdate.getPrice());
        statement.setInt(4, bookUpdate.getId());
        statement.executeUpdate();
        statement.close();
        dbConnect.close();
    }
     
    public static Book getByID(int id) throws SQLException {
    	Connection dbConnect = ConnectDB();
        Book book = null;
        String sql = "SELECT * FROM book WHERE book_id = ?";
                 
        PreparedStatement statement = dbConnect.prepareStatement(sql);
        statement.setInt(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            float price = resultSet.getFloat("price");
             
            book = new Book(id, title, author, price);
        }
        resultSet.close();
        statement.close();
        dbConnect.close(); 
        return book;
    }
}	