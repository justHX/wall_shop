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
import javafx.util.converter.IntegerStringConverter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import wall_shop.entyties.Color;
import wall_shop.entyties.Companys;
import wall_shop.entyties.Country;
import wall_shop.entyties.Party;
import wall_shop.entyties.Provider;
import wall_shop.entyties.Wallpapers;
import wall_shop.entyties.controllers.ColorJpaController;
import wall_shop.entyties.controllers.CompanysJpaController;
import wall_shop.entyties.controllers.CountryJpaController;
import wall_shop.entyties.controllers.PartyJpaController;
import wall_shop.entyties.controllers.ProviderJpaController;
import wall_shop.entyties.controllers.WallpapersJpaController;
import wall_shop.entyties.controllers.exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author hulk-
 */
public class ProviderTableController implements Initializable {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Wall_ShopPU");
    private final WallpapersJpaController wallController = new WallpapersJpaController(emf);
    private final ColorJpaController colorController = new ColorJpaController(emf);
    private final CompanysJpaController companyController = new CompanysJpaController(emf);
    private final CountryJpaController countryController = new CountryJpaController(emf);
    private final PartyJpaController partyController = new PartyJpaController(emf);
    private final ProviderJpaController providerCtrl = new ProviderJpaController(emf);
    private  ObservableList<Provider> providerList = FXCollections.observableArrayList();
    private  SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    
    
    //<editor-fold defaultstate="collapsed" desc="FXMLники">
    @FXML
    private BorderPane borderProvider;
    @FXML
    private TableView<Provider> provider;
    @FXML
    private TableColumn<Provider, Integer> idColumn;
    @FXML
    private TableColumn<Provider, String> dateColumn;
    @FXML
    private TableColumn<Provider, Wallpapers> wallColumn;
    @FXML
    private TableColumn<Provider, Color> colorColumn;
    @FXML
    private TableColumn<Provider, Companys> providerColumn;
    @FXML
    private TableColumn<Provider, Country> countryColumn;
    @FXML
    private TableColumn<Provider, Party> partyColumn;
    @FXML
    private TableColumn<Provider, Integer> sizeColumn;
    @FXML
    private TableColumn<Provider, Float> moneyColumn;
    @FXML
    private Button providerButtonAdd, providerButtonSave, providerButtonRem;
    //</editor-fold>
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        provider.setPlaceholder(new Label(""));
        
        //<editor-fold defaultstate="collapsed" desc="Инициализация колонок">
        idColumn.setCellValueFactory((TableColumn.CellDataFeatures<Provider, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : (param.getValue().getIdProvider() == null ? 0 : param.getValue().getIdProvider())));

        dateColumn.setCellValueFactory((TableColumn.CellDataFeatures<Provider, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? "" : (param.getValue().getDatedelivery() == null ? "" : format.format(param.getValue().getDatedelivery()))));
        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setOnEditCommit((TableColumn.CellEditEvent<Provider, String> event) -> {
            Date date = new Date();
            try {
                date = format.parse(event.getNewValue());
            } catch (ParseException ex) {
                Logger.getLogger(ProviderTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDatedelivery(date);
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });

        wallColumn.setCellValueFactory((TableColumn.CellDataFeatures<Provider, Wallpapers> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getIdWall() == null ? null : param.getValue().getIdWall())));
        wallColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Wallpapers>() {
            @Override
            public String toString(Wallpapers object) {
                return object == null ? "" : object.getArticles();
            }

            @Override
            public Wallpapers fromString(String string) {
                for (Wallpapers w : wallController.findWallpapersEntities()) {
                    if (w.getArticles().equals(string)) {
                        return w;
                    }
                }
                return null;
            }
        }, FXCollections.observableArrayList(wallController.findWallpapersEntities())));
        wallColumn.setOnEditCommit((TableColumn.CellEditEvent<Provider, Wallpapers> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIdWall(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });

        providerColumn.setCellValueFactory((TableColumn.CellDataFeatures<Provider, Companys> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getIdCompany() == null ? null : param.getValue().getIdCompany())));
        providerColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Companys>() {
            @Override
            public String toString(Companys object) {
                return object == null ? null : object.getNamecompany();
            }

            @Override
            public Companys fromString(String string) {
                for (Companys c : companyController.findCompanysEntities()) {
                    if (c.getNamecompany().equals(string)) {
                        return c;
                    }
                }
                return null;
            }
        }, FXCollections.observableArrayList(companyController.findCompanysEntities())));
        providerColumn.setOnEditCommit((TableColumn.CellEditEvent<Provider, Companys> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIdCompany(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });

        colorColumn.setCellValueFactory((TableColumn.CellDataFeatures<Provider, Color> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getIdColor() == null ? null : param.getValue().getIdColor())));
        colorColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Color>() {
            @Override
            public String toString(Color object) {
                return object == null ? "" : object.getColors();
            }

            @Override
            public Color fromString(String string) {
                for (Color c : colorController.findColorEntities()) {
                    if (c.getColors().equals(string));
                    return c;
                }
                return null;
            }
        }, FXCollections.observableArrayList(colorController.findColorEntities())));
        colorColumn.setOnEditCommit((TableColumn.CellEditEvent<Provider, Color> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIdColor(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });

        countryColumn.setCellValueFactory((TableColumn.CellDataFeatures<Provider, Country> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getIdCountry() == null ? null : param.getValue().getIdCountry())));
        countryColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Country>() {
            @Override
            public String toString(Country object) {
                return object == null ? "" : object.getNamecountry();
            }

            @Override
            public Country fromString(String string) {
                for (Country c : countryController.findCountryEntities()) {
                    if (c.getNamecountry().equals(string)) {
                        return c;
                    }
                }
                return null;
            }
        }, FXCollections.observableArrayList(countryController.findCountryEntities())));
        countryColumn.setOnEditCommit((TableColumn.CellEditEvent<Provider, Country> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIdCountry(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });

        partyColumn.setCellValueFactory((TableColumn.CellDataFeatures<Provider, Party> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? null : (param.getValue().getIdParty() == null ? null : param.getValue().getIdParty())));
        partyColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Party>() {
            @Override
            public String toString(Party object) {
                return object == null ? "" : object.getPartynum();
            }

            @Override
            public Party fromString(String string) {
                for (Party p : partyController.findPartyEntities()) {
                    if (p.getPartynum().equals(string)) {
                        return p;
                    }
                }
                return null;
            }
        }, FXCollections.observableArrayList(partyController.findPartyEntities())));
        partyColumn.setOnEditCommit((TableColumn.CellEditEvent<Provider, Party> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIdParty(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });

        sizeColumn.setCellValueFactory((TableColumn.CellDataFeatures<Provider, Integer> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : param.getValue().getNumberroll()));
        sizeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        sizeColumn.setOnEditCommit((TableColumn.CellEditEvent<Provider, Integer> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setNumberroll(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        moneyColumn.setCellValueFactory((TableColumn.CellDataFeatures<Provider, Float> param) -> new ReadOnlyObjectWrapper<>(param.getValue() == null ? 0 : param.getValue().getMoney()));
        moneyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        moneyColumn.setOnEditCommit((TableColumn.CellEditEvent<Provider, Float> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setMoney(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).state = -1;
        });
        
        //</editor-fold>
        
        providerButtonAdd.addEventHandler(ActionEvent.ACTION, eventButtonAdd);
        providerButtonSave.addEventHandler(ActionEvent.ACTION, eventButtonSave);
        providerButtonRem.addEventHandler(ActionEvent.ACTION, eventButtonRem);
    }    
    
    private final EventHandler<ActionEvent> eventButtonAdd = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Provider pr = new Provider();
            providerList.add(pr);
            provider.refresh();
            provider.getSelectionModel().select(pr);
            providerButtonAdd.setDisable(true);
        }
    };
    
    private final EventHandler<ActionEvent> eventButtonSave = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                List<Provider> allProv;
                allProv = provider.getItems();
                for (Provider p : allProv) {
                    if (p.getIdProvider() == null) {
                        providerCtrl.create(p);
                    } else if (p.state == -1) {
                        providerCtrl.edit(p);
                        p.state = 0;
                    }
                }
                provider.refresh();
            } catch (Exception ex) {
                Logger.getLogger(ProviderTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //<editor-fold defaultstate="collapsed" desc="Обновения комбобоксов">
            wallColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Wallpapers>() {
            @Override
            public String toString(Wallpapers object) {
                return object == null ? "" : object.getArticles();
            }

            @Override
            public Wallpapers fromString(String string) {
                for (Wallpapers w : wallController.findWallpapersEntities()) {
                    if (w.getArticles().equals(string)) {
                        return w;
                    }
                }
                return null;
            }
        }, FXCollections.observableArrayList(wallController.findWallpapersEntities())));
            providerColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Companys>() {
            @Override
            public String toString(Companys object) {
                return object == null ? null : object.getNamecompany();
            }

            @Override
            public Companys fromString(String string) {
                for (Companys c : companyController.findCompanysEntities()) {
                    if (c.getNamecompany().equals(string)) {
                        return c;
                    }
                }
                return null;
            }
        }, FXCollections.observableArrayList(companyController.findCompanysEntities())));
            colorColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Color>() {
            @Override
            public String toString(Color object) {
                return object == null ? "" : object.getColors();
            }

            @Override
            public Color fromString(String string) {
                for (Color c : colorController.findColorEntities()) {
                    if (c.getColors().equals(string));
                    return c;
                }
                return null;
            }
        }, FXCollections.observableArrayList(colorController.findColorEntities())));
            partyColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Party>() {
            @Override
            public String toString(Party object) {
                return object == null ? "" : object.getPartynum();
            }

            @Override
            public Party fromString(String string) {
                for (Party p : partyController.findPartyEntities()) {
                    if (p.getPartynum().equals(string)) {
                        return p;
                    }
                }
                return null;
            }
        }, FXCollections.observableArrayList(partyController.findPartyEntities())));
            //</editor-fold>
            providerButtonAdd.setDisable(false);
        }
    };
    
    private final EventHandler<ActionEvent> eventButtonRem = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Provider remProv = provider.getSelectionModel().getSelectedItem();
                providerCtrl.destroy(remProv.getIdProvider());
                providerList.remove(remProv);
                provider.refresh();
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProviderTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    public void initData()
    {
        providerList.clear();
        providerList.addAll(providerCtrl.findProviderEntities());
        provider.setItems(providerList);
        provider.refresh();
        provider.getSelectionModel().select(0);
    }
    
     public BorderPane getProviderPane(){
        return borderProvider;
    }
}
