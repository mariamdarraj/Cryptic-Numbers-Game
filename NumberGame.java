import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.util.Random;

// Main class for the Cryptic Numbers Game
public class NumberGame extends JFrame {

    // Game state variables
    int number;         // Number to guess
    int attempts = 0;   // Number of attempts in current game
    int wins = 0;       // Total wins
    boolean soundOn = true;
    boolean vibrationOn = true;
    int maxNumber = 100; // Maximum number for the current game

    // Colors for UI elements
    Color labelColor = Color.decode("#060708");

    // Panels and UI components
    JPanel mainPanel, settingsPanel, statsPanel, startPanel, gamePanel;
    JLabel label;
    JTextField guess;
    JButton button;
    JLabel statsLabel;

    // Background images
    Image mainBackground;
    Image startBackground;
    Image statsBackground;
    Image settingsBackground;

    // Constructor - sets up the game window and panels
    public NumberGame() {
        setTitle("Cryptic Numbers");
        setSize(360, 666);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Load background images
        mainBackground = new ImageIcon(
                "C:/Users/User/OneDrive/سطح المكتب/assets/main_menu-backgrounds.png"
        ).getImage();

        startBackground = new ImageIcon(
                "C:/Users/User/OneDrive/سطح المكتب/assets/settings,start,game_panel-backgrounds.png"
        ).getImage();

        statsBackground = new ImageIcon(
                "C:/Users/User/OneDrive/سطح المكتب/assets/stats_panel-backgrounds.png"
        ).getImage();
        settingsBackground = new ImageIcon(
                "C:/Users/User/OneDrive/سطح المكتب/assets/settings,start,game_panel-backgrounds.png"
        ).getImage();

        // Create all panels
        createMainPanel();
        createSettingsPanel();
        createStatsPanel();
        createStartPanel();
        createGamePanel();

        // Add panels to the frame
        add(mainPanel, "Main");
        add(settingsPanel, "Settings");
        add(statsPanel, "Stats");
        add(startPanel, "Start");
        add(gamePanel, "Game");

        // Show main panel initially
        showPanel("Main");
        setVisible(true);
    }

    // Helper method to create a panel with a background image
    private JPanel panelWithBackground(Image bg) {
        return new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }
    // Switch between panels using CardLayout
    private void showPanel(String name) {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), name);
    }
    // Makes a button transparent and with rounded borders
    private void transparentRoundButton(JButton b) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setForeground(Color.BLACK);

        b.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int arc = 20;
                int stroke=1;
                g2.setColor(new Color(0, 0, 0, 120));
                g2.setStroke(new BasicStroke(stroke));
                g2.drawRoundRect(stroke, stroke, c.getWidth()-stroke*2-1 , c.getHeight()-stroke*2-1 , arc, arc);
                g2.dispose();
                super.paint(g, c);
            }
        });
    }
    // Custom border class for rounded JTextField
    class RoundedTextFieldBorder extends AbstractBorder {

        private final int arc;
        private final Color color;
        private final int stroke;

        public RoundedTextFieldBorder(int arc, Color color, int stroke) {
            this.arc = arc;
            this.color = color;
            this.stroke = stroke;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(stroke));

            g2.drawRoundRect(
                    stroke, stroke, width - stroke * 2 - 1, height - stroke * 2 - 1, arc, arc
            );

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(8, 12, 8, 12);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(8, 12, 8, 12);
            return insets;
        }
    }
    // Makes JTextField transparent and rounded
    private void transparentRoundTextField(JTextField t) {
        t.setOpaque(false);
        t.setBackground(new Color(0, 0, 0, 0));
        t.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        t.setForeground(Color.BLACK);
        t.setBorder(new RoundedTextFieldBorder(20, new Color(0,0,0,120), 1));
    }
    // Create the main menu panel
    private void createMainPanel() {
        mainPanel = panelWithBackground(mainBackground);
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(5, 10, 2, 10);
        gbc.gridy = 0;
        gbc.weighty = 2.56;
        mainPanel.add(Box.createGlue(), gbc);
        gbc.gridy = 1;
        gbc.weighty=0;
        // Add game title
        gbc.insets = new Insets(50, 10, 20, 10);
        JLabel label1 = new JLabel("Cryptic Numbers", JLabel.CENTER);
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("Lucida Calligraphy", Font.BOLD, 24));
        label1.setOpaque(false);
        mainPanel.add(label1, gbc);
        // Add Start button
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 2, 10);
        ImageIcon startIcon = new ImageIcon(
                new ImageIcon("C:/Users/User/OneDrive/سطح المكتب/assets/start-buttons.png")
                        .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)
        );
        JButton startBtn = new JButton(startIcon);
        startBtn.setContentAreaFilled(false);
        startBtn.setBorderPainted(false);
        startBtn.setFocusPainted(false);
        startBtn.addActionListener(e -> showPanel("Start"));
        mainPanel.add(startBtn, gbc);
        // Start label
        gbc.gridy = 3;
        JLabel startLabel = new JLabel("Start", JLabel.CENTER);
        startLabel.setForeground(labelColor);
        mainPanel.add(startLabel, gbc);

        // Stats button
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 2, 10);
        ImageIcon statsIcon = new ImageIcon(
                new ImageIcon("C:/Users/User/OneDrive/سطح المكتب/assets/stats-buttons.png")
                        .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)
        );
        JButton statsBtn = new JButton(statsIcon);
        statsBtn.setContentAreaFilled(false);
        statsBtn.setBorderPainted(false);
        statsBtn.setFocusPainted(false);
        statsBtn.addActionListener(e -> {
            updateStatsLabel();
            showPanel("Stats");
        });
        mainPanel.add(statsBtn, gbc);

        gbc.gridy = 5;
        JLabel statsLabelBtn = new JLabel("Win Stats", JLabel.CENTER);
        statsLabelBtn.setForeground(labelColor);
        mainPanel.add(statsLabelBtn, gbc);
        // Settings button
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 10, 2, 10);
        ImageIcon settingsIcon = new ImageIcon(
                new ImageIcon("C:/Users/User/OneDrive/سطح المكتب/assets/Settings-buttons.png")
                        .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)
        );
        JButton settingsBtn = new JButton(settingsIcon);
        settingsBtn.setContentAreaFilled(false);
        settingsBtn.setBorderPainted(false);
        settingsBtn.setFocusPainted(false);
        settingsBtn.addActionListener(e -> showPanel("Settings"));
        mainPanel.add(settingsBtn, gbc);

        gbc.gridy = 7;
        JLabel settingsLabel = new JLabel("Settings", JLabel.CENTER);
        settingsLabel.setForeground(labelColor);
        mainPanel.add(settingsLabel, gbc);
        gbc.gridy = 8;
        gbc.weighty = 1.0;
        mainPanel.add(Box.createGlue(), gbc);
    }
    // Create the Settings panel where player can toggle sound and vibration
    private void createSettingsPanel() {

        settingsPanel = panelWithBackground(settingsBackground);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Checkboxes for sound and vibration
        JCheckBox soundBox = new JCheckBox("Sound", soundOn);
        JCheckBox vibrationBox = new JCheckBox("Vibration", vibrationOn);
        soundBox.setOpaque(false);
        vibrationBox.setOpaque(false);
        // Update game state when checkbox is toggled
        soundBox.addActionListener(e -> soundOn = soundBox.isSelected());
        vibrationBox.addActionListener(e -> vibrationOn = vibrationBox.isSelected());
        // Add checkboxes to panel
        gbc.gridy = 0;
        settingsPanel.add(soundBox, gbc);
        gbc.gridy = 1;
        settingsPanel.add(vibrationBox, gbc);
        // Back button to return to Main panel
        JButton backBtn = new JButton("Back");
        transparentRoundButton(backBtn);
        backBtn.addActionListener(e -> showPanel("Main"));
        gbc.gridy = 2;
        settingsPanel.add(backBtn, gbc);
    }

    // Create Stats panel showing wins and attempts
    private void createStatsPanel() {
        statsPanel = panelWithBackground(statsBackground);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        statsLabel = new JLabel();// Label to display stats
        statsLabel.setForeground(labelColor);
        gbc.gridx = 0;
        gbc.gridy = 10;
        statsPanel.add(statsLabel, gbc);
        // Back button to return to Main panel
        JButton backBtn = new JButton("Back");
        transparentRoundButton(backBtn);
        backBtn.addActionListener(e -> showPanel("Main"));
        gbc.gridy = 11;
        statsPanel.add(backBtn, gbc);
    }
    // Update the stats label with current wins and attempts
    private void updateStatsLabel() {
        statsLabel.setText("<html>Wins: " + wins + "<br>Attempts: " + attempts + "</html>");
    }
    // Create Start panel for selecting difficulty level
    private void createStartPanel() {
        startPanel = panelWithBackground(startBackground);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Difficulty buttons
        JButton easyBtn = new JButton("Easy (1-100)");
        transparentRoundButton(easyBtn);
        JButton mediumBtn = new JButton("Medium (1-500)");
        transparentRoundButton(mediumBtn);
        JButton hardBtn = new JButton("Hard (1-1000)");
        transparentRoundButton(hardBtn);
        // Start game with selected max number
        easyBtn.addActionListener(e -> startGame(100));
        mediumBtn.addActionListener(e -> startGame(500));
        hardBtn.addActionListener(e -> startGame(1000));
        // Add buttons to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        startPanel.add(easyBtn, gbc);
        gbc.gridy = 1;
        startPanel.add(mediumBtn, gbc);
        gbc.gridy = 2;
        startPanel.add(hardBtn, gbc);
        // Back button to Main panel
        JButton backBtn = new JButton("Back");
        transparentRoundButton(backBtn);
        backBtn.addActionListener(e -> showPanel("Main"));
        gbc.gridy = 3;
        startPanel.add(backBtn, gbc);

    }
    // Start a new game with given max number
    private void startGame(int max) {
        maxNumber = max;
        number = new Random().nextInt(maxNumber) + 1;// Generate random number
        attempts = 0;// Reset attempts
        guess.setText(""); // Clear input field
        button.setEnabled(true);// Enable check button
        label.setText("Guess a number from 1 to " + maxNumber);// Instruction
        showPanel("Game");// Show game panel
    }
    // Create the Game panel where player enters guesses
    private void createGamePanel() {
        gamePanel = panelWithBackground(startBackground);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Label for instructions / feedback
        label = new JLabel("Guess a number", JLabel.CENTER);
        label.setForeground(labelColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gamePanel.add(label, gbc);
        // TextField for player input
        guess = new JTextField(10);
        transparentRoundTextField(guess);
        guess.setPreferredSize(new Dimension(200, 29));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gamePanel.add(guess, gbc);
        // Button to check guess
        button = new JButton("Check");
        transparentRoundButton(button);
        button.addActionListener(e -> checkGuess());
        gbc.gridx = 1;
        gbc.gridy = 1;
        gamePanel.add(button, gbc);
        // Back button to Main panel
        JButton backBtn = new JButton("Back");
        transparentRoundButton(backBtn);
        backBtn.addActionListener(e -> showPanel("Main"));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gamePanel.add(backBtn, gbc);
    }
    // Check player's guess and update game state
    private void checkGuess() {
        try {
            int guessNumber = Integer.parseInt(guess.getText());    // Convert input to integer
            attempts++;     // Increment attempts
            // Compare guess with actual number
            if (guessNumber > number) {
                label.setText("The number is greater than required");
            } else if (guessNumber < number) {
                label.setText("The number is less than required");
            } else {
                label.setText("You Won! Attempts: " + attempts);
                button.setEnabled(false);   // Disable check button after win
                wins++;     // Increment total wins
            }
            guess.setText("");      // Clear input field
        } catch (NumberFormatException e) {
            // Handle invalid input
            label.setText("Enter a valid number");
            guess.setText("");
        }
    }
    // Main method to run the game
    public static void main(String[] args) {
        new NumberGame();
    }
}
