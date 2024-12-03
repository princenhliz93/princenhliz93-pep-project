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

    // Constructor initializes the services required by the controller.
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    
    
    /**
     * Configures and starts the Javalin application, defining the routes (endpoints) and their handlers.
     * 
     * @return A Javalin app object that sets up the endpoints for the application.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // Endpoints and their handlers
        app.get("/messages", this::getAllMessagesHandler);
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("messages", this::postCreateMessageHandler);
        app.get("/messages/{message_id}",this::retrieveMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::retrieveAllMessagesByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
       

        return app;
    }

    /**
     * Handler for user registration. 
     */
    private void postAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper  = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.createAccount(account);
        if(addedAccount!=null){
            context.json(addedAccount);
        }else{
            context.status(400);
        }
        
    }

   /**
     * Handler for message creation.
     */
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

    /**
     * Handler for user login. Validates user credentials and returns the account details.
     */
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

    /**
     * Handler to retrieve all messages.
     */
    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    /**
     * Handler to retrieve a specific message by its ID. Returns the message details.
     */
    private void retrieveMessageByIdHandler (Context context) throws JsonProcessingException{
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

    /**
     * Handler to update a specific message's text by its ID. Returns the updated message.
     */
    public void updateMessageByIdHandler (Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        String newText = message.getMessage_text();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessageById(newText,message_id);

    
        if(updatedMessage!= null){
            context.json(updatedMessage);
        }else{
            context.status(400);
        }

    }

    /**
     * Handler to retrieve all messages posted by a specific user by their account ID.
     */
    public void retrieveAllMessagesByIdHandler(Context context){
        int id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesById(id);
        context.json(messages);

    }

    /**
     * Handler to delete a message by its ID. Returns the deleted message details or a status code.
     */
    public void deleteMessageByIdHandler (Context context){
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        
        if(message != null){
            context.json(message);
        }else{
            context.status(200);
        }
        
    }


}