package Service;

import DAO.AccountDAO;
import DAO.MessageDao;
import Model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageService {

    private MessageDao messageDao;

     // Default constructor that initializes the MessageDao instance

    public MessageService(){
        messageDao = new MessageDao();
    }

    // Constructor that allows injecting a specific MessageDao instance

    public MessageService (MessageDao messageDao){
        this.messageDao = messageDao;
    }

    /**
     * Creates a new message in the system.
     * - Validates that the message text is between 1 and 255 characters.
     * - Ensures the account posting the message exists in the database.
     * 
     * @param message The Message object to be created.
     * @return The created Message object if valid; otherwise, returns null.
     */
    public Message createMessage(Message message){

        AccountDAO acc = new AccountDAO();

        if(message.getMessage_text().length()<1||message.getMessage_text().length()>255||acc.accountExists(message.getPosted_by())==false){
           return null;
       }

        
        return messageDao.createMessage(message);

    }

    /**
     * Retrieves all messages from the database.
     * 
     * @return A list of all Message objects.
     */
    public List<Message> getAllMessages (){
        return messageDao.getAllMessages();
    }

    /**
     * Retrieves a specific message by its ID.
     * 
     * @param id The ID of the message to retrieve.
     * @return The Message object if found; otherwise, returns null.
     */
    public Message getMessageById(int id){
        return messageDao.getMessageById(id);
    }


    /**
     * Deletes a message by its ID.
     * - Retrieves the message before deleting it.
     * - Deletes the message only if it exists.
     * 
     * @param id The ID of the message to delete.
     * @return The deleted Message object if it existed; otherwise, returns null.
     */
    public Message deleteMessageById (int id){

        Message msg = messageDao.getMessageById(id);

        if(msg != null){
            messageDao.deleteMessageById(id);
            return msg;
        }
        
        return null;
        

    }


    /**
     * Updates the text of a message identified by its ID.
     * - Validates that the new message text is between 1 and 255 characters.
     * - Ensures the message exists before updating.
     * 
     * @param new_message The new message text.
     * @param id The ID of the message to update.
     * @return The updated Message object if successful; otherwise, returns null.
     */

    public Message updateMessageById (String new_message,int id){

       

        if(new_message.length()!=0 &&new_message.length()<256&&messageDao.getMessageById(id)!=null){
           messageDao.updateMessageById(new_message,id);
           return getMessageById(id);
        }

        return null;

        

    }

    /**
     * Retrieves all messages posted by a specific user.
     * 
     * @param id The ID of the user whose messages are to be retrieved.
     * @return A list of Message objects posted by the specified user.
     */

    public List<Message> getAllMessagesById (int id){

        return messageDao.getMessagesById(id);

    }

}
