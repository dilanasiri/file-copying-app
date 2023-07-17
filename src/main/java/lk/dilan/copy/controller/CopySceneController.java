package lk.dilan.copy.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Optional;

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
    private File targetFolder;
public void initialize(){
    btnCopy.setDisable(true);
}

    @FXML
    void btnCopyOnAction(ActionEvent event) {
        File targetFile = new File(targetFolder, sourceFile.getName());
        if (targetFile.exists()){
            Optional<ButtonType> optResult = new Alert(Alert.AlertType.CONFIRMATION, "File already exists, are you sure to replace the file?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (optResult.isEmpty() || optResult.get()==ButtonType.NO) return;
        }
        btnCopy.getScene().getWindow().setHeight(325);
        Task task=new Task() {
            @Override
            protected Object call() throws Exception {
                FileInputStream fis = new FileInputStream(sourceFile);
                FileOutputStream fos = new FileOutputStream(targetFile);

                double write=0.0;
                while (true){
                    byte[] buffer = new byte[1024 * 10];
                    int read = fis.read(buffer);
                    if (read==-1) break;
                    fos.write(buffer,0,read);
                    write +=read;
                    updateMessage(String.format("%2.2f",write/sourceFile.length() *100).concat("% Complete"));
                    updateProgress(write,sourceFile.length());
                }
                fis.close();
                fos.close();
                return null;
            }
        };
        task.exceptionProperty().addListener(observable -> {
            task.getException().printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong,please try again!!!").show();
        });
        lblStatus.textProperty().bind(task.messageProperty());
        prgCopy.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
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
        btnCopy.setDisable(sourceFile==null || targetFolder ==null || sourceFile.getParentFile().equals(targetFolder));
    }

    @FXML
    void btnTargetOnAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select the destination folder");
        targetFolder =directoryChooser.showDialog(btnTarget.getScene().getWindow());
        enableCopyButton();
        if (targetFolder ==null) return;
        txtTarget.setText(targetFolder.getAbsolutePath());
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        txtSource.clear();
        txtTarget.clear();
        enableCopyButton();
        prgCopy.setProgress(0);
        lblStatus.setText("0% Complete");
        txtSource.requestFocus();
    }

}
