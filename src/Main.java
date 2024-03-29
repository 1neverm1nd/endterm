import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class City {
    private String name;
    private List<Building> buildings;

    public City() {
        buildings = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public void removeBuilding(Building building) {
        buildings.remove(building);
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    // Method to search for a building by street name and house number
    public Building findBuilding(String streetName, String houseNumber) {
        for (Building building : buildings) {
            if (building.getStreetName().equals(streetName) && building.getHouseNumber().equals(houseNumber)) {
                return building;
            }
        }
        return null; // If no matching building found
    }
}

class Building {
    private String streetName;
    private String houseNumber;
    private double basicMonthlyPaymentPerSqM;
    private List<Room> rooms;

    public Building() {
        rooms = new ArrayList<>();
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public double getBasicMonthlyPaymentPerSqM() {
        return basicMonthlyPaymentPerSqM;
    }

    public void setBasicMonthlyPaymentPerSqM(double basicMonthlyPaymentPerSqM) {
        this.basicMonthlyPaymentPerSqM = basicMonthlyPaymentPerSqM;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    // Method to calculate total area of all rooms in the building
    public double getTotalArea() {
        double totalArea = 0;
        for (Room room : rooms) {
            totalArea += room.getArea();
        }
        return totalArea;
    }
}

class Room {
    private String number;
    private double area;
    private Building building; // Reference to the building the room belongs to

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}

public class Main extends JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/city";
    private static final String USER = "1111";
    private static final String PASSWORD = "1111";

    private JTextField streetField, houseField, paymentField;
    private JButton addButton, updateButton, deleteButton, showButton;
    private JTextArea outputArea;
    private City city;

    public Main() {
        city = new City();
        city.setName("Astana");

        setTitle("Building Database App");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Street Name:"));
        streetField = new JTextField();
        inputPanel.add(streetField);
        inputPanel.add(new JLabel("House Number:"));
        houseField = new JTextField();
        inputPanel.add(houseField);
        inputPanel.add(new JLabel("Basic Monthly Payment Per SqM:"));
        paymentField = new JTextField();
        inputPanel.add(paymentField);
        addButton = new JButton("Add Building");
        inputPanel.add(addButton);
        updateButton = new JButton("Update Building");
        inputPanel.add(updateButton);
        deleteButton = new JButton("Delete Building");
        inputPanel.add(deleteButton);
        showButton = new JButton("Show Buildings");
        inputPanel.add(showButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBuilding();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateBuilding();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteBuilding();
            }
        });

        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayBuildings();
            }
        });

        setVisible(true);
    }

    private void addBuilding() {
        String streetName = streetField.getText();
        String houseNumber = houseField.getText();
        double basicPayment = Double.parseDouble(paymentField.getText());
        // Create a new Building instance and add it to the city
        Building building = new Building();
        building.setStreetName(streetName);
        building.setHouseNumber(houseNumber);
        building.setBasicMonthlyPaymentPerSqM(basicPayment);
        city.addBuilding(building);
        // Insert into the database
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO buildings (street_name, house_number, basic_monthly_payment_per_sqm) VALUES (?, ?, ?)")) {
            statement.setString(1, streetName);
            statement.setString(2, houseNumber);
            statement.setDouble(3, basicPayment);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                outputArea.append("New building added: " + streetName + " " + houseNumber + "\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateBuilding() {
        // Implement the updateBuilding() method
    }

    private void deleteBuilding() {
        // Implement the deleteBuilding() method
    }

    private void displayBuildings() {
        // Implement the displayBuildings() method
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}

