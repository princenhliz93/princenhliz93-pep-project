import Controller.SocialMediaController;
import Model.Message;
import Model.Account;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
        MessageService msgg = new MessageService();
        AccountService accservice =  new AccountService();
        Account acc = new Account("joseph","james");
      //  accservice.addAccount(acc);
        Message msg = new Message(2,"hey hey now",1669947792);
        
     /* 
       
       Account acc2 = new Account("jameson","williams");
       
       
       accservice.addAccount(acc2);
       
       
       Message msg2 = new Message(4,"hey yes now",1669947792);

       
       msgg.createMessage(msg2); */
     //  msgg.createMessage(msg);
       System.out.print(" THESE RESULTS"+ msgg.getAllMessagesById(2));

       app.close();

    }
}
