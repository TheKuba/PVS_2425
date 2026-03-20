package exams.severs.gui;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class TreasureClientGUI extends JFrame {

    private static final int GRID_SIZE = 7;

    private JLabel triesLabel;
    private JLabel statusLabel;
    private JButton[][] cellButtons;

    private int tries = 0;

    private static final int PORT = 5555;
    private static final String HOST = "127.0.0.1";

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private void connect() {
        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            new Thread(this::listenLoop, "server-listener").start();

            out.println("/connect");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Could not connect to server at " + HOST + ":" + PORT + "\n" + ex.getMessage(),
                    "Connection error",
                    JOptionPane.ERROR_MESSAGE);
            setControlsEnabled(false);
        }
    }

    private void listenLoop() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();

                //tady aktualizuju stav
                if (line.startsWith("/gamestarted")) {
                    String[] parts = line.split("\\s+");
                    if (parts[1].equals("true")) {
                        setControlsEnabled(false);
                    }
                }
                if (line.startsWith("/reveal")) {
                    String[] parts = line.split("\\s+");
                    if (parts[1].equals("empty")) {
                        revealEmptyCell(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                    } else if (parts[1].equals("treasure")) {
                        revealTreasureCell(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                        showTreasureFoundMessage(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                    }
                }
                if (line.startsWith("/restart")) {
                    resetBoard();
                }
                if (line.startsWith("ERROR")) {
                    String errLine = line;
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(null, errLine, "Error", JOptionPane.WARNING_MESSAGE)
                    );
                }
            }
        } catch (IOException | NumberFormatException ex) {
            SwingUtilities.invokeLater(() -> {
                setControlsEnabled(false);
                JOptionPane.showMessageDialog(null,
                        "Disconnected from server.",
                        "Disconnected",
                        JOptionPane.WARNING_MESSAGE);
            });
        } finally {
            closeQuietly();
        }
    }

    private void closeQuietly() {
        try {
            if (socket != null) socket.close();
            JOptionPane.showMessageDialog(null,
                    "Disconnected from server.",
                    "Disconnected",
                    JOptionPane.WARNING_MESSAGE);
        } catch (IOException ignored) {
        }
    }

    public TreasureClientGUI() {
        initializeWindow();
        initializeComponents();
        setVisible(true);
    }

    private void initializeWindow() {
        setTitle("Treasure Hunting Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void initializeComponents() {
        //Horni panel
        JPanel topPanel = new JPanel(new GridLayout(2, 1));

        triesLabel = new JLabel("Tries: 0"); //pocet pokusu
        triesLabel.setFont(new Font("Arial", Font.BOLD, 18));

        statusLabel = new JLabel("Find the treasure!");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        topPanel.add(triesLabel);
        topPanel.add(statusLabel);

        add(topPanel, BorderLayout.NORTH);

        // Hraci plocha
        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 5, 5));
        cellButtons = new JButton[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton button = new JButton("?");
                button.setFocusPainted(false);
                button.setFont(new Font("Arial", Font.BOLD, 20));
                final int r = row;
                final int c = col;
                button.addActionListener(e -> {
                    onCellClicked(r, c);
                });

                cellButtons[row][col] = button;
                gridPanel.add(button);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
        connect();
    }

    private void onCellClicked(int row, int col) {
        incrementTries();
        out.println("/dig " + row + " " + col);

        // tady lokalni ukazka
        cellButtons[row][col].setText("X");
        cellButtons[row][col].setEnabled(false);
    }

    public void setControlsEnabled(boolean b){
        if (!b){
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    cellButtons[row][col].setEnabled(false);
                }
            }
        }
    }

    public void incrementTries() {
        tries++;
        triesLabel.setText("Tries: " + tries);
    }


    public void revealEmptyCell(int row, int col) {
        cellButtons[row][col].setText(".");
        cellButtons[row][col].setEnabled(false);
    }

    public void revealTreasureCell(int row, int col) {
        cellButtons[row][col].setText("T");
        cellButtons[row][col].setEnabled(false);
    }

    public void resetBoard() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cellButtons[row][col].setText("?");
                cellButtons[row][col].setEnabled(true);
            }
        }
    }

    public void showTreasureFoundMessage(int row, int col) {
        JOptionPane.showMessageDialog(
                this,
                "Treasure found at (" + row + ", " + col + ")!\nNew round begins.",
                "Congratulations",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TreasureClientGUI::new);

    }
}