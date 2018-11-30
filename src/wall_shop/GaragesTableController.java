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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import wall_shop.entyties.Companys;
import wall_shop.entyties.Garages;
import wall_shop.entyties.InfoGarages;
import wall_shop.entyties.Wallpapers;
import wall_shop.entyties.controllers.CompanysJpaController;
import wall_shop.entyties.controllers.GaragesJpaController;
import wall_shop.entyties.controllers.InfoGaragesJpaController;
import wall_shop.entyties.controllers.WallpapersJpaController;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author hulk-
 */
public class GaragesTableController implements Initializable {
    private ObservableList<InfoGarages> infList = FXCollections.observableArrayList();
    private  EntityManagerFactory emf = Persistence.createEntityManagerFactory("Wall_ShopPU");
    private InfoGaragesJpaController infController = new InfoGaragesJpaController(emf);
    private GaragesJpaController garController = new GaragesJpaController(emf);
    private WallpapersJpaController wallController = new WallpapersJpaController(emf);
    private CompanysJpaController compController = new CompanysJpaController(emf);

        
    @FXML
    private BorderPane borderGar;
    @FXML
    private Button infButtAdd;
    @FXML
    private Button infButtSave;
    @FXML
    private Button infButtRem;
    @FXML
    private TableView<InfoGarages> tableInfGar;
    @FXML
    private TableColumn<InfoGarages, Integer> idGar;
    @FXML
    private TableColumn<InfoGarages, Garages> nameGar;
    @FXML
    private TableColumn<InfoGarages, Companys> providerGar;
    @FXML
    private TableColumn<InfoGarages, Wallpapers> wallGar;
    @FXML
    private TableColumn<InfoGarages, Integer> numGar;
    @FXML
    private TableColumn<InfoGarages, Float> lenthGar;
    @FXML
    private TableColumn<InfoGarages, Float> widthGar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableInfGar.setPlaceholder(new Label(""));
        
        //<editor-fold defaultstate="collapsed" desc="Инициализация колонок">
        idGar.setCellValueFactory((TableColumn.CellDataFeatures<InfoGarages, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getIdinfGar() == null ? 0 : param.getValue().getIdinfGar())));
        
        nameGar.setCellValueFactory((TableColumn.CellDataFeatures<InfoGarages, Garages> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getIdGarage() == null ? null : param.getValue().getIdGarage())));
        nameGar.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Garages>() {
            @Override
            public String toString(Garages object) {
                return object == null ? "" : object.getName();
            }

            @Override
            public Garages fromString(String string) {
                for(Garages g : garController.findGaragesEntities()){
                if(g.getName().equals(string))
                    return g;
                }
                return null;
            }
        }, FXCollections.observableArrayList(garController.findGaragesEntities())));
        nameGar.setOnEditCommit((TableColumn.CellEditEvent<InfoGarages, Garages> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIdGarage(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        providerGar.setCellValueFactory((TableColumn.CellDataFeatures<InfoGarages, Companys> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getIdCompany() == null ? null : param.getValue().getIdCompany())));
        providerGar.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Companys>() {
            @Override
            public String toString(Companys object) {
                return object == null ? "" : object.getNamecompany();
            }

            @Override
            public Companys fromString(String string) {
               for(Companys c : compController.findCompanysEntities()){
               if(c.getNamecompany().equals(string))
                   return c;
               }
               return null;
            }
        }, FXCollections.observableArrayList(compController.findCompanysEntities())));
        providerGar.setOnEditCommit((TableColumn.CellEditEvent<InfoGarages, Companys> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIdCompany(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        wallGar.setCellValueFactory((TableColumn.CellDataFeatures<InfoGarages, Wallpapers> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getIdWall() == null ? null : param.getValue().getIdWall())));
        wallGar.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Wallpapers>() {
            @Override
            public String toString(Wallpapers object) {
                return object == null ? "" : object.getArticles();
            }

            @Override
            public Wallpapers fromString(String string) {
               for(Wallpapers w : wallController.findWallpapersEntities()){
               if(w.getArticles().equals(string))
                   return w;
               }
               return null;
            }
        }, FXCollections.observableArrayList(wallController.findWallpapersEntities())));
        wallGar.setOnEditCommit((TableColumn.CellEditEvent<InfoGarages, Wallpapers> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIdWall(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        numGar.setCellValueFactory((TableColumn.CellDataFeatures<InfoGarages, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getNumberroll() == null ? 0 : param.getValue().getNumberroll())));
        numGar.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numGar.setOnEditCommit((TableColumn.CellEditEvent<InfoGarages, Integer> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setNumberroll(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        lenthGar.setCellValueFactory((TableColumn.CellDataFeatures<InfoGarages, Float> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getLength() == null ? 0 : param.getValue().getLength())));
        lenthGar.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        lenthGar.setOnEditCommit((TableColumn.CellEditEvent<InfoGarages, Float> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setLength(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        widthGar.setCellValueFactory((TableColumn.CellDataFeatures<InfoGarages, Float> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getWidth() == null ? 0 : param.getValue().getWidth())));
        widthGar.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        widthGar.setOnEditCommit((TableColumn.CellEditEvent<InfoGarages, Float> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setWidth(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        //</editor-fold>
        
        infButtAdd.addEventHandler(ActionEvent.ACTION, eventAdd);
        infButtSave.addEventHandler(ActionEvent.ACTION, eventSave);
        infButtRem.addEventHandler(ActionEvent.ACTION, eventRem);
        
    }    
    
    private final EventHandler<ActionEvent> eventAdd = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            InfoGarages ig = new InfoGarages();
            infList.add(ig);
            tableInfGar.refresh();
            tableInfGar.getSelectionModel().select(ig);
            infButtAdd.setDisable(true);
        }
    };
    
    private final EventHandler<ActionEvent> eventSave = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
               List<InfoGarages> allInfo;
                allInfo = tableInfGar.getItems();
                for (InfoGarages ig : allInfo) {
                    if (ig.getIdinfGar() == null) {
                        infController.create(ig);
                    } else if (ig.state == -1) {
                        infController.edit(ig);
                        ig.state = 0;
                    }
                }
                   tableInfGar.refresh();
            } catch (Exception ex) {
                Logger.getLogger(ProviderTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //<editor-fold defaultstate="collapsed" desc="Обновления комбобоксов">
            nameGar.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Garages>() {
            @Override
            public String toString(Garages object) {
                return object == null ? "" : object.getName();
            }

            @Override
            public Garages fromString(String string) {
                for(Garages g : garController.findGaragesEntities()){
                if(g.getName().equals(string))
                    return g;
                }
                return null;
            }
        }, FXCollections.observableArrayList(garController.findGaragesEntities())));
            wallGar.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Wallpapers>() {
            @Override
            public String toString(Wallpapers object) {
                return object == null ? "" : object.getArticles();
            }

            @Override
            public Wallpapers fromString(String string) {
               for(Wallpapers w : wallController.findWallpapersEntities()){
               if(w.getArticles().equals(string))
                   return w;
               }
               return null;
            }
        }, FXCollections.observableArrayList(wallController.findWallpapersEntities())));
            providerGar.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Companys>() {
            @Override
            public String toString(Companys object) {
                return object == null ? "" : object.getNamecompany();
            }

            @Override
            public Companys fromString(String string) {
               for(Companys c : compController.findCompanysEntities()){
               if(c.getNamecompany().equals(string))
                   return c;
               }
               return null;
            }
        }, FXCollections.observableArrayList(compController.findCompanysEntities())));
            
            //</editor-fold>
            infButtAdd.setDisable(false);
        }
    };
    
    private final EventHandler<ActionEvent> eventRem = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
             try {
                InfoGarages remInf = tableInfGar.getSelectionModel().getSelectedItem();
                infController.destroy(remInf.getIdinfGar());
                infList.remove(remInf);
                tableInfGar.refresh();
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProviderTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
        public void initData()
    {
        infList.clear();
        infList.addAll(infController.findInfoGaragesEntities());
        tableInfGar.setItems(infList);
        tableInfGar.refresh();
        tableInfGar.getSelectionModel().select(0);
    }
    
     public BorderPane getGarPane(){
        return borderGar;
    }
    
}
