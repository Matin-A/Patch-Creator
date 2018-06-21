import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

/**
 * Created by Matin Afkhami on 6/16/2018.
 * Java 10 is recommended
 */

public class Controller {

    private double x = 0;
    private double y = 0;
    private static PatchCreator patchCreator;
    private static String message;
    private static Color messageColor;
    private static boolean inTask = false;
    private static boolean startIsDisable;
    private static String oldPath;
    private static String newPath;
    private static String saveTo;
    private static ScheduledExecutorService patchService;

    @FXML Button cancel;
    @FXML Button start;
    @FXML Button exit;
    @FXML Button minimize;
    @FXML Button info;
    @FXML Label textMessage;
    @FXML TextField oldPathT;
    @FXML TextField newPathT;
    @FXML TextField saveToT;

    public void initialize(){
        if (oldPathT!=null){
            oldPathT.setText(oldPath);
        }
        if (newPathT!=null){
            newPathT.setText(newPath);
        }
        if (saveToT!=null){
            saveToT.setText(saveTo);
        }
        if (start!=null){
            start.setDisable(startIsDisable);
        }
        if (textMessage!=null){
            textMessage.setText(message);
            textMessage.setTextFill(messageColor);
        }
    }

    public void startClicked() {
        if (Objects.equals(oldPathT.getText(), "")
                || Objects.equals(newPathT.getText(), "")
                || Objects.equals(saveToT.getText(), "")){
            textMessage.setTextFill(RED);
            messageColor = RED;
            message = "Keyword or Path couldn't be empty.";
            textMessage.setText(message);
        }else if (!new File(oldPathT.getText()).exists()
                || !new File(newPathT.getText()).exists()
                || !new File(saveToT.getText()).exists()) {
            textMessage.setTextFill(RED);
            messageColor = RED;
            message = "Path not exists.";
            textMessage.setText(message);

        }else {
            patchService = Executors.newSingleThreadScheduledExecutor();
            patchService.schedule(this::createPatch,0,TimeUnit.MILLISECONDS);
        }
    }

    private void createPatch() {
        inTask = true;
        start.setDisable(true);
        startIsDisable = true;
        oldPath = oldPathT.getText();
        newPath = newPathT.getText();
        saveTo = saveToT.getText();
        try {
            patchCreator = new PatchCreator(new File(oldPath),new File(newPath),new File(saveTo));
        } catch (Exception ignored) {}
        try {
            textMessage.setTextFill(BLACK);
            messageColor = BLACK;
            Platform.runLater(() -> textMessage.setText("Copying Files..."));
            message = "Copying Files...";
            patchCreator.extractPatch();
            textMessage.setTextFill(GREEN);
            messageColor = GREEN;
            Platform.runLater(() -> textMessage.setText("Patch Successfully Created."));
            message = "Patch Successfully Created.";
        } catch(Exception e){
            textMessage.setTextFill(RED);
            messageColor = RED;
            Platform.runLater(() -> textMessage.setText("Something went wrong.\nSome/All file(s) may not be copied."));
            message = "Something went wrong.\nSome/All file(s) may not be copied.";
        }
        start.setDisable(false);
        startIsDisable = false;
        inTask = false;
        patchService.shutdown();
    }


    public void oldFileChooserClicked() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Directory");
        File selectedDirectory = chooser.showDialog(Main.primaryStage);
        if (selectedDirectory!=null){
            oldPathT.setText(selectedDirectory.getPath());
        }

    }

    public void oldOpenClicked() {
        openClicked(oldPathT);
    }

    public void newFileChooserClicked() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Directory");
        File selectedDirectory = chooser.showDialog(Main.primaryStage);
        if (selectedDirectory!=null){
            newPathT.setText(selectedDirectory.getPath());
        }
    }

    public void newOpenClicked() {
        openClicked(newPathT);
    }

    public void saveToFileChooserClicked() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Directory");
        File selectedDirectory = chooser.showDialog(Main.primaryStage);
        if (selectedDirectory!=null){
            saveToT.setText(selectedDirectory.getPath());
        }
    }

    public void saveToOpenClicked() {
        openClicked(saveToT);
    }

    private void openClicked(TextField saveToT) {
        if (saveToT.getText()!=null){
            if (!new File(saveToT.getText()).exists()) {
                textMessage.setTextFill(RED);
                messageColor = RED;
                message = "Path not exists.";
                textMessage.setText(message);
            }else {
                try {
                    Desktop.getDesktop().open(new File(saveToT.getText()));
                } catch (IOException ignored) {}
            }
        }
    }


    public void telegramClicked() {
        try {
            Desktop.getDesktop().browse(URI.create("https://t.me/MatinAfkhami"));
        } catch (IOException ignored) {}
    }

    public void googlePlusClicked() {
        try {
            Desktop.getDesktop().browse(URI.create("http://google.com/+MatinAfkhami"));
        } catch (IOException ignored) {}
    }

    public void githubClicked() {
        try {
            Desktop.getDesktop().browse(URI.create("https://github.com/Matin-A"));
        } catch (IOException ignored) {}
    }


    public void infoOpen() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/infoForm.fxml"));
        root.setOnMousePressed(e -> {
            x = e.getSceneX();
            y = e.getSceneY();
        });
        root.setOnMouseDragged(e -> {
            Main.primaryStage.setX(e.getScreenX() - x);
            Main.primaryStage.setY(e.getScreenY() - y);
        });
        Main.primaryStage.setScene(new Scene(root, 520, 350));
    }

    public void infoClose() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/mainForm.fxml"));
        root.setOnMousePressed(e -> {
            x = e.getSceneX();
            y = e.getSceneY();
        });
        root.setOnMouseDragged(e -> {
            Main.primaryStage.setX(e.getScreenX() - x);
            Main.primaryStage.setY(e.getScreenY() - y);
        });
        Main.primaryStage.setScene(new Scene(root,520 ,350));
    }


    public void minimClicked() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    public void cancelClicked() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void exitClicked() throws IOException {
        if (inTask){
            Parent root = FXMLLoader.load(getClass().getResource("view/exitForm.fxml"));
            Stage stage = new Stage();
            root.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });
            stage.setScene(new Scene(root,300 ,150));
            stage.setResizable(false);
            stage.setTitle("Confirm Exit");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } else{
            Platform.exit();
        }
    }

    public void confirmExitClicked() {
        Platform.exit();
    }


    public void infoHovered() {
        info.setStyle("-fx-background-color:  linear-gradient(#77aaff, #478cff);" +
                "-fx-background-insets: 4; " +
                "-fx-background-radius: 30; " +
                "-fx-text-fill:  White");
    }

    
    public void infoUnHovered() {
        info.setStyle("-fx-background-color:  linear-gradient(#d1e2ff, #adcbff);" +
                "-fx-background-insets: 4; " +
                "-fx-background-radius: 30; " +
                "-fx-text-fill:  White");
    }

    
    public void infoPressed() {
        info.setStyle("-fx-background-color:  linear-gradient(#478cff, #116aff);" +
                "-fx-background-insets: 4; " +
                "-fx-background-radius: 30; " +
                "-fx-text-fill:  White");
    }

    
    public void infoReleased() {
        info.setStyle("-fx-background-color:  linear-gradient(#d1e2ff, #adcbff);" +
                "-fx-background-insets: 4; " +
                "-fx-background-radius: 30; " +
                "-fx-text-fill:  White");
    }

    
    public void exitPressed() {
        exit.setStyle("-fx-background-color:  linear-gradient(#dd3838,#e01f1f);" +
                "-fx-background-insets: 4; " +
                "-fx-background-radius: 30; " +
                "-fx-text-fill:  White");
    }

    
    public void exitReleased() {
        exit.setStyle("-fx-background-color:  linear-gradient(#e06464,#e05555);" +
                "-fx-background-insets: 4; " +
                "-fx-background-radius: 30; " +
                "-fx-text-fill:  White");
    }

    
    public void exitHovered() {
        exit.setStyle("-fx-background-color:  linear-gradient(#e06464,#e05555);" +
                "-fx-background-insets: 4; " +
                "-fx-background-radius: 30; " +
                "-fx-text-fill:  White");
    }

    
    public void exitUnHovered() {
        exit.setStyle("-fx-background-color:  linear-gradient(#e8c7c7,#e5a7a7);" +
                "-fx-background-insets: 4; " +
                "-fx-background-radius: 30; " +
                "-fx-text-fill:  White");
    }

    
    public void minimPressed() {
        minimize.setStyle("-fx-background-color:  linear-gradient(#0fd830,#00d122);" +
                "-fx-background-insets: 4; " +
                "-fx-background-radius: 30; " +
                "-fx-text-fill:  White");
    }

    
    public void minimReleased() {
        minimize.setStyle("-fx-background-color:  linear-gradient(#c9edcf, #a7e5b2);" +
                "-fx-background-insets: 4; " +
                "-fx-background-radius: 30; " +
                "-fx-text-fill:  White");
    }

    
    public void minimHovered() {
        minimize.setStyle("-fx-background-color:  linear-gradient(#55e087, #2fe070);" +
                "-fx-background-insets: 4; " +
                "-fx-background-radius: 30; " +
                "-fx-text-fill:  White");
    }

    
    public void minimUnHovered() {
        minimize.setStyle("-fx-background-color:  linear-gradient(#c9edcf, #a7e5b2);" +
                "-fx-background-insets: 4; " +
                "-fx-background-radius: 30; " +
                "-fx-text-fill:  White");
    }
}
