package SwingDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SwingDemo {
    // Use different names for ArrayLists
    static ArrayList<String> students = new ArrayList<>();
    static ArrayList<String> dep = new ArrayList<>();
    static ArrayList<Integer> roll = new ArrayList<>();
    static ArrayList<String> phone = new ArrayList<>();
    static ArrayList<String> dob = new ArrayList<>();
    static ArrayList<String> photoPathList = new ArrayList<>();
    static ArrayList<String> admYearList = new ArrayList<>();
    static ArrayList<String> busRouteList = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student ID Card Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        JLabel title = new JLabel("STUDENT ID CARD GENERATOR", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLUE);

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);                    
        displayArea.setBackground(Color.LIGHT_GRAY);
        displayArea.setForeground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JTextField nameField = new JTextField(20);
        JTextField departmentField = new JTextField(20);
        JTextField rollField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField dobField = new JTextField(20);
        JTextField photoPathField = new JTextField(20);
        JTextField admYearField = new JTextField(20);
        JTextField busRouteField = new JTextField(20);

        nameField.setBackground(Color.YELLOW);
        departmentField.setBackground(Color.ORANGE);
        rollField.setBackground(Color.CYAN);
        phoneField.setBackground(Color.PINK);
        dobField.setBackground(Color.GREEN);
        photoPathField.setBackground(Color.LIGHT_GRAY);
        admYearField.setBackground(Color.LIGHT_GRAY);
        busRouteField.setBackground(Color.LIGHT_GRAY);

        JButton choosePhotoBtn = new JButton("Choose Photo");
        JButton addButton = new JButton("Add Student");
        JButton deleteButton = new JButton("Delete Student");
        JButton viewButton = new JButton("View All Students");
        JButton generateButton = new JButton("Generate ID Card");
        JButton saveIDButton = new JButton("Save ID Card");

        choosePhotoBtn.setBackground(Color.LIGHT_GRAY);
        addButton.setBackground(new Color(255, 128, 0));
        deleteButton.setBackground(Color.RED);
        viewButton.setBackground(Color.YELLOW);
        generateButton.setBackground(Color.CYAN);
        saveIDButton.setBackground(new Color(100, 200, 100));

        addButton.setForeground(Color.BLACK);
        deleteButton.setForeground(Color.WHITE);
        viewButton.setForeground(Color.BLACK);
        generateButton.setForeground(Color.BLACK);
        saveIDButton.setForeground(Color.WHITE);

        // Create input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2, 5, 5));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Department/Branch:"));
        inputPanel.add(departmentField);
        inputPanel.add(new JLabel("Roll Number:"));
        inputPanel.add(rollField);
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Date of Birth:"));
        inputPanel.add(dobField);
        inputPanel.add(new JLabel("Admission Year:"));
        inputPanel.add(admYearField);
        inputPanel.add(new JLabel("Bus Route:"));
        inputPanel.add(busRouteField);
        inputPanel.add(new JLabel("Student Photo:"));
        
        JPanel photoPanel = new JPanel(new BorderLayout(5, 0));
        photoPanel.add(photoPathField, BorderLayout.CENTER);
        photoPanel.add(choosePhotoBtn, BorderLayout.EAST);
        inputPanel.add(photoPanel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(generateButton);
        buttonPanel.add(saveIDButton);

        // South panel combining input and buttons
        JPanel southPanel = new JPanel(new BorderLayout(5, 10));
        southPanel.add(inputPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        southPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // ID Card Preview Panel - Increasing size to match real ID card aspect ratio
        JPanel idCardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // This will be overridden when generating an actual ID card
            }
        };
        idCardPanel.setBackground(Color.WHITE);
        idCardPanel.setPreferredSize(new Dimension(600, 250)); // Larger size for better visibility
        idCardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(new Color(240, 240, 240));
        eastPanel.setLayout(new BorderLayout(5, 5));
        eastPanel.add(new JLabel("ID Card Preview", JLabel.CENTER), BorderLayout.NORTH);
        eastPanel.add(idCardPanel, BorderLayout.CENTER);
        eastPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        frame.getContentPane().setLayout(new BorderLayout(10, 10));
        frame.getContentPane().add(title, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(eastPanel, BorderLayout.EAST);

        choosePhotoBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(".jpg") || 
                           f.getName().toLowerCase().endsWith(".jpeg") ||
                           f.getName().toLowerCase().endsWith(".png") ||
                           f.isDirectory();
                }
                public String getDescription() {
                    return "Image Files (*.jpg, *.jpeg, *.png)";
                }
            });
            
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                photoPathField.setText(selectedFile.getAbsolutePath());
            }
        });

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String department = departmentField.getText();
            String rollInput = rollField.getText();
            String phoneNum = phoneField.getText();
            String dateOfBirth = dobField.getText();
            String photo = photoPathField.getText();
            String admYear = admYearField.getText();
            String busRoute = busRouteField.getText();

            if (name.isEmpty() || department.isEmpty() || rollInput.isEmpty() || phoneNum.isEmpty() || dateOfBirth.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Error: Name, Department, Roll Number, Phone and Date of Birth are required fields.", 
                                              "Missing Information", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int rollNum = Integer.parseInt(rollInput);
                
                // Check if roll number already exists
                if (roll.contains(rollNum)) {
                    JOptionPane.showMessageDialog(frame, "Error: Roll Number already exists!", 
                                                  "Duplicate Roll Number", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add to ArrayLists
                students.add(name);
                dep.add(department);
                roll.add(rollNum);
                phone.add(phoneNum);
                dob.add(dateOfBirth);
                photoPathList.add(photo);
                admYearList.add(admYear);
                busRouteList.add(busRoute);

                displayArea.append("Added Student: " + name + " (Roll: " + rollNum + ")\n");
                
                // Clear input fields
                clearFields(nameField, departmentField, rollField, phoneField, dobField, 
                           photoPathField, admYearField, busRouteField);
                
                // Auto-generate the ID card for the newly added student
                Graphics g = idCardPanel.getGraphics();
                drawIDCard(g, idCardPanel, name, department, rollNum, phoneNum, 
                          dateOfBirth, photo, admYear, busRoute);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Error: Roll Number must be a valid integer.", 
                                              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            if (roll.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No students in database to delete.", 
                                             "Empty Database", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String rollInput = JOptionPane.showInputDialog(frame, "Enter Roll Number to Delete:");

            if (rollInput == null || rollInput.isEmpty()) {
                return; // User canceled or didn't enter anything
            }

            try {
                int rollNum = Integer.parseInt(rollInput);
                int index = roll.indexOf(rollNum);

                if (index != -1) {
                    String deletedName = students.get(index);
                    
                    students.remove(index);
                    dep.remove(index);
                    roll.remove(index);
                    phone.remove(index);
                    dob.remove(index);
                    photoPathList.remove(index);
                    admYearList.remove(index);
                    busRouteList.remove(index);

                    displayArea.append("Deleted Student: " + deletedName + " (Roll: " + rollNum + ")\n");
                    
                    // Clear the ID card preview
                    Graphics g = idCardPanel.getGraphics();
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, idCardPanel.getWidth(), idCardPanel.getHeight());
                } else {
                    JOptionPane.showMessageDialog(frame, "Student not found for Roll Number: " + rollNum, 
                                                 "Not Found", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Error: Roll Number must be a valid integer.", 
                                             "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewButton.addActionListener(e -> {
            if (students.isEmpty()) {
                displayArea.setText("No students in the database.\n");
                return;
            }
            
            displayArea.setText("STUDENT DATABASE:\n");
            displayArea.append("-----------------------------------------------------\n");
            displayArea.append("Name | Department | Roll | Phone | DOB\n");
            displayArea.append("-----------------------------------------------------\n");
            
            for (int i = 0; i < students.size(); i++) {
                displayArea.append(students.get(i) + " | " + dep.get(i) + " | " + roll.get(i)
                        + " | " + phone.get(i) + " | " + dob.get(i) + "\n");
            }
            displayArea.append("-----------------------------------------------------\n");
            displayArea.append("Total Students: " + students.size() + "\n");
        });

        generateButton.addActionListener(e -> {
            if (roll.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No students in database to generate ID.", 
                                             "Empty Database", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String[] rollOptions = new String[roll.size()];
            for (int i = 0; i < roll.size(); i++) {
                rollOptions[i] = roll.get(i) + " - " + students.get(i);
            }
            
            String selectedOption = (String) JOptionPane.showInputDialog(frame, 
                "Select Student:", "Generate ID Card", 
                JOptionPane.QUESTION_MESSAGE, null, 
                rollOptions, rollOptions[0]);
                
            if (selectedOption == null) {
                return; // User canceled
            }
            
            try {
                int rollNum = Integer.parseInt(selectedOption.split(" - ")[0]);
                int index = roll.indexOf(rollNum);

                if (index != -1) {
                    // Generate visual ID card on the panel
                    String name = students.get(index);
                    String department = dep.get(index);
                    String phoneNum = phone.get(index);
                    String dateOfBirth = dob.get(index);
                    String photoPath = index < photoPathList.size() ? photoPathList.get(index) : "";
                    String year = index < admYearList.size() ? admYearList.get(index) : "N/A";
                    String route = index < busRouteList.size() ? busRouteList.get(index) : "N/A";
                    
                    Graphics g = idCardPanel.getGraphics();
                    drawIDCard(g, idCardPanel, name, department, rollNum, phoneNum, 
                              dateOfBirth, photoPath, year, route);
                    
                    // Display text version
                    displayArea.setText("");
                    displayArea.append("=== STUDENT ID CARD GENERATED ===\n");
                    displayArea.append("Name: " + name + "\n");
                    displayArea.append("Department: " + department + "\n");
                    displayArea.append("Roll Number: " + rollNum + "\n");
                    displayArea.append("Phone Number: " + phoneNum + "\n");
                    displayArea.append("Date of Birth: " + dateOfBirth + "\n");
                    displayArea.append("Admission Year: " + year + "\n");
                    displayArea.append("Bus Route: " + route + "\n");
                    displayArea.append("==============================\n");
                } else {
                    displayArea.append("Student not found for Roll Number: " + rollNum + "\n");
                }
            } catch (Exception ex) {
                displayArea.append("Error generating ID card: " + ex.getMessage() + "\n");
            }
        });
        
        saveIDButton.addActionListener(e -> {
            try {
                BufferedImage image = new BufferedImage(idCardPanel.getWidth(), idCardPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = image.createGraphics();
                idCardPanel.paint(g2d);
                g2d.dispose();
                
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File("student_id_card.png"));
                if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (!file.getName().toLowerCase().endsWith(".png")) {
                        file = new File(file.getAbsolutePath() + ".png");
                    }
                    ImageIO.write(image, "png", file);
                    displayArea.append("ID Card saved successfully to: " + file.getAbsolutePath() + "\n");
                }
            } catch (Exception ex) {
                displayArea.append("Error saving ID card: " + ex.getMessage() + "\n");
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
    }

    private static void drawIDCard(Graphics g, JPanel panel, String name, String department, 
                                  int rollNumber, String phone, String dob, String photoPath, 
                                  String admYear, String busRoute) {
        // Clear panel
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        
        // Card dimensions
        int cardWidth = panel.getWidth();
        int cardHeight = panel.getHeight();
        
        // Draw card background - red like the original ID
        g.setColor(new Color(220, 50, 50)); // Brighter red to match the sample
        g.fillRect(0, 0, cardWidth, cardHeight);
        
        // Draw top header band with logos and text
        int headerHeight = 80;
        
        // Draw college logo on left
        int logoSize = 40;
        g.setColor(new Color(40, 40, 40)); // Dark background for logo
        g.fillOval(15, 10, logoSize, logoSize);
        g.setColor(Color.YELLOW);
        g.fillOval(20, 15, logoSize - 10, logoSize - 10);
        
        // Add simulated gear in logo
        g.setColor(Color.GRAY);
        g.fillOval(25, 20, logoSize - 20, logoSize - 20);
        
        // Add blue ribbon under logo
        g.setColor(new Color(100, 150, 255));
        g.fillRect(5, 52, logoSize + 20, 8);
        
        // Quality certification logo on right
        g.setColor(Color.WHITE);
        g.fillOval(cardWidth - 45, 10, 35, 35);
        g.setColor(Color.BLUE);
        g.drawLine(cardWidth - 35, 20, cardWidth - 35, 35);
        g.drawLine(cardWidth - 40, 27, cardWidth - 30, 27);
        g.drawString("DNV", cardWidth - 40, 45);
        
        // College name and information
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("R.M.K.", cardWidth/2 - 70, 20);
        g.drawString("ENGINEERING COLLEGE", cardWidth/2 - 100, 40);
        
        g.setFont(new Font("Arial", Font.BOLD, 7));
        g.drawString("(AN AUTONOMOUS INSTITUTION)", cardWidth/2 - 70, 50);
        g.drawString("Approved by AICTE & Affiliated to Anna University", cardWidth/2 - 100, 58);
        g.drawString("Accredited by NAAC with A+ Grade", cardWidth/2 - 80, 66);
        
        // Identity card header bar
        g.setColor(Color.WHITE);
        g.fillRect(0, headerHeight + 5, cardWidth, 20);
        g.setColor(new Color(75, 0, 130)); // Purple for Identity Card text
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("IDENTITY CARD", cardWidth/2 - 55, headerHeight + 20);
        
        // Information section
        // Create pink info panel
        int infoStartY = headerHeight + 30;
        int infoBoxHeight = 110;
        g.setColor(new Color(255, 200, 200)); // Light pink background
        g.fillRect(cardWidth / 3, infoStartY, (2 * cardWidth / 3) - 10, infoBoxHeight);
        g.setColor(Color.BLACK);
        g.drawRect(cardWidth / 3, infoStartY, (2 * cardWidth / 3) - 10, infoBoxHeight);
        
        // Photo area
        int photoWidth = 80;
        int photoHeight = 100;
        int photoX = cardWidth - photoWidth - 20;
        int photoY = infoStartY + 5;
        
        g.setColor(Color.WHITE);
        g.fillRect(photoX, photoY, photoWidth, photoHeight);
        g.setColor(Color.BLACK);
        g.drawRect(photoX, photoY, photoWidth, photoHeight);
        
        // Try to load and display photo if available
        if (photoPath != null && !photoPath.isEmpty()) {
            try {
                Image photo = ImageIO.read(new File(photoPath));
                g.drawImage(photo, photoX, photoY, photoWidth, photoHeight, null);
            } catch (Exception e) {
                g.setColor(Color.BLACK);
                g.drawString("No Photo", photoX + 15, photoY + 50);
            }
        }
        
        // Draw student information with proper labels and values
        int labelX = cardWidth / 3 + 10;
        int valueX = labelX + 70;
        int infoY = infoStartY + 25;
        int lineSpacing = 20;
        
        // Draw info labels with gold color
        g.setColor(new Color(204, 153, 0)); // Gold color for labels
        g.setFont(new Font("Arial", Font.BOLD, 11));
        
        // Draw labels
        g.drawString("Name:", labelX, infoY);
        g.drawString("Adm. Year:", labelX, infoY + lineSpacing);
        g.drawString("Branch:", labelX + 110, infoY + lineSpacing);
        g.drawString("Reg/Roll No:", labelX, infoY + lineSpacing * 2);
        g.drawString("Bus Route:", labelX, infoY + lineSpacing * 3);
        
        // Draw value backgrounds in a lighter pink
        g.setColor(new Color(255, 220, 220));
        g.fillRect(valueX - 5, infoY - 12, 130, 16); // Name
        g.fillRect(valueX - 5, infoY + lineSpacing - 12, 50, 16); // Adm Year
        g.fillRect(labelX + 160, infoY + lineSpacing - 12, 70, 16); // Branch
        g.fillRect(valueX - 5, infoY + lineSpacing * 2 - 12, 130, 16); // Roll
        g.fillRect(valueX - 5, infoY + lineSpacing * 3 - 12, 130, 16); // Bus Route
        
        // Draw actual values in black
        g.setColor(Color.BLACK);
        g.drawString(name, valueX, infoY);
        
        String admYearDisplay = (admYear != null && !admYear.isEmpty()) ? admYear : "N/A";
        g.drawString(admYearDisplay, valueX, infoY + lineSpacing);
        
        g.drawString(department, labelX + 165, infoY + lineSpacing);
        g.drawString(String.valueOf(rollNumber), valueX, infoY + lineSpacing * 2);
        
        String busRouteDisplay = (busRoute != null && !busRoute.isEmpty()) ? busRoute : "N/A";
        if (busRouteDisplay.length() > 0 && !busRouteDisplay.toLowerCase().contains("n/a")) {
            if (!busRouteDisplay.contains("-")) {
                busRouteDisplay += "-ERNAVOOR"; // Adding a default destination if not specified
            }
        }
        g.drawString(busRouteDisplay, valueX, infoY + lineSpacing * 3);
        
        // Add "DAYSCHOLAR" text in red at bottom left
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("DAYSCHOLAR", 20, infoStartY + infoBoxHeight + 15);
        
        // Draw Valid Until text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 9));
        try {
            if (admYear != null && !admYear.isEmpty()) {
                int graduationYear = Integer.parseInt(admYear) + 4;
                g.drawString("Valid Until: June " + graduationYear, 20, cardHeight - 15);
            }
        } catch (NumberFormatException e) {
            g.drawString("Valid Until: N/A", 20, cardHeight - 15);
        }
        
        // Draw signature line and principal text at bottom right
        int signatureX = cardWidth - 100;
        int signatureY = cardHeight - 20;
        g.setColor(Color.BLACK);
        g.drawLine(signatureX, signatureY, signatureX + 80, signatureY);
        g.setFont(new Font("Arial", Font.ITALIC, 10));
        g.drawString("Principal", signatureX + 30, signatureY + 15);
        
        // Space for signature on left side of photo
        g.setColor(Color.BLACK);
        int signX = photoX - 70;
        int signY = photoY + photoHeight - 15;
        g.drawLine(signX, signY, signX + 60, signY);
        
        // The panel will be updated with this ID card
    }

    private static void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}