/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall_shop;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author hulk-
 */
public class MainFormController implements Initializable {
  
    @FXML
    private MenuItem mProvider, mSelling, mAbout, mDopValue, mDopGarage, mGarages, mDopRes;
    @FXML
    private BorderPane borderMain;
    private ProviderTableController controllerProvider;
    private DirectoryTableController dirController;
    private GarTableController garController;
    private GaragesTableController infGarCtrl;
    private ReserveTableController resCtrl;
    private SaleTableController sellCtrl;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mProvider.addEventHandler(ActionEvent.ACTION, mProviderEvent);
        mDopValue.addEventHandler(ActionEvent.ACTION, mDopValueEvent);
        mAbout.addEventHandler(ActionEvent.ACTION, mAboutEvent);
        mDopGarage.addEventHandler(ActionEvent.ACTION, mGarEvent);
        mGarages.addEventHandler(ActionEvent.ACTION, mGaragesEvent);
        mDopRes.addEventHandler(ActionEvent.ACTION, resEvent);
        mSelling.addEventHandler(ActionEvent.ACTION, sellEvent);
    }    
    
    private final EventHandler<ActionEvent> mAboutEvent = (ActionEvent event) -> {
         Dialog dialog = new Alert(Alert.AlertType.INFORMATION);
         dialog.setTitle("О программе.");
         dialog.setHeaderText("Тут всё работает!");
         dialog.setContentText("На самом деле тут ничего не работает!!");
         dialog.showAndWait();
    };
    
    private final EventHandler<ActionEvent> sellEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
             if (sellCtrl == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FormsFXML/SaleTable.fxml"));
                    BorderPane pane = loader.load();
                    sellCtrl = loader.getController();
                    sellCtrl.initData();
                    borderMain.centerProperty().setValue(pane);
                    pane.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                borderMain.centerProperty().setValue(sellCtrl.getSellPane());
                borderMain.setVisible(true);
            }
        }
    };
    
    private final EventHandler<ActionEvent> resEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (resCtrl == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FormsFXML/ReserveTable.fxml"));
                    BorderPane pane = loader.load();
                    resCtrl = loader.getController();
                    resCtrl.initData();
                    borderMain.centerProperty().setValue(pane);
                    pane.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                borderMain.centerProperty().setValue(resCtrl.getResPane());
                borderMain.setVisible(true);
            }
        }
    };
    
    private final EventHandler<ActionEvent> mGaragesEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
             if (infGarCtrl == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FormsFXML/GaragesTable.fxml"));
                    BorderPane pane = loader.load();
                    infGarCtrl = loader.getController();
                    infGarCtrl.initData();
                    borderMain.centerProperty().setValue(pane);
                    pane.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                borderMain.centerProperty().setValue(infGarCtrl.getGarPane());
                borderMain.setVisible(true);
            }
        }
    };
    
    private final EventHandler<ActionEvent> mDopValueEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (dirController == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FormsFXML/DirectoryTable.fxml"));
                    BorderPane pane = loader.load();
                    dirController = loader.getController();
                    dirController.initData();
                    borderMain.centerProperty().setValue(pane);
                    pane.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                borderMain.centerProperty().setValue(dirController.getDopValuePane());
                borderMain.setVisible(true);
            }
        }
    };
    
    private final EventHandler<ActionEvent> mProviderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (controllerProvider == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FormsFXML/ProviderTableFXML.fxml"));
                    BorderPane pane = loader.load();
                    controllerProvider = loader.getController();
                    controllerProvider.initData();
                    borderMain.centerProperty().setValue(pane);
                    pane.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                borderMain.centerProperty().setValue(controllerProvider.getProviderPane());
                borderMain.setVisible(true);
            }
        }
    };
    
    private final EventHandler<ActionEvent> mGarEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
              if (garController == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FormsFXML/GarTable.fxml"));
                    BorderPane pane = loader.load();
                    garController = loader.getController();
                    garController.initData();
                    borderMain.centerProperty().setValue(pane);
                    pane.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                borderMain.centerProperty().setValue(garController.getGarPane());
                borderMain.setVisible(true);
            }
        }
    };
}
