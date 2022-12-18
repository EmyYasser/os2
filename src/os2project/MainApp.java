/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package os2project;

 
import java.awt.Image;
import java.awt.Insets;
import java.util.concurrent.Semaphore;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import static javafx.scene.layout.Background.fill;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
        
         Thread t1=new Thread(new Reader());
         Thread t2=new Thread(new Writer());
         
        t1.start();
         t2.start();
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book Tickets");
        //////////////////////////////
           
        ////////////////////////////////
        
GridPane grid = new GridPane();
grid.setAlignment(Pos.CENTER);
grid.setHgap(20);
grid.setVgap(30);
Scene scene = new Scene(grid, 600, 500);
scene.setFill(Color.web("#81c483"));
primaryStage.setScene(scene);

Text scenetitle = new Text("Book Tickets");
scenetitle.setStroke(Color.RED); /////

scenetitle.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 40));
grid.add(scenetitle, 1, 0, 1, 1);
/////////////////////////////////
Label form = new Label("From :");
grid.add(form, 0, 1);

TextField formBox = new TextField();
grid.add(formBox, 1, 1);
//////////////////////////////////
Label to = new Label("To :");
grid.add(to, 0, 2);

TextField toBox = new TextField();
grid.add(toBox, 1, 2);
/////////////////////////////////
Label date = new Label("Date :");
grid.add(date, 0, 3);

 DatePicker d = new DatePicker();
 grid.add(d, 1, 3);
///////////////////////////////// BOTTONS
HBox hb = new HBox();
//hb.setPadding(new insets(15));
hb.setSpacing(15);

Button b1 = new Button("Exit");
Button b2 = new Button("Book");
Button b3 = new Button("Refresh");
Button b4 = new Button("Reset");

hb.getChildren().add(b1);
b1.setPrefWidth(100);
hb.getChildren().add(b2);
b2.setPrefWidth(100);
hb.getChildren().add(b3);
b3.setPrefWidth(100);
hb.getChildren().add(b4);
b4.setPrefWidth(100);

grid.add(hb, 1, 4);
hb.setBackground(Background.EMPTY);
////////////////////////////////
final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
  //////////////////////////////
  b1.setOnAction(new EventHandler<ActionEvent>() {
 /////Exit
    @Override
    public void handle(ActionEvent e) {
         Platform.exit();
        System.exit(0);
//start();
    }   });
 /////////////////////////////
b2.setOnAction(new EventHandler<ActionEvent>() {
 //// Book 
    @Override
    public void handle(ActionEvent e) {
        actiontarget.setFill(Color.GREEN);
        actiontarget.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
//        actiontarget.setText("available tickets is 2");
          Thread t1=new Thread(new Reader());
         Thread t2=new Thread(new Writer());
          t1.start();
         t2.start(); 
//        actiontarget.setText("\n available tickets is: "+Thread.currentThread().getId());
            if(AvailableTicket!=0)
        {
           actiontarget.setText("Passenger " + t1.getId() + " booked a ticket.");
        }
             if(AvailableTicket==0)
        {
          actiontarget.setText(" no available tickets  ");
        }
    }
});
///////////////////////////////
/////////////////////////////
b3.setOnAction(new EventHandler<ActionEvent>() {
 ///// Refresh
    @Override
    public void handle(ActionEvent e) {
        actiontarget.setFill(Color.RED);
        actiontarget.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
//        actiontarget.setText("available tickets is 2");
           Thread t1=new Thread(new Reader());
         Thread t2=new Thread(new Writer());
          t1.start();
         t2.start();
//        actiontarget.setText("\n available tickets is: "+Thread.currentThread().getId());
        if (AvailableTicket!=0)
        {
         actiontarget.setText("available tickets is: "+AvailableTicket);
        }
        if(AvailableTicket==0)
        {
          actiontarget.setText(" no available tickets  ");
        }
    }
});
///////////////////////////////
b4.setOnAction(new EventHandler<ActionEvent>() {
 /// Reset
    @Override
    public void handle(ActionEvent e) {
       toBox.clear();
       formBox.clear();
    }
});
///////////////////////////////

/////////////////////////////
        primaryStage.show(); 
    }
    
     private static Semaphore writer=new Semaphore(1);     
    private static Semaphore reader=new Semaphore(1);
    private static int AvailableTicket=20;
    private static int readers=0;
      
    static class Reader implements Runnable{

        @Override
        public void run() {
            while(AvailableTicket!=0){
                try {
                    reader.acquire();
                } catch (InterruptedException e) {
                     e.printStackTrace();
                }
                readers++;
                if (readers == 1){
                    try{
                        writer.acquire();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }   
                }
                reader.release();
                System.out.println("Passenger " +Thread.currentThread().getId()+" checked the available tickets.");
                try {
                    reader.acquire();
                } catch (InterruptedException e) {
                     e.printStackTrace();
                }
                readers--;
                if (readers==0){
                    writer.release();
                }
                reader.release();
                try{
                    Thread.sleep(5000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    static class Writer implements Runnable{

        @Override
        public void run() {
            while(AvailableTicket!=0){
                try {
                writer.acquire();
            } catch (InterruptedException e) {
                     e.printStackTrace();
            }
            AvailableTicket--;
            System.out.println("Passenger " + Thread.currentThread().getId()+ " booked a ticket.");
            writer.release();
            System.out.println("currently tickets Available: "+AvailableTicket);
            try{
                    Thread.sleep(5000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            System.out.println("no available tickets.");
            
            
         
        }
    }
}