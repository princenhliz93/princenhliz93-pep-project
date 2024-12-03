package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    /**
     * Inserts a new message into the database.
     * - Saves the message details (posted_by, message_text, time_posted_epoch) in the message table.
     * - Retrieves the auto-generated message ID and returns a new Message object.
     * 
     * @param message The Message object containing the details of the message to be created.
     * @return The newly created Message object with the generated message ID, or null if an error occurs.
     */
    public Message createMessage(Message message){

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "insert into message(posted_by,message_text, time_posted_epoch) values(?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2,message.getMessage_text());
            preparedStatement.setLong(3,message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
            }


            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    /**
     * Retrieves all messages from the database.
     * 
     * @return A list of all messages stored in the database.
     */
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();

        List<Message> messages = new ArrayList<>();

        try {

            String sql = "select * from message";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            Message message;

            while(rs.next()){

                message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),
                rs.getString("message_text"),rs.getLong("time_posted_epoch"));

                messages.add(message);
                
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    /**
     * Retrieves a message from the database by its ID.
     * 
     * @param id The ID of the message to retrieve.
     * @return The Message object if found; otherwise, returns null.
     */
    public Message getMessageById(int id){

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "select * from message where message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.executeQuery();

            ResultSet rs = preparedStatement.executeQuery();
            Message message;

            while(rs.next()){

                message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),
                rs.getString("message_text"),rs.getLong("time_posted_epoch"));

                return message;

            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    /**
     * Deletes a message from the database by its ID.
     * 
     * @param id The ID of the message to delete.
     */
    public void deleteMessageById (int id){

        Connection connection = ConnectionUtil.getConnection();

        try {
            
            String sql = "delete from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }      

    }

    /**
     * Updates the text of a message in the database.
     * 
     * @param message The new message text to replace the existing one.
     * @param id The ID of the message to be updated.
     */
    public void updateMessageById (String message,int id){
        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "update message set message_text = ? where message_id = ? ";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1,message);
            preparedStatement.setInt(2,id);

            preparedStatement.executeUpdate();

            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        
    }

    /**
     * Retrieves all messages posted by a specific user.
     * 
     * @param id The ID of the user whose messages are to be retrieved.
     * @return A list of messages posted by the specified user.
     */
    public List<Message> getMessagesById (int id){

        Connection connection = ConnectionUtil.getConnection();

        List<Message> userMessages = new ArrayList<>();

        try {

            String sql = "select * from message where posted_by = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,id);

            ResultSet rs = preparedStatement.executeQuery();

            Message message;

            while(rs.next()){

                message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),
                rs.getString("message_text"),rs.getLong("time_posted_epoch"));

                userMessages.add(message);


            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userMessages;

    }

}
