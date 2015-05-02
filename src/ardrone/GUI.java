/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

/**
 *
 * @author domagalla
 */
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


 
public class GUI extends Application implements Runnable{
 
   
        
      Pane rootPane = new Pane();
        
        Pane topViewPane = new Pane();
        Pane frontViewPane = new Pane();
        Pane sideViewPane = new Pane();
        GridPane statsPane = new GridPane();
        GridPane infoPane = new GridPane();
        GridPane gridpane = new GridPane();   
        
       
        Label posXLabel = new Label("PosX:  "); 
        Label posYLabel = new Label("PosY:  "); 
        Label posZLabel = new Label("PosZ:  "); 
        Label rotXLabel = new Label("RotX:  "); 
        Label rotYLabel = new Label("RotY:  "); 
        Label rotZLabel = new Label("RotZ:  "); 
        
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ARDrone Stats Control");
 
        Label topViewLabel = new Label("TopView");
        Label sideViewLabel = new Label("SideView");
        Label frontViewLabel = new Label("FrontView");
        
       
       
        
        
        topViewPane.setMinSize(500, 300);
        topViewPane.setMaxSize(500, 300);
        topViewPane.setStyle("-fx-background-color: blue;");
        
        topViewPane.getChildren().addAll(topViewLabel);
        
        sideViewPane.setMinSize(500, 300);
        sideViewPane.setMaxSize(500, 300);
        sideViewPane.setStyle("-fx-background-color: green;");
        
        sideViewPane.getChildren().addAll(sideViewLabel);
        
        frontViewPane.setMinSize(300, 300);
        frontViewPane.setMaxSize(300, 300);
        frontViewPane.setStyle("-fx-background-color: yellow;");
        
        
        frontViewPane.getChildren().addAll(frontViewLabel);
        
        statsPane.setMinSize(300, 300);
        statsPane.setMaxSize(300, 300);
        
        statsPane.setConstraints(posXLabel, 1,3);
        statsPane.setConstraints(posYLabel, 1,4);
        statsPane.setConstraints(posZLabel, 1,5);
        statsPane.setConstraints(rotXLabel, 1,9);
        statsPane.setConstraints(rotYLabel, 1,10);
        statsPane.setConstraints(rotZLabel, 1,11);
        
        
        statsPane.getChildren().addAll(posXLabel, posYLabel, posZLabel, rotXLabel, rotYLabel, rotZLabel);
        
        infoPane.setMinSize(400,300);
        infoPane.setMaxSize(400,300);
     
        GridPane.setConstraints(topViewPane, 1, 1); // column=3 row=1
        GridPane.setConstraints(sideViewPane, 2, 1);
        GridPane.setConstraints(frontViewPane, 1, 2);
        GridPane.setConstraints(statsPane, 2,2);
        
        gridpane.getChildren().addAll(topViewPane,sideViewPane,frontViewPane, statsPane);
 

        
//        zeichneDrone(1,1,1);
        rootPane.getChildren().add(gridpane);
        
 
        primaryStage.setScene(new Scene(rootPane, 1000, 600));
        //primaryStage.getStylesheets().add("style.css");
        primaryStage.show();
    }
    public void zeichneDrone(double x, double y, double z){
        Circle droneTop = CircleBuilder.create()
            .radius(10)
            .centerX(z)
            .centerY(x)
            .build();
        droneTop.setFill(Color.RED);
        topViewPane.getChildren().addAll(droneTop);
        
        Circle droneSide = CircleBuilder.create()
            .radius(10)
            .centerX(z)
            .centerY(y)
            .build();
        droneSide.setFill(Color.RED);
        sideViewPane.getChildren().addAll(droneSide);
        
        Circle droneFront = CircleBuilder.create()
            .radius(10)
            .centerX(x)
            .centerY(y)
            .build();
        droneFront.setFill(Color.RED);
        frontViewPane.getChildren().addAll(droneFront);
        System.out.print(y);
        /*rootPane.requestLayout();
        gridpane.requestLayout();
        frontViewPane.requestLayout();*/
        
    } 
            
    public void zeichneStats(double x, double y, double z){
        
        posXLabel.setText("PosX: "+ x);
        System.out.print(y);
        
    }
   /* public static void main(String[] args) {
        launch(args);
    }*/

    @Override
    public void run() {
        launch();
   
    }

}
