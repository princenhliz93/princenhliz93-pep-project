package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

/**
     * Registers a new account in the database.
     * - Inserts the provided username and password into the account table.
     * - Retrieves the auto-generated account ID and returns a new Account object.
     * 
     * @param acc The Account object containing the username and password to be registered.
     * @return The newly created Account object with the generated account ID, or null if an error occurs.
     */
public class AccountDAO {

    public Account registerAccount(Account acc){

        Connection conn = ConnectionUtil.getConnection();

        try {

            String sql = "insert into account(username, password) values (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2,acc.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_user_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_user_id, acc.getUsername(),acc.getPassword());
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    /**
     * Authenticates a user by validating the username and password.
     * - Searches the account table for a record matching the provided username and password.
     * 
     * @param acc The Account object containing the login credentials.
     * @return The authenticated Account object if found; otherwise, returns null.
     */
    public Account login (Account acc){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "Select * from account where username = ? and password = ? ";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());

            ResultSet rs = preparedStatement.executeQuery();

            Account account;

            while(rs.next()){
               account = new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));

               return account;
                
            }

            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    return null;

    }

   /**
     * Checks if an account exists in the database by account ID.
     * - Queries the account table for a record with the specified ID.
     * 
     * @param id The ID of the account to check.
     * @return True if the account exists; otherwise, false.
     */
    public Boolean accountExists (int id){

        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "select * from account where account_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs!=null){
                return true;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;


    }

}
