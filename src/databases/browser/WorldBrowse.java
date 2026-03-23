package databases.browser;

import javax.swing.*;
import java.awt.*;

public class WorldBrowse extends JFrame {

    MyText IDText, countryText, populationText, nameText;
    Operation currentOperation;

    MyButton saveButton, cancelButton, updateButton;
    MyButton firstButton, nextButton, prevButton, lastButton;
    MyButton addButton, deleteButton;

    public WorldBrowse() {
        setSize(700, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        currentOperation = Operation.NONE;

        JPanel gridPanel = new JPanel(new GridLayout(4, 2, 2, 2));
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

        // ID
        MyLabel IDLabel = new MyLabel("ID:");
        gridPanel.add(IDLabel);
        IDText = new MyText("");
        gridPanel.add(IDText);

        // Name
        MyLabel nameLabel = new MyLabel("City name:");
        gridPanel.add(nameLabel);
        nameText = new MyText("");
        gridPanel.add(nameText);

        // Country
        MyLabel countryLabel = new MyLabel("Country:");
        gridPanel.add(countryLabel);
        countryText = new MyText("");
        gridPanel.add(countryText);

        // Population
        MyLabel populationLabel = new MyLabel("Population:");
        gridPanel.add(populationLabel);
        populationText = new MyText("");
        gridPanel.add(populationText);

        // Read buttons
        prevButton = new MyButton("Prev");
        nextButton = new MyButton("Next");
        firstButton = new MyButton("First");
        lastButton = new MyButton("Last");

        prevButton.addActionListener(e -> previous());
        nextButton.addActionListener(e -> next());
        firstButton.addActionListener(e -> first());
        lastButton.addActionListener(e -> last());

        JPanel readButtons = new JPanel(new FlowLayout());
        readButtons.add(firstButton);
        readButtons.add(prevButton);
        readButtons.add(nextButton);
        readButtons.add(lastButton);
        buttonPanel.add(readButtons);

        // Edit buttons
        addButton = new MyButton("Add new");
        deleteButton = new MyButton("Delete");
        updateButton = new MyButton("Update");

        addButton.addActionListener(e -> startAdd());
        deleteButton.addActionListener(e -> deleteRecord());
        updateButton.addActionListener(e -> startUpdate());

        JPanel editButtons = new JPanel(new FlowLayout());
        editButtons.add(addButton);
        editButtons.add(deleteButton);
        editButtons.add(updateButton);
        buttonPanel.add(editButtons);

        // Save buttons
        saveButton = new MyButton("Save");
        cancelButton = new MyButton("Cancel");

        saveButton.addActionListener(e -> save());
        cancelButton.addActionListener(e -> cancel());

        JPanel saveButtons = new JPanel(new FlowLayout());
        saveButtons.add(saveButton);
        saveButtons.add(cancelButton);
        buttonPanel.add(saveButtons);

        add(buttonPanel, BorderLayout.SOUTH);
        add(gridPanel, BorderLayout.CENTER);

        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);
        setFields(false);
    }

    void startAdd() {
        currentOperation = Operation.ADD;

        IDText.setText("");
        nameText.setText("");
        countryText.setText("");
        populationText.setText("");

        setFields(true);
        setBrowseButtonsEnabled(false);
        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);
    }

    void startUpdate() {
        currentOperation = Operation.UPDATE;

        setFields(true);
        setBrowseButtonsEnabled(false);
        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);
    }

    void save() {
        if (currentOperation == Operation.ADD) {
            JOptionPane.showMessageDialog(this, "TMP..");
        } else if (currentOperation == Operation.UPDATE) {
            JOptionPane.showMessageDialog(this, "TMP...");
        }

        currentOperation = Operation.NONE;
        setFields(false);
        setBrowseButtonsEnabled(true);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);
    }

    void cancel() {
        currentOperation = Operation.NONE;
        setFields(false);
        setBrowseButtonsEnabled(true);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);

        JOptionPane.showMessageDialog(this, "Changes cancelled.");
    }

    void deleteRecord() {
        JOptionPane.showMessageDialog(this, "TMP...");
    }

    void next() {
        JOptionPane.showMessageDialog(this, "TMP...");
    }

    void previous() {
        JOptionPane.showMessageDialog(this, "TMP...");
    }

    void first() {
        JOptionPane.showMessageDialog(this, "TMP...");
    }

    void last() {
        JOptionPane.showMessageDialog(this, "TMP...");
    }

    void setFields(boolean status) {
        IDText.setEnabled(status);
        nameText.setEnabled(status);
        countryText.setEnabled(status);
        populationText.setEnabled(status);
    }

    void setBrowseButtonsEnabled(boolean status) {
        firstButton.setEnabled(status);
        lastButton.setEnabled(status);
        nextButton.setEnabled(status);
        prevButton.setEnabled(status);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WorldBrowse().setVisible(true));
    }
}

class MyLabel extends JLabel {
    public MyLabel(String text) {
        super(text);
        setFont(new Font("Consolas", Font.PLAIN, 18));
        setOpaque(true);
        setBackground(new Color(0xF5ECBC));
        setForeground(new Color(0x000000));
    }
}

class MyText extends JTextField {
    public MyText(String text) {
        super(text);
        setFont(new Font("Consolas", Font.PLAIN, 18));
        setOpaque(true);
        setBackground(new Color(0xF5ECBC));
        setForeground(new Color(0x000000));
    }
}

class MyButton extends JButton {
    public MyButton(String text) {
        super(text);
        setFont(new Font("Consolas", Font.PLAIN, 18));
        setOpaque(true);
        setBackground(Color.BLUE);
        setForeground(Color.WHITE);
    }
}

enum Operation {
    ADD, UPDATE, NONE
}