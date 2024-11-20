package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

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

    public Account login (Account acc){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "Select * from account where username = ? and password = ? ";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(1, acc.getPassword());

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

}
