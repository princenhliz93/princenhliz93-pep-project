package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/messages", this::getAllMessagesHandler);
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("messages", this::postCreateMessageHandler);
        app.get("/messages/{message_id}",this::retrieveMessagesByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::retrieveAllMessagesByIdHandler);
       // app.start();

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper  = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            context.json(mapper.writeValueAsString(addedAccount));
        }else{
            context.status(400);
        }
        
    }

    private void postCreateMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper  = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage!=null){
            context.json(mapper.writeValueAsString(createdMessage));
        }else{
            context.status(400);
        }
        
    }

    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper  = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loggedIn = accountService.login(account);
        if(loggedIn!=null){
            context.json(mapper.writeValueAsString(loggedIn));
        }else{
            context.status(401);
        }
    }

    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void retrieveMessagesByIdHandler (Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
      //  Message message = mapper.readValue(context.body(), Message.class);
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message retMessage = messageService.getMessageById(message_id);
        if(retMessage!=null){
            context.json(retMessage);
        }else {
            context.status(200);
        }
          
    }

    public void updateMessageByIdHandler (Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        String newText = (context.pathParam("message_text"));
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessageById(message,message_id);

    
        if(updatedMessage!= null){
            context.json(updatedMessage);
        }else{
            context.status(400);
        }

    }

    public void retrieveAllMessagesByIdHandler(Context context){
        int id = Integer.parseInt(context.pathParam("posted_by"));
        List<Message> messages = messageService.getAllMessagesById(id);
        context.json(messages);

    }


}