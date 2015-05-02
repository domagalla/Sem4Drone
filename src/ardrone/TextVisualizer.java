/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.stage.Stage;

/**
 *
 * @author Oliver
 */
public class TextVisualizer extends Application implements Observer{
    
        double x = 300;
        double y = 150;
        double  z = 50;
        
        boolean single = true;
        
        Pane rootPane = new Pane();
        
        Pane topViewPane = new Pane();
        Pane frontViewPane = new Pane();
        Pane sideViewPane = new Pane();
        GridPane statsPane = new GridPane();
        GridPane infoPane = new GridPane();
        GridPane gridpane = new GridPane();   
        
        public TextVisualizer(){
        }
        
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Abacus");
        
       
        
       
        
        
        Label topViewLabel = new Label("TopView");
        Label sideViewLabel = new Label("SideView");
        Label frontViewLabel = new Label("FrontView");
        
        Label posXLabel = new Label("PosX:  " + x); 
        Label posYLabel = new Label("PosY:  "); 
        Label posZLabel = new Label("PosZ:  "); 
        Label rotXLabel = new Label("RotX:  "); 
        Label rotYLabel = new Label("RotY:  "); 
        Label rotZLabel = new Label("RotZ:  "); 
       
        
        
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
 

        
        zeichneDrone();
        rootPane.getChildren().add(gridpane);
        
 
        primaryStage.setScene(new Scene(rootPane, 1000, 600));
        //primaryStage.getStylesheets().add("style.css");
        primaryStage.show();
    }
    //Der Textvisualizer sollte die GUI erstellen und in der Methode visualize() die GUI mit den Daten aus pos[] füttern
    
    @Override
    public void update(Observable o, Object arg) {
        //nur ausführen wenn nicht nur die Drohne für das Model übergeben wird
        if(!arg.getClass().getName().equals("ardrone.Actor")){
            visualize((double[])arg);
            if(single){
                launch();
                single = false;
            }
        }
    }
    
    public void visualize(double[] pos){
        //pos beinhaltet Position in x, y und z und die Rotation um die Z-Achse
	System.out.println("Drone bewegt sich auf " + pos[0] +", "+ pos[1] +", "+ pos[2]);
        System.out.println("Drohne ist um "+ pos[3] +" Grad rotiert");
       
        
    }
    
    
     public void zeichneDrone(){
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
    } 

    
	
	
	
}
