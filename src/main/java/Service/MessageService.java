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

    public void deletMessageById (int id){

        messageDao.deleteMessageById(id);

    }

    public void updateMessageById (Message message,int id){

        messageDao.updateMessageById(message,id);

    }

    public List<Message> getMessagesById (int id){

        return messageDao.getMessagesById(id);

    }

}
