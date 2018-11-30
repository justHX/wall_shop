/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall_shop;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import wall_shop.entyties.Reservet;
import wall_shop.entyties.Selling;
import wall_shop.entyties.controllers.ReservetJpaController;
import wall_shop.entyties.controllers.SellingJpaController;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author hulk-
 */
public class SaleTableController implements Initializable {
    private ObservableList<Selling> sellList = FXCollections.observableArrayList();
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Wall_ShopPU");
    private SellingJpaController sellCtrl = new SellingJpaController(emf);
    private ReservetJpaController resCtrl = new ReservetJpaController(emf);
    private  SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    
    @FXML
    private BorderPane borderSale;
    @FXML
    private TableView<Selling> tableSell;
    @FXML
    private TableColumn<Selling, Integer> idSell;
    @FXML
    private TableColumn<Selling, String> dateSell;
    @FXML
    private TableColumn<Selling, Reservet> resSell;
    @FXML
    private TableColumn<Selling, Float> moneySell;
    @FXML
    private Button buttAddSal, buttSaveSal, buttRemSal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableSell.setPlaceholder(new Label(""));
        
        //<editor-fold defaultstate="collapsed" desc="Инициализация ">
        idSell.setCellValueFactory((TableColumn.CellDataFeatures<Selling, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getIdSell() == null ? 0 : param.getValue().getIdSell())));
        
        dateSell.setCellValueFactory((TableColumn.CellDataFeatures<Selling, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getDate() == null ? "" : format.format(param.getValue().getDate()))));
        dateSell.setCellFactory(TextFieldTableCell.forTableColumn());
        dateSell.setOnEditCommit((TableColumn.CellEditEvent<Selling, String> event) -> {
            Date date = new Date();
            try {
                date = format.parse(event.getNewValue());
            } catch (ParseException ex) {
                Logger.getLogger(ProviderTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDate(date);
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        resSell.setCellValueFactory((TableColumn.CellDataFeatures<Selling, Reservet> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getIdReserv() == null ? null : param.getValue().getIdReserv())));
        resSell.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Reservet>() {
            @Override
            public String toString(Reservet object) {
                return object == null ? "" : object.getIdReserv().toString();
            }

            @Override
            public Reservet fromString(String string) {
                for(Reservet r : resCtrl.findReservetEntities()){
                if(r.getIdReserv().toString().equals(string))
                    return r;
                }
                return null;
            }
        }, FXCollections.observableArrayList(resCtrl.findReservetEntities())));
        resSell.setOnEditCommit((TableColumn.CellEditEvent<Selling, Reservet> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIdReserv(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        moneySell.setCellValueFactory((TableColumn.CellDataFeatures<Selling, Float> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getMoney() == null ? 0 : param.getValue().getMoney())));
        moneySell.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        moneySell.setOnEditCommit((TableColumn.CellEditEvent<Selling, Float> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setMoney(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        //</editor-fold>
    
        buttAddSal.addEventHandler(ActionEvent.ACTION, eventAdd);
        buttSaveSal.addEventHandler(ActionEvent.ACTION, eventSave);
        buttRemSal.addEventHandler(ActionEvent.ACTION, eventRem);
    }    
    
     private final EventHandler<ActionEvent> eventAdd = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Selling sell = new Selling();
            sellList.add(sell);
            tableSell.refresh();
            tableSell.getSelectionModel().select(sell);
            buttAddSal.setDisable(true);
        }
    };
     
      private final EventHandler<ActionEvent> eventSave = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
               List<Selling> allSell;
                allSell = tableSell.getItems();
                for (Selling s : allSell) {
                    if (s.getIdSell()== null) {
                        sellCtrl.create(s);
                    } else if (s.state == -1) {
                        sellCtrl.edit(s);
                        s.state = 0;
                    }
                }
                   tableSell.refresh();
            } catch (Exception ex) {
                Logger.getLogger(ProviderTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            resSell.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Reservet>() {
            @Override
            public String toString(Reservet object) {
                return object == null ? "" : object.getIdReserv().toString();
            }

            @Override
            public Reservet fromString(String string) {
                for(Reservet r : resCtrl.findReservetEntities()){
                if(r.getIdReserv().toString().equals(string))
                    return r;
                }
                return null;
            }
        }, FXCollections.observableArrayList(resCtrl.findReservetEntities())));
            buttAddSal.setDisable(false);
        }
    };
      
        private final EventHandler<ActionEvent> eventRem = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
             try {
                Selling remSell = tableSell.getSelectionModel().getSelectedItem();
                sellCtrl.destroy(remSell.getIdSell());
                sellList.remove(remSell);
                tableSell.refresh();
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProviderTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
        public void initData(){
        sellList.clear();
        sellList.addAll(sellCtrl.findSellingEntities());
        tableSell.setItems(sellList);
        tableSell.refresh();
        tableSell.getSelectionModel().select(0);
    }
    
     public BorderPane getSellPane(){
        return borderSale;
    }
}
