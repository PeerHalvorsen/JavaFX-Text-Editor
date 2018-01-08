
import java.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextArea;
import static javafx.scene.text.Font.font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javax.swing.*;

/**
 This is the controller class for the Text Editor JavaFX application

 @author Peer-Anders
 */
public class TextEditorController
{

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem newMenuItem;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private MenuItem saveAsMenuItem;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private Menu fontMenu;

    @FXML
    private RadioButton monospacedRadioButton;

    @FXML
    private ToggleGroup fontToggleGroup;

    @FXML
    private RadioButton serifRadioButton;

    @FXML
    private RadioButton sanSerifRadioButton;

    @FXML
    private CheckBox italicCheckbox;

    @FXML
    private CheckBox boldCheckbox;

    @FXML
    private TextArea textArea;

    @FXML
    private ScrollPane scrollPane;

    private String filename = null;
    private JFileChooser fileChooser;

    /**
     The initialize method sets text wrap on the text area, the preferred column
     count to 40, and the preferred row count to 20
     */
    public void initialize()
    {
        textArea.setWrapText(true);
        textArea.setPrefColumnCount(40);
        textArea.setPrefRowCount(20);
    }

    /*
     Event listener for the format radio buttons in the font menu
     */
    @FXML
    void formatListener(ActionEvent event)
    {
        if (monospacedRadioButton.isSelected())
        {
            textArea.setFont(font("Monospaced"));
            styleListener(event);
        }
        else if (serifRadioButton.isSelected())
        {
            textArea.setFont(font("Serif"));
            styleListener(event);
        }
        else if (sanSerifRadioButton.isSelected())
        {
            textArea.setFont(font("SansSerif"));
            styleListener(event);
        }

    }

    /*
     Event listener for the checkboxes in the font menu
     */
    @FXML
    void styleListener(ActionEvent event)
    {
        String family = textArea.getFont().getFamily();
        double size = textArea.getFont().getSize();
        if (boldCheckbox.isSelected() && !italicCheckbox.isSelected())
        {
            textArea.setFont(font(family, FontWeight.BOLD, size));
        }
        else if (italicCheckbox.isSelected() && !boldCheckbox.isSelected())
        {
            textArea.setFont(font(family, FontPosture.ITALIC, size));
        }
        else if (boldCheckbox.isSelected() && italicCheckbox.isSelected())
        {
            textArea.setFont(font(family, FontWeight.BOLD,
                    FontPosture.ITALIC, size));
        }
        else
        {
            textArea.setFont(font(family, FontWeight.NORMAL,
                    FontPosture.REGULAR, size));
        }
    }


    /*
     Event listener for new menu item. Clears the text area
     sets the file name to null, and resets the font buttons and checkboxes
     */
    @FXML
    void newMenuItemListener(ActionEvent event)
    {
        textArea.clear();
        filename = null;
        monospacedRadioButton.setSelected(false);
        serifRadioButton.setSelected(false);
        sanSerifRadioButton.setSelected(false);
        italicCheckbox.setSelected(false);
        boldCheckbox.setSelected(false);
    }

    /*
     Event listener for open menu item. Opens a JFileChooser open dialog
     If a file is selected and found then it saves the file path in the file name
     and reads the file to the text area.
     */
    @FXML
    void openMenuItemListener(ActionEvent event)
    {
        try
        {
            File selectedFile;
            fileChooser = new JFileChooser();
            int status = fileChooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION)
            {
                //get file
                selectedFile = fileChooser.getSelectedFile();
                //save file path
                filename = selectedFile.getPath();

                //read file to text area
                BufferedReader buffFile = new BufferedReader(
                        new FileReader(selectedFile));
                String line = buffFile.readLine();
                while (line != null)
                {
                    textArea.appendText(line + "\n");
                    line = buffFile.readLine();
                }
                buffFile.close();
            }
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "The file  was not found or "
                    + "it does not exist.");
        }

    }

    /*
     Event listener for save menu item. If the filename is not null, saves
     the edited contents of the file to the file path.  If the file name is null
     calls the save as menu item listener to open a save dialog.
     */
    @FXML
    void saveMenuItemListener(ActionEvent event)
    {

        try
        {   //check if we are working in an opened file
            if (filename != null)
            {
                //write changes to the file and save to file at filename
                BufferedWriter buffFile = new BufferedWriter(
                        new FileWriter(filename));
                buffFile.write(textArea.getText().replaceAll("\n",
                        System.getProperty("line.separator")));
                buffFile.close();
            }
            else
            {
                //call save as listener if filename is null
                saveAsMenuItemListener(event);
            }
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "The file could not be saved "
                    + "as the given directory does not exist.");
        }
    }

    /*
     Event listener for the save as menu item. Opens a JFileChooser save dialog
     and retrieves the filename and path that the user selected.  It then writes
     the contents of the text area to the file.
     */
    @FXML
    void saveAsMenuItemListener(ActionEvent event)
    {

        try
        {
            File saveFile;
            fileChooser = new JFileChooser();
            int status = fileChooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION)
            {
                //get file
                saveFile = fileChooser.getSelectedFile();
                //get file path and save to file name
                filename = saveFile.getPath();

                //write contents of text area to file and close
                BufferedWriter buffFile = new BufferedWriter(
                        new FileWriter(saveFile));
                buffFile.write(textArea.getText().replaceAll("\n",
                        System.getProperty("line.separator")));
                buffFile.close();
            }
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "The file could not be saved "
                    + "as the given directory does not exist.");
        }

    }

    /*
     Event listener for the exit menu item in the file menu. Exits the application
     */
    @FXML
    void exitMenuItemListener(ActionEvent event)
    {
        System.exit(0);
    }

}
