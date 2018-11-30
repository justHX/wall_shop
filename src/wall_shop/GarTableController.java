/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall_shop;

import java.net.URL;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.IntegerStringConverter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import wall_shop.entyties.Garages;
import wall_shop.entyties.controllers.GaragesJpaController;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;


public class GarTableController implements Initializable {
    private ObservableList<Garages> garList = FXCollections.observableArrayList();
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Wall_ShopPU");
    private GaragesJpaController garController = new GaragesJpaController(emf);
    
    
    @FXML
    private BorderPane mGarage;
    @FXML
    private Button garButtonAdd;
    @FXML
    private Button garButtonSave;
    @FXML
    private Button garButtonRem;
    @FXML
    private TableView<Garages> tableGar;
    @FXML
    private TableColumn<Garages, Integer> idGar;
    @FXML
    private TableColumn<Garages, String> nameGar;
    @FXML
    private TableColumn<Garages, String> cityGar;
    @FXML
    private TableColumn<Garages, String> strGar;
    @FXML
    private TableColumn<Garages, Integer> numGar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableGar.setPlaceholder(new Label(""));
        
        //<editor-fold defaultstate="collapsed" desc="Инициализация колонок">
        idGar.setCellValueFactory((TableColumn.CellDataFeatures<Garages, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getIdGarage() == null ? 0 : param.getValue().getIdGarage())));
        
        nameGar.setCellValueFactory((TableColumn.CellDataFeatures<Garages, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getName() == null ? "" : param.getValue().getName())));
        nameGar.setCellFactory(TextFieldTableCell.forTableColumn());
        nameGar.setOnEditCommit((TableColumn.CellEditEvent<Garages, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        cityGar.setCellValueFactory((TableColumn.CellDataFeatures<Garages, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getCity() == null ? "" : param.getValue().getCity())));
        cityGar.setCellFactory(TextFieldTableCell.forTableColumn());
        cityGar.setOnEditCommit((TableColumn.CellEditEvent<Garages, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setCity(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        strGar.setCellValueFactory((TableColumn.CellDataFeatures<Garages, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getStreet() == null ? "" : param.getValue().getStreet())));
        strGar.setCellFactory(TextFieldTableCell.forTableColumn());
        strGar.setOnEditCommit((TableColumn.CellEditEvent<Garages, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setStreet(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        numGar.setCellValueFactory((TableColumn.CellDataFeatures<Garages, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getHousenum() == null ? 0 : param.getValue().getHousenum())));
        numGar.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numGar.setOnEditCommit((TableColumn.CellEditEvent<Garages, Integer> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setHousenum(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        //</editor-fold>
    
        garButtonAdd.addEventHandler(ActionEvent.ACTION, eventAdd);
        garButtonSave.addEventHandler(ActionEvent.ACTION, eventSave);
        garButtonRem.addEventHandler(ActionEvent.ACTION, eventRem);
    }    
    
    private final EventHandler<ActionEvent> eventAdd = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Garages gar = new Garages();
            garList.add(gar);
            tableGar.refresh();
            tableGar.getSelectionModel().select(gar);
            garButtonAdd.setDisable(true);
        }
    };
    
    private final EventHandler<ActionEvent> eventSave = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
               List<Garages> allGar;
                allGar = tableGar.getItems();
                for (Garages g : allGar) {
                    if (g.getIdGarage() == null) {
                        garController.create(g);
                    } else if (g.state == -1) {
                        garController.edit(g);
                        g.state = 0;
                    }
                }
                   tableGar.refresh();
            } catch (Exception ex) {
                Logger.getLogger(ProviderTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            garButtonAdd.setDisable(false); 
        }
    };
    
    private final EventHandler<ActionEvent> eventRem = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Garages remGar = tableGar.getSelectionModel().getSelectedItem();
                garController.destroy(remGar.getIdGarage());
                garList.remove(remGar);
                tableGar.refresh();
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProviderTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
        public void initData()
    {
        garList.clear();
        garList.addAll(garController.findGaragesEntities());
        tableGar.setItems(garList);
        tableGar.refresh();
        tableGar.getSelectionModel().select(0);
    }
    
     public BorderPane getGarPane(){
        return mGarage;
    }
}
