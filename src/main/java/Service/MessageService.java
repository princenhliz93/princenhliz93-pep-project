package Service;

import DAO.AccountDAO;
import DAO.MessageDao;
import Model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageService {

    private MessageDao messageDao;

    public MessageService(){
        messageDao = new MessageDao();
    }

    public MessageService (MessageDao messageDao){
        this.messageDao = messageDao;
    }

    public Message createMessage(Message message){

        AccountDAO acc = new AccountDAO();

        if(message.getMessage_text().length()<1||message.getMessage_text().length()>255||acc.accountExists(message.getPosted_by())==false){
           return null;
       }

        
        return messageDao.submitMessage(message);

    }

    public List<Message> getAllMessages (){
        return messageDao.getAllMessages();
    }

    public Message getMessageById(int id){
        return messageDao.getMessageById(id);
    }

    public Message deleteMessageById (int id){

        Message msg = messageDao.getMessageById(id);

        if(msg != null){
            messageDao.deleteMessageById(id);
            return msg;
        }
        
        return null;
        

    }

    public Message updateMessageById (String new_message,int id){

       

        if(new_message.length()!=0 &&new_message.length()<256&&messageDao.getMessageById(id)!=null){
           messageDao.updateMessageById(new_message,id);
           return getMessageById(id);
        }

        return null;

        

    }

    public List<Message> getAllMessagesById (int id){

        return messageDao.getMessagesById(id);

    }

}
