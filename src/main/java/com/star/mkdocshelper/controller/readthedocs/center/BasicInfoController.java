package com.star.mkdocshelper.controller.readthedocs.center;

import com.star.mkdocshelper.properties.readthedocs.Readthedocs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BasicInfoController {

    @FXML
    private Label s;

    @FXML
    private TextField siteAuthorTextField;

    @FXML
    private TextArea siteDescriptionTextArea;

    @FXML
    private TextField siteNameTextField;

    @FXML
    void save(ActionEvent event) {
        Readthedocs.getConfigInstance().setSite_name(siteNameTextField.getText());
        Readthedocs.getConfigInstance().setSite_author(siteAuthorTextField.getText());
        Readthedocs.getConfigInstance().setSite_description(siteDescriptionTextArea.getText());

        Readthedocs.refreshConfigFile();
    }

    public void initialize() {
        loadInformation();
    }

    private void loadInformation() {
        siteNameTextField.setText(Readthedocs.getConfigInstance().getSite_name());
        siteAuthorTextField.setText(Readthedocs.getConfigInstance().getSite_author());
        siteDescriptionTextArea.setText(Readthedocs.getConfigInstance().getSite_description());
    }

    @FXML
    void refreshInfo(ActionEvent event) {
        loadInformation();
    }

}
