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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
import wall_shop.entyties.Clients;
import wall_shop.entyties.Delivery;
import wall_shop.entyties.Garages;
import wall_shop.entyties.Reservet;
import wall_shop.entyties.Wallpapers;
import wall_shop.entyties.controllers.ClientsJpaController;
import wall_shop.entyties.controllers.DeliveryJpaController;
import wall_shop.entyties.controllers.GaragesJpaController;
import wall_shop.entyties.controllers.ReservetJpaController;
import wall_shop.entyties.controllers.WallpapersJpaController;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author hulk-
 */
public class ReserveTableController implements Initializable {

    private ObservableList<Clients> clientList = FXCollections.observableArrayList();
    private ObservableList<Reservet> resList = FXCollections.observableArrayList();
    private ObservableList<Delivery> delList = FXCollections.observableArrayList();
    private  EntityManagerFactory emf = Persistence.createEntityManagerFactory("Wall_ShopPU");
    private ClientsJpaController clientController = new ClientsJpaController(emf);
    private ReservetJpaController resCtrl = new ReservetJpaController(emf);
    private DeliveryJpaController delCtrl = new DeliveryJpaController(emf);
    private WallpapersJpaController wallCtrl = new WallpapersJpaController(emf);
    private GaragesJpaController garCtrl = new GaragesJpaController(emf);
    private  SingleSelectionModel<Tab> selectionModel;
    
    //<editor-fold defaultstate="collapsed" desc="FXMLники">
    @FXML
    private BorderPane borderRes;
    @FXML
    private Button buttAdd;
    @FXML
    private Button buttSave;
    @FXML
    private Button buttRem;
    @FXML
    private TableView<Clients> tableClients;
    @FXML
    private TableView<Reservet> tableRes;
     @FXML
    private TableColumn<Reservet, Integer> idRes;
    @FXML
    private TableColumn<Reservet, Clients> clientRes;
    @FXML
    private TableColumn<Reservet, Wallpapers> wallRes;
    @FXML
    private TableColumn<Reservet, Integer> numRes;
    @FXML
    private TableColumn<Reservet, Float> lenthRes;
    @FXML
    private TableColumn<Reservet, Float> widthRes;
    @FXML
    private TableColumn<Reservet, Garages> garRes;
    @FXML
    private TableColumn<Reservet, String> dilRes;
    @FXML
    private TableColumn<Clients, Integer> idClient;
    @FXML
    private TableColumn<Clients, String> nameClient;
    @FXML
    private TableColumn<Clients, String> numClient;
    @FXML
    private TableView<Delivery> tableDil;

    @FXML
    private TableColumn<Delivery, Integer> idDel;

    @FXML
    private TableColumn<Delivery, Reservet> resDel;

    @FXML
    private TableColumn<Delivery, String> cityDel;

    @FXML
    private TableColumn<Delivery, String> strDel;

    @FXML
    private TableColumn<Delivery, Integer> housDel;

    @FXML
    private TableColumn<Delivery, String> corpDel;

    @FXML
    private TableColumn<Delivery, Integer> kvarDel;

    @FXML
    private TableColumn<Delivery, Integer> numDel;
    @FXML
    private TabPane tabPaneRes;
    @FXML
    private Tab tabDeliv, tabClient, tabRes;
    //</editor-fold>


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectionModel = tabPaneRes.getSelectionModel();
        selectionModel.select(tabClient);
        tableClients.setPlaceholder(new Label(""));
        tableRes.setPlaceholder(new Label(""));
        tableDil.setPlaceholder(new Label(""));
        
        //<editor-fold defaultstate="collapsed" desc="Инициализация Клиентов">
        idClient.setCellValueFactory((TableColumn.CellDataFeatures<Clients, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getIdClient() == null ? 0 : param.getValue().getIdClient())));
      
        nameClient.setCellValueFactory((TableColumn.CellDataFeatures<Clients, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getName() == null ? "" : param.getValue().getName())));
        nameClient.setCellFactory(TextFieldTableCell.forTableColumn());
        nameClient.setOnEditCommit((TableColumn.CellEditEvent<Clients, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        numClient.setCellValueFactory((TableColumn.CellDataFeatures<Clients, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getPhonenum() == null ? "" : param.getValue().getPhonenum())));
        numClient.setCellFactory(TextFieldTableCell.forTableColumn());
        numClient.setOnEditCommit((TableColumn.CellEditEvent<Clients, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPhonenum(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        //</editor-fold>
   
        //<editor-fold defaultstate="collapsed" desc="Инициализация Резервов">
        idRes.setCellValueFactory((TableColumn.CellDataFeatures<Reservet, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getIdReserv() == null ? 0 : param.getValue().getIdReserv())));
        
        clientRes.setCellValueFactory((TableColumn.CellDataFeatures<Reservet, Clients> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getIdClient() == null ? null : param.getValue().getIdClient())));
        clientRes.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Clients>() {
            @Override
            public String toString(Clients object) {
                return object == null ? "" : object.getName();
            }

            @Override
            public Clients fromString(String string) {
               for(Clients c : clientController.findClientsEntities()){
               if(c.getName().equals(string))
                   return c;
               }
               return null;
            }
        }, FXCollections.observableArrayList(clientController.findClientsEntities())));
        clientRes.setOnEditCommit((TableColumn.CellEditEvent<Reservet, Clients> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIdClient(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        
        wallRes.setCellValueFactory((TableColumn.CellDataFeatures<Reservet, Wallpapers> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getWall() == null ? null : param.getValue().getWall())));
        wallRes.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Wallpapers>() {
            @Override
            public String toString(Wallpapers object) {
                return object == null ? "" : object.getArticles();
            }

            @Override
            public Wallpapers fromString(String string) {
                for(Wallpapers w : wallCtrl.findWallpapersEntities()){
                if(w.getArticles().equals(string))
                    return w;
                }
                return null;
            }
        }, FXCollections.observableArrayList(wallCtrl.findWallpapersEntities())));
        wallRes.setOnEditCommit((TableColumn.CellEditEvent<Reservet, Wallpapers> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setWall(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        numRes.setCellValueFactory((TableColumn.CellDataFeatures<Reservet, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getWallnum() == null ? 0 : param.getValue().getWallnum())));
        numRes.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numRes.setOnEditCommit((TableColumn.CellEditEvent<Reservet, Integer> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setWallnum(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        lenthRes.setCellValueFactory((TableColumn.CellDataFeatures<Reservet, Float> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getLength() == null ? 0 : param.getValue().getLength())));
        lenthRes.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        lenthRes.setOnEditCommit((TableColumn.CellEditEvent<Reservet, Float> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setLength(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        widthRes.setCellValueFactory((TableColumn.CellDataFeatures<Reservet, Float> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getWidth() == null ? 0 : param.getValue().getWidth())));
        widthRes.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        widthRes.setOnEditCommit((TableColumn.CellEditEvent<Reservet, Float> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setWidth(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        garRes.setCellValueFactory((TableColumn.CellDataFeatures<Reservet, Garages> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getIdGar() == null ? null : param.getValue().getIdGar())));
        garRes.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Garages>() {
            @Override
            public String toString(Garages object) {
                return object == null ? "" : object.getName();
            }

            @Override
            public Garages fromString(String string) {
                for(Garages g : garCtrl.findGaragesEntities()){
                if(g.getName().equals(string))
                    return g;
                }
                return null;
            }
        }, FXCollections.observableArrayList(garCtrl.findGaragesEntities())));
        garRes.setOnEditCommit((TableColumn.CellEditEvent<Reservet, Garages> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIdGar(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
       
        dilRes.setCellValueFactory((TableColumn.CellDataFeatures<Reservet, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "НЕТ" : (param.getValue().getYoNdeliv() == null ? "НЕТ" : param.getValue().getYoNdeliv())));
        dilRes.setCellFactory(ComboBoxTableCell.forTableColumn("ДА", "НЕТ"));
        dilRes.setOnEditCommit((TableColumn.CellEditEvent<Reservet, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setYoNdeliv(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        //</editor-fold> 
        
        //<editor-fold defaultstate="collapsed" desc="Инициализация Доставки">
        idDel.setCellValueFactory((TableColumn.CellDataFeatures<Delivery, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getIdDelivery() == null ? 0 : param.getValue().getIdDelivery())));
        
        resDel.setCellValueFactory((TableColumn.CellDataFeatures<Delivery, Reservet> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getIdReserv() == null ? null : param.getValue().getIdReserv())));
        resDel.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Reservet>() {
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
        resDel.setOnEditCommit((TableColumn.CellEditEvent<Delivery, Reservet> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIdReserv(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        cityDel.setCellValueFactory((TableColumn.CellDataFeatures<Delivery, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getCity() == null ? "" : param.getValue().getCity())));
        cityDel.setCellFactory(TextFieldTableCell.forTableColumn());
        cityDel.setOnEditCommit((TableColumn.CellEditEvent<Delivery, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setCity(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        
        strDel.setCellValueFactory((TableColumn.CellDataFeatures<Delivery, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getStreet() == null ? "" : param.getValue().getStreet())));
        strDel.setCellFactory(TextFieldTableCell.forTableColumn());
        strDel.setOnEditCommit((TableColumn.CellEditEvent<Delivery, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setStreet(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        
        housDel.setCellValueFactory((TableColumn.CellDataFeatures<Delivery, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getHousenum() == null ? 0 : param.getValue().getHousenum())));
        housDel.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        housDel.setOnEditCommit((TableColumn.CellEditEvent<Delivery, Integer> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setHousenum(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        corpDel.setCellValueFactory((TableColumn.CellDataFeatures<Delivery, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getKorpus() == null ? "" : param.getValue().getKorpus())));
        corpDel.setCellFactory(TextFieldTableCell.forTableColumn());
        corpDel.setOnEditCommit((TableColumn.CellEditEvent<Delivery, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setKorpus(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        kvarDel.setCellValueFactory((TableColumn.CellDataFeatures<Delivery, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getKvartira() == null ? 0 : param.getValue().getKvartira())));
        kvarDel.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        kvarDel.setOnEditCommit((TableColumn.CellEditEvent<Delivery, Integer> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setKvartira(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        numDel.setCellValueFactory((TableColumn.CellDataFeatures<Delivery, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getNumdeliv() == null ? 0 : param.getValue().getNumdeliv())));
        numDel.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numDel.setOnEditCommit((TableColumn.CellEditEvent<Delivery, Integer> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setNumdeliv(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        //</editor-fold>
    
        buttAdd.addEventHandler(ActionEvent.ACTION, eventAdd);
        buttSave.addEventHandler(ActionEvent.ACTION, eventSave);
        buttRem.addEventHandler(ActionEvent.ACTION, eventRem);
    }    
    
    private final EventHandler<ActionEvent> eventAdd = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (selectionModel.getSelectedItem() == tabClient) {
                Clients cl = new Clients();
                clientList.add(cl);
                tableClients.refresh();
                tableClients.getSelectionModel().select(cl);
            } else if (selectionModel.getSelectedItem() == tabRes) {
                Reservet res = new Reservet();
                resList.add(res);
                tableRes.refresh();
                tableRes.getSelectionModel().select(res);
            } else if (selectionModel.getSelectedItem() == tabDeliv) {
                Delivery dl = new Delivery();
                delList.add(dl);
                tableDil.refresh();
                tableDil.getSelectionModel().select(dl);
            }
         
       buttAdd.setDisable(true);
        }
    };
    
        private final EventHandler<ActionEvent> eventSave = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                if (selectionModel.getSelectedItem() == tabClient) {
                    List<Clients> allClient;
                    allClient = tableClients.getItems();
                    for (Clients c : allClient) {
                        if (c.getIdClient()== null) {
                            clientController.create(c);
                            c.state = 0;
                        } else if (c.state == -1) {
                            clientController.edit(c);
                            c.state = 0;
                        }
                    }
                    tableClients.refresh();
                } else if (selectionModel.getSelectedItem() == tabRes) {
                    List<Reservet> allRes;
                    allRes = tableRes.getItems();
                    for (Reservet r : allRes) {
                        if (r.getIdReserv()== null) {
                            resCtrl.create(r);
                        } else if (r.state == -1) {
                            resCtrl.edit(r);
                            r.state = 0;
                        }
                    }                  
                    tableRes.refresh();
                } else if (selectionModel.getSelectedItem() == tabDeliv) {
                    List<Delivery> allDel;
                    allDel = tableDil.getItems();
                    for (Delivery d : allDel) {
                        if (d.getIdDelivery()== null) {
                            delCtrl.create(d);
                        } else if (d.state == -1) {
                            delCtrl.edit(d);
                            d.state = 0;
                        }
                    }
                    tableDil.refresh();
                }
                //<editor-fold defaultstate="collapsed" desc="Обновление комбобоксов">
                clientRes.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Clients>() {
                    @Override
                    public String toString(Clients object) {
                        return object == null ? "" : object.getName();
                    }

                    @Override
                    public Clients fromString(String string) {
                        for (Clients c : clientController.findClientsEntities()) {
                            if (c.getName().equals(string)) {
                                return c;
                            }
                        }
                        return null;
                    }
                }, FXCollections.observableArrayList(clientController.findClientsEntities())));
                wallRes.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Wallpapers>() {
                    @Override
                    public String toString(Wallpapers object) {
                        return object == null ? "" : object.getArticles();
                    }

                    @Override
                    public Wallpapers fromString(String string) {
                        for (Wallpapers w : wallCtrl.findWallpapersEntities()) {
                            if (w.getArticles().equals(string)) {
                                return w;
                            }
                        }
                        return null;
                    }
                }, FXCollections.observableArrayList(wallCtrl.findWallpapersEntities())));
                garRes.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Garages>() {
                    @Override
                    public String toString(Garages object) {
                        return object == null ? "" : object.getName();
                    }

                    @Override
                    public Garages fromString(String string) {
                        for (Garages g : garCtrl.findGaragesEntities()) {
                            if (g.getName().equals(string)) {
                                return g;
                            }
                        }
                        return null;
                    }
                }, FXCollections.observableArrayList(garCtrl.findGaragesEntities())));
                //</editor-fold>
                buttAdd.setDisable(false);
            } catch (Exception ex) {
                Logger.getLogger(DirectoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
        
        private final EventHandler<ActionEvent> eventRem = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                if (selectionModel.getSelectedItem() == tabClient) {
                    
                    Clients remClient = tableClients.getSelectionModel().getSelectedItem();
                    if(remClient.getIdClient() != null)
                    clientController.destroy(remClient.getIdClient());
                    clientList.remove(remClient);
                    tableClients.refresh();
                } else if (selectionModel.getSelectedItem() == tabRes) {
                    Reservet remRes = tableRes.getSelectionModel().getSelectedItem();
                    if(remRes.getIdReserv() != null)
                    resCtrl.destroy(remRes.getIdReserv());
                    resList.remove(remRes);
                    tableRes.refresh();
                } else if (selectionModel.getSelectedItem() == tabDeliv) {
                    Delivery remDel = tableDil.getSelectionModel().getSelectedItem();
                    if(remDel.getIdDelivery() != null)
                    delCtrl.destroy(remDel.getIdDelivery());
                    delList.remove(remDel);
                    tableDil.refresh();
                }
            }catch (NonexistentEntityException ex) {
                Logger.getLogger(DirectoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
        
        
       public void initData(){
        clientList.clear();
        clientList.addAll(clientController.findClientsEntities());
        tableClients.setItems(clientList);
        tableClients.refresh();
        tableClients.getSelectionModel().select(0);
        
        resList.clear();
        resList.addAll(resCtrl.findReservetEntities());
        tableRes.setItems(resList);
        tableRes.refresh();
        tableRes.getSelectionModel().select(0);
        
        delList.clear();
        delList.addAll(delCtrl.findDeliveryEntities());
        tableDil.setItems(delList);
        tableDil.refresh();
        tableDil.getSelectionModel().select(0);
    }
    
     public BorderPane getResPane(){
        return borderRes;
    }
}
