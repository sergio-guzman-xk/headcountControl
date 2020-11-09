package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class camp_update_deleteController {

    @FXML
    private AnchorPane campaignsAnchorPane;

    @FXML
    private TableView<Campaign> showCampTable;
    @FXML
    private TableColumn<Campaign, String> colCampBatchUid;
    @FXML
    private TableColumn<Campaign, String> colCampCampID;
    @FXML
    private TableColumn<Campaign, String> colCampName;
    @FXML
    private TableColumn<Campaign, String> colCampHeadReq;
    @FXML
    private TableColumn<Campaign, String> colCampPrio;
    @FXML
    private TableColumn<Campaign, String> colCampRev;
    @FXML
    private TableColumn<Campaign, String> colCampConDate;
    @FXML
    private TableColumn<Campaign, String> colCampRenDate;

    @FXML
    private TextField showCampBatchUid;
    @FXML
    private TextField showCampCampId;
    @FXML
    private TextField showCampName;
    @FXML
    private TextField showCampHeadReq;
    @FXML
    private TextField showCampPrior;
    @FXML
    private TextField showCampRevenue;
    @FXML
    private TextField showCampConDate;
    @FXML
    private TextField showCampRenDate;

    private String campBatchUidData;
    private String campCampIdData;
    private String campNameData;
    private String campHeadReqData;
    private String campPriorData;
    private String campRevenueData;
    private String campConDateData;
    private String campRenDateData;

    AppData newData = new AppData();

    public void campaignPaneSearch () throws SQLException {
        showCampTable.getItems().clear();
        campBatchUidData = showCampBatchUid.getText().trim();
        campCampIdData = showCampCampId.getText().trim();
        campNameData = showCampName.getText().trim();
        campHeadReqData = showCampHeadReq.getText().trim();
        campPriorData = showCampPrior.getText().trim();
        campRevenueData = showCampRevenue.getText().trim();
        campConDateData = showCampConDate.getText().trim();
        campRenDateData = showCampRenDate.getText().trim();

        // -- Define a list of Campaign objects --
        List<Campaign> campaigns;
        // -- Checks if there are any search parameters --
        if (campBatchUidData.isEmpty() & campNameData.isEmpty() & campCampIdData.isEmpty() & campConDateData.isEmpty()
                & campRenDateData.isEmpty() & campHeadReqData.isEmpty() & campRevenueData.isEmpty() & campPriorData.isEmpty()) {
            campaigns = newData.queryCampaign();
        } else {
            campaigns = newData.queryCampaign(campBatchUidData, campNameData, campCampIdData, campConDateData, campRenDateData,
                    campHeadReqData, campRevenueData, campPriorData);
        }
        // -- Checks if there are any results being returned from the query --
        if (campaigns.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("No results");
            alert.showAndWait();
        } else {
            for (Campaign entry : campaigns) {
                showCampTable.getItems().addAll(entry);
                colCampBatchUid.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBatch_uid()));
                colCampCampID.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCampaign_id()));
                colCampName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
                colCampHeadReq.setCellValueFactory(c -> new SimpleStringProperty(String.format("%.0f", c.getValue().getHeadcount_req())));
                colCampPrio.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPriority()));
                colCampRev.setCellValueFactory(c -> new SimpleStringProperty(String.format("%.0f", c.getValue().getRevenue())));
                colCampConDate.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getContract_date())));
                colCampRenDate.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getRenew_date())));

            }
        }
    }

    // -- Something is selected in the table view --
    public void campaignSelected() {
        Campaign selectedCampaign = showCampTable.getSelectionModel().getSelectedItem();
        if(selectedCampaign != null) {
            showCampBatchUid.setText(selectedCampaign.getBatch_uid());
            showCampCampId.setText(selectedCampaign.getCampaign_id());
            showCampName.setText(selectedCampaign.getName());
            showCampHeadReq.setText(String.format("%.0f",selectedCampaign.getHeadcount_req()));
            showCampPrior.setText(selectedCampaign.getPriority());
            showCampRevenue.setText(String.format("%.0f",selectedCampaign.getRevenue()));
            showCampConDate.setText(String.valueOf(selectedCampaign.getContract_date()));
            showCampRenDate.setText(String.valueOf(selectedCampaign.getRenew_date()));
            System.out.println(selectedCampaign.getPk1());
        }
    }

    // -- Create Button --
    public void campaignPaneCreate() {
        // -- Creates and empty campaign object --
        Campaign newCampaign = new Campaign();
        campBatchUidData = showCampBatchUid.getText().trim();
        campCampIdData = showCampCampId.getText().trim();
        campNameData = showCampName.getText().trim();
        campHeadReqData = showCampHeadReq.getText().trim();
        campPriorData = showCampPrior.getText().trim();
        campRevenueData = showCampRevenue.getText().trim();
        campConDateData = showCampConDate.getText().trim();
        campRenDateData = showCampRenDate.getText().trim();

        // -- Checks if there are any empty fields --
        if(campBatchUidData.isEmpty() || campCampIdData.isEmpty() || campNameData.isEmpty() ||
                campHeadReqData.isEmpty() || campPriorData.isEmpty() || campRevenueData.isEmpty() ||
                campConDateData.isEmpty() || campRenDateData.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("One or more fields are empty. The records was not created.");
            alert.showAndWait();
        } else {
            try {
                Date campConDateData = Date.valueOf(showCampConDate.getText().trim());
                Date campRenDateData = Date.valueOf(showCampRenDate.getText().trim());
                try {
                    double campHeadReqData = (Double.parseDouble(showCampHeadReq.getText().trim()));
                    double campRevenueData = (Double.parseDouble(showCampRevenue.getText().trim()));
                    // -- Use set methods to set the the values of the campaign object created before --
                    newCampaign.setBatch_uid(campBatchUidData);
                    newCampaign.setName(campNameData);
                    newCampaign.setCampaign_id(campCampIdData);
                    newCampaign.setContract_date(campConDateData);
                    newCampaign.setRenew_date(campRenDateData);
                    newCampaign.setHeadcount_req(campHeadReqData);
                    newCampaign.setRevenue(campRevenueData);
                    newCampaign.setPriority(campPriorData);
                    // -- Run the insertCampaign() to create the campaign in the DB. Assign the returned value to success --
                    int success = newData.insertCampaign(newCampaign);
                    // -- If it returns 0 is because 0 rows where modified thus the above was not execute --
                    // -- If the above was a success then refresh the tableview to show the new data --
                    if(success != 0) {
                        campaignPaneSearch();
                    }
                } catch (Exception e) {
                    System.out.println("Empty or invalid value" + e.getCause());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(e.getMessage());
                    alert.showAndWait();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR: Date format must be yyyy-mm-dd. " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    // -- Update Button. Needs a selected item from the table view --
    public void campaignPaneUpdate() {
        Campaign selectedCampaign = showCampTable.getSelectionModel().getSelectedItem();
        if(selectedCampaign != null) {
            try {
                // -- checks if the double values are double --
                double newCampHeadReq = Double.parseDouble(showCampHeadReq.getText());
                double newCampRev = Double.parseDouble(showCampRevenue.getText());
                try {
                    // -- checks if the data is a value date format yyyy-mm-dd --
                    Date newCampConDate = Date.valueOf(showCampConDate.getText());
                    Date newCampRenDate = Date.valueOf(showCampRenDate.getText());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("UPDATE RECORD");
                    alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && (result.get() == ButtonType.OK)) {
                        /* -- If the user hit ok in the confirmation box it calls the overload method updateEmployee()
                         * and stores the returned value -- */
                        int success = newData.updateCampaign(selectedCampaign, showCampBatchUid.getText().trim(), showCampName.getText().trim(),
                                showCampCampId.getText().trim(), newCampConDate, newCampRenDate,
                                newCampHeadReq, newCampRev, showCampPrior.getText().trim());
                        // If the value is different from 0 it was success, then it will refresh the tableview to show the updated record --
                        if(success != 0){
                            campaignPaneSearch();
                        }
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("ERROR: Date format must be yyyy-mm-dd. " + e.getMessage());
                    alert.showAndWait();
                }
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            System.out.println("Nothing is selected");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DELETE RECORD");
            alert.setHeaderText("Nothing is selected");
            alert.showAndWait();
        }
    }

    // -- Delete Button. Requires an item selected in the tableview --
    public void campaignPaneDelete() throws SQLException {
        Campaign selectedCampaign= showCampTable.getSelectionModel().getSelectedItem();
        if(selectedCampaign != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DELETE RECORD");
            alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && (result.get() == ButtonType.OK)) {
                // -- Calls deleteCampaign() and then resets all the fields in the UI and refreshes the tableview --
                newData.deleteCampaign(selectedCampaign);
                campaignPaneReset();
                campaignPaneSearch();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DELETE RECORD");
            alert.setHeaderText("Nothing is selected");
            alert.showAndWait();
        }
    }

    // -- Reset Button --
    public void campaignPaneReset() {
        showCampBatchUid.clear();
        showCampCampId.clear();
        showCampName.clear();
        showCampHeadReq.clear();
        showCampPrior.clear();
        showCampRevenue.clear();
        showCampConDate.clear();
        showCampRenDate.clear();
        showCampTable.getItems().clear();
    }

    // -- Exit Button. This changes the Scene to return to the Main Window --
    public void campExitHandler() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT");
        alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && (result.get() == ButtonType.OK)) {
            AppData.sceneChange(campaignsAnchorPane.getScene(), "mainWindow.fxml");
        }
    }
}
