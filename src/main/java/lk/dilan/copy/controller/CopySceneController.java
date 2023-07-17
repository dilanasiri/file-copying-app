package lk.dilan.copy.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class CopySceneController {

    @FXML
    private Button btnCopy;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSource;

    @FXML
    private Button btnTarget;

    @FXML
    private Label lblStatus;

    @FXML
    private ProgressBar prgCopy;

    @FXML
    private TextField txtSource;

    @FXML
    private TextField txtTarget;
    private File sourceFile;

    @FXML
    void btnCopyOnAction(ActionEvent event) {

    }

    @FXML
    void btnSourceOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to copy");
        sourceFile=fileChooser.showOpenDialog(btnCopy.getScene().getWindow());
        enableCopyButton();
        if (sourceFile==null) return;
        txtSource.setText(sourceFile.getAbsolutePath());
    }

    private void enableCopyButton() {
        btnCopy.setDisable(sourceFile==null);
    }

    @FXML
    void btnTargetOnAction(ActionEvent event) {


    }

    @FXML
    void ntmResetOnAction(ActionEvent event) {

    }

}
