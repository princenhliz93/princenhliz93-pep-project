import Controller.SocialMediaController;
import Model.Message;
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
        

       // MessageService msgg = new MessageService();
      //  Message msg = new Message(1,3,"hey hey now",1669947792);

      //  System.out.print(msgg.createMessage(msg));

    }
}
