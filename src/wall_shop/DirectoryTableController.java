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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import wall_shop.entyties.Color;
import wall_shop.entyties.Companys;
import wall_shop.entyties.Country;
import wall_shop.entyties.Party;
import wall_shop.entyties.Wallpapers;
import wall_shop.entyties.controllers.ColorJpaController;
import wall_shop.entyties.controllers.CompanysJpaController;
import wall_shop.entyties.controllers.CountryJpaController;
import wall_shop.entyties.controllers.PartyJpaController;
import wall_shop.entyties.controllers.WallpapersJpaController;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author hulk-
 */
public class DirectoryTableController implements Initializable {

    public final ObservableList<Wallpapers> wallList = FXCollections.observableArrayList();
    private final ObservableList<Country> countList = FXCollections.observableArrayList();
    private final ObservableList<Color> colorList = FXCollections.observableArrayList();
    private final ObservableList<Party> partyList = FXCollections.observableArrayList();
    private final ObservableList<Companys> compList = FXCollections.observableArrayList();
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Wall_ShopPU");
    private final WallpapersJpaController wallController = new WallpapersJpaController(emf);
    private final CountryJpaController countController = new CountryJpaController(emf);
    private final ColorJpaController colorController = new ColorJpaController(emf);
    private final PartyJpaController partyController = new PartyJpaController(emf);
    private final CompanysJpaController compController = new CompanysJpaController(emf);
    private  SingleSelectionModel<Tab> selectionModel;

    //<editor-fold defaultstate="collapsed" desc="FXMLники">
    @FXML
    private Button wallButtonAdd;
    @FXML
    private Button wallButtonSave;
    @FXML
    private Button wallButtonDel;
    @FXML
    private BorderPane borderDir;
    @FXML
    private TableView<Wallpapers> dopValueWall;
    @FXML
    private TableView<Country> dopValueCount;
    @FXML
    private TableView<Color> dopValueColor;
    @FXML
    private TableView<Party> dopValueParty;
    @FXML 
    private TableView<Companys> dopValueComp;
    @FXML
    private TableColumn<Wallpapers, Integer> idWall;
    @FXML
    private TableColumn<Wallpapers, String> nameWall;
    @FXML
    private TableColumn<Wallpapers, String> articWall;
    @FXML
    private TableColumn<Country, Integer> idCount;
    @FXML
    private TableColumn<Country, String> nameCount;
    @FXML
    private TableColumn<Color, Integer> idColor;
    @FXML
    private TableColumn<Color, String> nameColor;
    @FXML
    private TableColumn<Party, Integer> idParty;
    @FXML
    private TableColumn<Party, String> nameParty;
    @FXML
    private TableColumn<Companys, Integer> idComp;
    @FXML
    private TableColumn<Companys, String> nameComp;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabWall,countTab,colorTab,partyTab,compTab;
    //</editor-fold>
       
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectionModel = tabPane.getSelectionModel();
        selectionModel.select(tabWall);
        dopValueWall.setPlaceholder(new Label(""));
        dopValueCount.setPlaceholder(new Label(""));
        dopValueColor.setPlaceholder(new Label(""));
        dopValueParty.setPlaceholder(new Label(""));
        dopValueComp.setPlaceholder(new Label(""));

        //<editor-fold defaultstate="collapsed" desc="Иницилизация колонок WallPapers">
        idWall.setCellValueFactory((TableColumn.CellDataFeatures<Wallpapers, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getIdWall() == null ? 0 : param.getValue().getIdWall())));

        nameWall.setCellValueFactory((TableColumn.CellDataFeatures<Wallpapers, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getName() == null ? "" : param.getValue().getName())));
        nameWall.setCellFactory(TextFieldTableCell.forTableColumn());
        nameWall.setOnEditCommit((TableColumn.CellEditEvent<Wallpapers, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });

        articWall.setCellValueFactory((TableColumn.CellDataFeatures<Wallpapers, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getArticles() == null ? "" : param.getValue().getArticles())));
        articWall.setCellFactory(TextFieldTableCell.forTableColumn());
        articWall.setOnEditCommit((TableColumn.CellEditEvent<Wallpapers, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setArticles(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });

        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Инициализация колонок Country">
        idCount.setCellValueFactory((TableColumn.CellDataFeatures<Country, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getIdCountry() == null ? 0 : param.getValue().getIdCountry())));
       
        nameCount.setCellValueFactory((TableColumn.CellDataFeatures<Country, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getNamecountry() == null ? "" : param.getValue().getNamecountry())));
        nameCount.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCount.setOnEditCommit((TableColumn.CellEditEvent<Country, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setNamecountry(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Инициализация колонок Color">
        idColor.setCellValueFactory((TableColumn.CellDataFeatures<Color, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getIdColor() == null ? 0 : param.getValue().getIdColor())));
        nameColor.setCellValueFactory((TableColumn.CellDataFeatures<Color, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getColors() == null ? "" : param.getValue().getColors())));
        nameColor.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColor.setOnEditCommit((TableColumn.CellEditEvent<Color, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setColors(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Инициализация колонок Party">
        idParty.setCellValueFactory((TableColumn.CellDataFeatures<Party, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getIdParty() == null ? 0 : param.getValue().getIdParty())));
        nameParty.setCellValueFactory((TableColumn.CellDataFeatures<Party, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getPartynum() == null ? "" : param.getValue().getPartynum())));
        nameParty.setCellFactory(TextFieldTableCell.forTableColumn());
        nameParty.setOnEditCommit((TableColumn.CellEditEvent<Party, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPartynum(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        //</editor-fold>
       
        //<editor-fold defaultstate="collapsed" desc="Инициализация колонок Companys">
        idComp.setCellValueFactory((TableColumn.CellDataFeatures<Companys, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getIdCompany() == null ? 0 : param.getValue().getIdCompany())));
        nameComp.setCellValueFactory((TableColumn.CellDataFeatures<Companys, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getNamecompany() == null ? "" : param.getValue().getNamecompany())));
        nameComp.setCellFactory(TextFieldTableCell.forTableColumn());
        nameComp.setOnEditCommit((TableColumn.CellEditEvent<Companys, String> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setNamecompany(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        //</editor-fold>
        wallButtonAdd.addEventHandler(ActionEvent.ACTION, eventWallAdd);
        wallButtonSave.addEventHandler(ActionEvent.ACTION, eventSave);
        wallButtonDel.addEventHandler(ActionEvent.ACTION, eventDelete);
    }

    private final EventHandler<ActionEvent> eventWallAdd = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (selectionModel.getSelectedItem() == tabWall) {
                Wallpapers wl = new Wallpapers();
                wallList.add(wl);
                dopValueWall.refresh();
                dopValueWall.getSelectionModel().select(wl);
            } else if (selectionModel.getSelectedItem() == countTab) {
                Country ct = new Country();
                countList.add(ct);
                dopValueCount.refresh();
                dopValueCount.getSelectionModel().select(ct);
            } else if (selectionModel.getSelectedItem() == colorTab) {
                Color cl = new Color();
                colorList.add(cl);
                dopValueColor.refresh();
                dopValueColor.getSelectionModel().select(cl);
            }else if(selectionModel.getSelectedItem() == partyTab){
                Party pt = new Party();
                partyList.add(pt);
                dopValueParty.refresh();
                dopValueParty.getSelectionModel().select(pt);
            }else if(selectionModel.getSelectedItem() == compTab){
            Companys cm = new Companys();
            compList.add(cm);
            dopValueComp.refresh();
            dopValueComp.getSelectionModel().select(cm);
            }

            wallButtonAdd.setDisable(true);
        }
    };

    private final EventHandler<ActionEvent> eventDelete = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (selectionModel.getSelectedItem() == tabWall) {
                    Wallpapers remItem = dopValueWall.getSelectionModel().getSelectedItem();
                    wallController.destroy(remItem.getIdWall());
                    wallList.remove(remItem);
                    dopValueWall.refresh();
                } else if (selectionModel.getSelectedItem() == countTab) {
                    Country remCount = dopValueCount.getSelectionModel().getSelectedItem();
                    countController.destroy(remCount.getIdCountry());
                    countList.remove(remCount);
                    dopValueCount.refresh();
                } else if (selectionModel.getSelectedItem() == colorTab) {
                    Color remColor = dopValueColor.getSelectionModel().getSelectedItem();
                    colorController.destroy(remColor.getIdColor());
                    colorList.remove(remColor);
                    dopValueColor.refresh();
                } else if (selectionModel.getSelectedItem() == partyTab) {
                    Party remParty = dopValueParty.getSelectionModel().getSelectedItem();
                    partyController.destroy(remParty.getIdParty());
                    partyList.remove(remParty);
                    dopValueParty.refresh();
                } else if(selectionModel.getSelectedItem() == compTab){
                    Companys remComp = dopValueComp.getSelectionModel().getSelectedItem();
                    compController.destroy(remComp.getIdCompany());
                    compList.remove(remComp);
                    dopValueComp.refresh();
                }
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(DirectoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    private EventHandler<ActionEvent> eventSave = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (selectionModel.getSelectedItem() == tabWall) {
                    List<Wallpapers> allWall;
                    allWall = dopValueWall.getItems();
                    for (Wallpapers w : allWall) {
                        if (w.getIdWall() == null) {
                            wallController.create(w);
                            w.state = 0;
                        } else if (w.state == -1) {
                            wallController.edit(w);
                            w.state = 0;
                        }
                    }
                    dopValueWall.refresh();
                } else if (selectionModel.getSelectedItem() == countTab) {
                    List<Country> allCount;
                    allCount = dopValueCount.getItems();
                    for (Country c : allCount) {
                        if (c.getIdCountry() == null) {
                            countController.create(c);
                        } else if (c.state == -1) {
                            countController.edit(c);
                            c.state = 0;
                        }
                    }
                    dopValueCount.refresh();
                } else if (selectionModel.getSelectedItem() == colorTab) {
                    List<Color> allColor;
                    allColor = dopValueColor.getItems();
                    for (Color c : allColor) {
                        if (c.getIdColor() == null) {
                            colorController.create(c);
                        } else if (c.state == -1) {
                            colorController.edit(c);
                            c.state = 0;
                        }
                    }
                    dopValueColor.refresh();
                }else if(selectionModel.getSelectedItem() == partyTab){
                    List<Party> allParty;
                    allParty = dopValueParty.getItems();
                    for (Party p : allParty) {
                        if (p.getIdParty() == null) {
                            partyController.create(p);
                        } else if (p.state == -1) {
                            partyController.edit(p);
                            p.state = 0;
                        }
                    }
                dopValueParty.refresh();
                } else if(selectionModel.getSelectedItem() == compTab){
                    List<Companys> allComp;
                    allComp = dopValueComp.getItems();
                    for (Companys c : allComp) {
                        if (c.getIdCompany() == null) {
                            compController.create(c);
                        } else if (c.state == -1) {
                            compController.edit(c);
                            c.state = 0;
                        }
                    }
                    dopValueComp.refresh();
                }

                wallButtonAdd.setDisable(false);
            } catch (Exception ex) {
                Logger.getLogger(DirectoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public void initData() {
        wallList.clear();
        wallList.addAll(wallController.findWallpapersEntities());
        dopValueWall.setItems(wallList);
        dopValueWall.refresh();
        dopValueWall.getSelectionModel().select(0);
        
        countList.clear();
        countList.addAll(countController.findCountryEntities());
        dopValueCount.setItems(countList);
        dopValueCount.refresh();
        dopValueCount.getSelectionModel().select(0);
        
        colorList.clear();
        colorList.addAll(colorController.findColorEntities());
        dopValueColor.setItems(colorList);
        dopValueColor.refresh();
        dopValueColor.getSelectionModel().select(0);
        
        partyList.clear();
        partyList.addAll(partyController.findPartyEntities());
        dopValueParty.setItems(partyList);
        dopValueParty.refresh();
        dopValueParty.getSelectionModel().select(0);
        
        compList.clear();
        compList.addAll(compController.findCompanysEntities());
        dopValueComp.setItems(compList);
        dopValueComp.refresh();
        dopValueComp.getSelectionModel().select(0);
    }

    public BorderPane getDopValuePane() {
        return borderDir;
    }
}
