import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage initialStage){
        new BankUI().startUI(initialStage);
        
    }


    
}
