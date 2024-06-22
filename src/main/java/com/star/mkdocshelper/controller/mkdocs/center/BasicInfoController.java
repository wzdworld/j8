package com.star.mkdocshelper.controller.mkdocs.center;

import com.star.mkdocshelper.properties.mkdocs.MkDocs;
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
        MkDocs.getConfigInstance().setSite_name(siteNameTextField.getText());
        MkDocs.getConfigInstance().setSite_author(siteAuthorTextField.getText());
        MkDocs.getConfigInstance().setSite_description(siteDescriptionTextArea.getText());

        MkDocs.refreshConfigFile();
    }

    public void initialize() {
        loadInformation();
    }

    private void loadInformation() {
        siteNameTextField.setText(MkDocs.getConfigInstance().getSite_name());
        siteAuthorTextField.setText(MkDocs.getConfigInstance().getSite_author());
        siteDescriptionTextArea.setText(MkDocs.getConfigInstance().getSite_description());
    }

    @FXML
    void refreshInfo(ActionEvent event) {
        loadInformation();
    }

}
