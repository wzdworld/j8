package com.star.mkdocshelper.controller.material.center;

import com.star.mkdocshelper.properties.material.ColorScheme;
import com.star.mkdocshelper.properties.material.Material;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class StyleController {
    private static final List<String> PRIMARY_CHOICES = new ArrayList<>();
    private static final List<String> ACCENT_CHOICES = new ArrayList<>();

    static {
        PRIMARY_CHOICES.add("red");
        PRIMARY_CHOICES.add("pink");
        PRIMARY_CHOICES.add("purple");
        PRIMARY_CHOICES.add("deep-purple");
        PRIMARY_CHOICES.add("indigo");
        PRIMARY_CHOICES.add("blue");
        PRIMARY_CHOICES.add("light-blue");
        PRIMARY_CHOICES.add("cyan");
        PRIMARY_CHOICES.add("teal");
        PRIMARY_CHOICES.add("green");
        PRIMARY_CHOICES.add("light-green");
        PRIMARY_CHOICES.add("lime");
        PRIMARY_CHOICES.add("yellow");
        PRIMARY_CHOICES.add("amber");
        PRIMARY_CHOICES.add("orange");
        PRIMARY_CHOICES.add("deep-orange");
        PRIMARY_CHOICES.add("brown");
        PRIMARY_CHOICES.add("grey");
        PRIMARY_CHOICES.add("blue-grey");
        PRIMARY_CHOICES.add("black");
        PRIMARY_CHOICES.add("white");
    }

    static {
        ACCENT_CHOICES.add("red");
        ACCENT_CHOICES.add("pink");
        ACCENT_CHOICES.add("purple");
        ACCENT_CHOICES.add("deep-purple");
        ACCENT_CHOICES.add("indigo");
        ACCENT_CHOICES.add("blue");
        ACCENT_CHOICES.add("light-blue");
        ACCENT_CHOICES.add("cyan");
        ACCENT_CHOICES.add("teal");
        ACCENT_CHOICES.add("green");
        ACCENT_CHOICES.add("light-green");
        ACCENT_CHOICES.add("lime");
        ACCENT_CHOICES.add("yellow");
        ACCENT_CHOICES.add("amber");
        ACCENT_CHOICES.add("orange");
        ACCENT_CHOICES.add("deep-orange");
    }

    @FXML
    private ComboBox<String> accentDarkComboBox;

    @FXML
    private ComboBox<String> accentDayComboBox;

    @FXML
    private ComboBox<String> primaryDarkComboBox;

    @FXML
    private ComboBox<String> primaryDayComboBox;

    public void initialize() {
        loadInformation();
    }

    private void loadInformation() {
        ColorScheme dayColorScheme = Material.getConfigInstance().getTheme().getPalette().get(0);
        ColorScheme darkColorScheme = Material.getConfigInstance().getTheme().getPalette().get(1);

        // 设置ComboBox的选项集合
        primaryDarkComboBox.setItems(FXCollections.observableArrayList(
                PRIMARY_CHOICES
        ));
        primaryDarkComboBox.setValue(darkColorScheme.getPrimary());
        primaryDayComboBox.setItems(FXCollections.observableArrayList(
                PRIMARY_CHOICES
        ));
        primaryDayComboBox.setValue(dayColorScheme.getPrimary());
        // 设置ComboBox的选项集合
        accentDarkComboBox.setItems(FXCollections.observableArrayList(
                PRIMARY_CHOICES
        ));
        accentDarkComboBox.setValue(darkColorScheme.getAccent());
        accentDayComboBox.setItems(FXCollections.observableArrayList(
                PRIMARY_CHOICES
        ));
        accentDayComboBox.setValue(dayColorScheme.getAccent());
    }

    @FXML
    void saveColor(ActionEvent event) {
        if (Material.getConfigInstance().getTheme() != null
                && Material.getConfigInstance().getTheme().getPalette() != null) {
            // 亮色设置
            ColorScheme dayColorScheme = Material.getConfigInstance().getTheme().getPalette().get(0);
            dayColorScheme.setPrimary(primaryDayComboBox.getValue());
            dayColorScheme.setAccent(accentDayComboBox.getValue());
            // 暗色设置
            ColorScheme darkColorScheme = Material.getConfigInstance().getTheme().getPalette().get(1);
            darkColorScheme.setPrimary(primaryDarkComboBox.getValue());
            darkColorScheme.setAccent(accentDarkComboBox.getValue());
        }

        Material.refreshConfigFile();
    }
}
