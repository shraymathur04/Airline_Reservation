
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

class InvalidDestinationException extends Exception {
    public InvalidDestinationException(String message) {
        super(message);
    }
}

class Flight {
    private String flightNumber;
    String origin;
    String destination;
    int totalSeats;
    int bookedSeats;
    double basePrice;

    public Flight(String flightNumber, String origin, String destination, int totalSeats, double basePrice) throws InvalidDestinationException {
        if (origin.equalsIgnoreCase(destination)) {
            throw new InvalidDestinationException("Origin and destination cannot be the same.");
        }
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.bookedSeats = 0;
        this.basePrice = basePrice;
    }

    public boolean isFullyBooked() {
        return bookedSeats >= totalSeats;
    }

    public double getDynamicPrice() {
        double occupancyRate = (double) bookedSeats / totalSeats;
        return occupancyRate > 0.8 ? basePrice * 1.2 : basePrice;
    }

    public boolean bookSeat() {
        if (!isFullyBooked()) {
            bookedSeats++;
            return true;
        } else {
            System.out.println("Flight is fully booked.");
            return false;
        }
    }

    public void displayFlightDetails(TextArea textArea) {
        String details = "Flight Number: " + flightNumber + "\n" +
                "Origin: " + origin + "\n" +
                "Destination: " + destination + "\n" +
                "Seats Available: " + (totalSeats - bookedSeats) + "/" + totalSeats + "\n" +
                "Price per Seat: $" + getDynamicPrice() + "\n\n";
        textArea.appendText(details);
    }

    public boolean matches(String origin, String destination) {
        return this.origin.equalsIgnoreCase(origin) && this.destination.equalsIgnoreCase(destination);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public boolean isAvailable(int seats) {
        return bookedSeats + seats <= totalSeats;
    }

    public void bookSeats(int seats) {
        bookedSeats += seats;
    }
}

class FlightCapacityException extends Exception {
    public FlightCapacityException(String message) {
        super(message);
    }
}

class InvalidFlightException extends Exception {
    public InvalidFlightException(String message) {
        super(message);
    }
}

class Manager {
    private Flight[] flights;
    private int flightCount;

    Manager(int maxFlight) {
        flights = new Flight[maxFlight];
        flightCount = 0;
    }

    void addFlight(Flight flight) {
        try {
            if (flightCount < flights.length) {
                flights[flightCount] = flight;
                flightCount++;
            } else {
                throw new FlightCapacityException("Cannot Add Flight, capacity Full.");
            }
        } catch (FlightCapacityException e) {
            System.out.println(e.getMessage());
        }
    }

    void displayAllFlights(TextArea textArea) {
        textArea.clear();
        if (flightCount == 0) {
            textArea.appendText("No flights available.\n");
        } else {
            for (Flight flight : flights) {
                if (flight != null) {
                    flight.displayFlightDetails(textArea);
                }
            }
        }
    }

    public Flight searchFlightByRoute(String origin, String destination) {
        for (Flight flight : flights) {
            if (flight != null && flight.matches(origin, destination)) {
                return flight;
            }
        }
        return null;
    }
}

class Traveller {
    private String name;
    private String id;
    private String contactInfo;
    private Flight bookedFlight;
    private int seatsBooked;

    public Traveller(String name, String id, String contactInfo) {
        this.name = name;
        this.id = id;
        this.contactInfo = contactInfo;
        this.bookedFlight = null;
        this.seatsBooked = 0;
    }

    public Flight searchFlights(Manager manager, String origin, String destination) {
        return manager.searchFlightByRoute(origin, destination);
    }

    public void bookMultipleSeats(Flight flight, int seats, TextArea searchResultsArea) {
        if (flight.isAvailable(seats)) {
            flight.bookSeats(seats);
            this.bookedFlight = flight;
            this.seatsBooked = seats;

            double totalCost = seats * flight.getDynamicPrice();
            searchResultsArea.setText("Booking successful for " + seats + " seat(s) on flight " + flight.getFlightNumber() +
                    "\nTotal Cost: $" + totalCost);
            generateInvoice();
        } else {
            searchResultsArea.setText("Not enough available seats.");
        }
    }


    private void generateInvoice() {
        System.out.println("Invoice generated for " + seatsBooked + " seat(s) on flight " + bookedFlight.getFlightNumber());
    }
}

class AddFlightHandler implements EventHandler<ActionEvent> {
    private TextField flightField, originField, destinationField, seatsField, priceField;
    private Manager flightManager;
    private TextArea flightDetailsArea;

    public AddFlightHandler(TextField flightField, TextField originField, TextField destinationField, TextField seatsField,
                            TextField priceField, Manager flightManager, TextArea flightDetailsArea) {
        this.flightField = flightField;
        this.originField = originField;
        this.destinationField = destinationField;
        this.seatsField = seatsField;
        this.priceField = priceField;
        this.flightManager = flightManager;
        this.flightDetailsArea = flightDetailsArea;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            String flightNumber = flightField.getText();
            String origin = originField.getText();
            String destination = destinationField.getText();
            int totalSeats = Integer.parseInt(seatsField.getText());
            double basePrice = Double.parseDouble(priceField.getText());

            Flight newFlight = new Flight(flightNumber, origin, destination, totalSeats, basePrice);
            flightManager.addFlight(newFlight);
            flightDetailsArea.setText("Flight added successfully!\n");

            // Clear fields after successful addition
            flightField.clear();
            originField.clear();
            destinationField.clear();
            seatsField.clear();
            priceField.clear();

        } catch (InvalidDestinationException e) {
            flightDetailsArea.setText("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            flightDetailsArea.setText("Error: Please enter valid numbers for seats and price.");
        }
    }
}

public class AirlineReservationSystem extends Application {

    private Manager flightManager = new Manager(10); // Maximum of 10 flights

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Airline Reservation System");

        // Main Scene with Manager and Traveller options
        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setVgap(20);

        Button managerButton = new Button("Manager");
        Button travellerButton = new Button("Traveller");

        mainGrid.add(managerButton, 0, 0);
        mainGrid.add(travellerButton, 0, 1);

        Scene mainScene = new Scene(mainGrid, 300, 200);
        primaryStage.setScene(mainScene);

        // Manager Interface
        Scene managerScene = createManagerScene(primaryStage, mainScene);

        // Traveller Interface
        Scene travellerScene = createTravellerScene(primaryStage, mainScene);

        // Event handlers to switch between scenes
        managerButton.setOnAction(event -> primaryStage.setScene(managerScene));
        travellerButton.setOnAction(event -> primaryStage.setScene(travellerScene));

        primaryStage.show();
    }

    private Scene createManagerScene(Stage primaryStage, Scene mainScene) {
        GridPane managerGrid = new GridPane();
        managerGrid.setHgap(10);
        managerGrid.setVgap(10);
        managerGrid.setAlignment(Pos.CENTER);

        Label flightLabel = new Label("Flight Number:");
        TextField flightField = new TextField();
        managerGrid.add(flightLabel, 0, 0);
        managerGrid.add(flightField, 1, 0);

        Label originLabel = new Label("Origin:");
        TextField originField = new TextField();
        managerGrid.add(originLabel, 0, 1);
        managerGrid.add(originField, 1, 1);

        Label destinationLabel = new Label("Destination:");
        TextField destinationField = new TextField();
        managerGrid.add(destinationLabel, 0, 2);
        managerGrid.add(destinationField, 1, 2);

        Label seatsLabel = new Label("Total Seats:");
        TextField seatsField = new TextField();
        managerGrid.add(seatsLabel, 0, 3);
        managerGrid.add(seatsField, 1, 3);

        Label priceLabel = new Label("Base Price:");
        TextField priceField = new TextField();
        managerGrid.add(priceLabel, 0, 4);
        managerGrid.add(priceField, 1, 4);

        TextArea flightDetailsArea = new TextArea();
        flightDetailsArea.setEditable(false);
        managerGrid.add(flightDetailsArea, 0, 7, 2, 1);

        Button addFlightButton = new Button("Add Flight");
        Button displayFlightsButton = new Button("Display All Flights");
        Button backButton = new Button("Back");

        managerGrid.add(addFlightButton, 0, 5);
        managerGrid.add(displayFlightsButton, 1, 5);
        managerGrid.add(backButton, 1, 6);

        addFlightButton.setOnAction(new AddFlightHandler(flightField, originField, destinationField, seatsField, priceField, flightManager, flightDetailsArea));

        displayFlightsButton.setOnAction(event -> flightManager.displayAllFlights(flightDetailsArea));

        backButton.setOnAction(event -> primaryStage.setScene(mainScene));

        return new Scene(managerGrid, 400, 400);
    }

    private Scene createTravellerScene(Stage primaryStage, Scene mainScene) {
        GridPane travellerGrid = new GridPane();
        travellerGrid.setHgap(10);
        travellerGrid.setVgap(10);
        travellerGrid.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        travellerGrid.add(nameLabel, 0, 0);
        travellerGrid.add(nameField, 1, 0);

        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        travellerGrid.add(idLabel, 0, 1);
        travellerGrid.add(idField, 1, 1);

        Label contactLabel = new Label("Contact Info:");
        TextField contactField = new TextField();
        travellerGrid.add(contactLabel, 0, 2);
        travellerGrid.add(contactField, 1, 2);

        Label originLabel = new Label("Origin:");
        TextField originField = new TextField();
        travellerGrid.add(originLabel, 0, 3);
        travellerGrid.add(originField, 1, 3);

        Label destinationLabel = new Label("Destination:");
        TextField destinationField = new TextField();
        travellerGrid.add(destinationLabel, 0, 4);
        travellerGrid.add(destinationField, 1, 4);

        Label seatsLabel = new Label("Seats to Book:");
        TextField seatsField = new TextField();
        travellerGrid.add(seatsLabel, 0, 5);
        travellerGrid.add(seatsField, 1, 5);

        TextArea searchResultsArea = new TextArea();
        searchResultsArea.setEditable(false);
        travellerGrid.add(searchResultsArea, 0, 7, 2, 1);

        Button searchButton = new Button("Search & Book Flight");
        Button backButton = new Button("Back");

        travellerGrid.add(searchButton, 0, 6);
        travellerGrid.add(backButton, 1, 6);

        searchButton.setOnAction(event -> {
            String origin = originField.getText();
            String destination = destinationField.getText();
            int seatsToBook;
            try {
                seatsToBook = Integer.parseInt(seatsField.getText());
            } catch (NumberFormatException e) {
                searchResultsArea.setText("Error: Enter a valid number of seats.");
                return;
            }

            String name = nameField.getText();
            String id = idField.getText();
            String contactInfo = contactField.getText();

            Traveller traveller = new Traveller(name, id, contactInfo);
            Flight matchedFlight = traveller.searchFlights(flightManager, origin, destination);

            if (matchedFlight != null) {
                if (matchedFlight.isAvailable(seatsToBook)) {
                    traveller.bookMultipleSeats(matchedFlight, seatsToBook, searchResultsArea);

                    // Clear the fields after successful booking
                    nameField.clear();
                    idField.clear();
                    contactField.clear();
                    originField.clear();
                    destinationField.clear();
                    seatsField.clear();

                } else {
                    searchResultsArea.setText("Not enough seats available for this flight.");
                }
            } else {
                searchResultsArea.setText("No matching flight found for the specified route.");
            }
        });


        backButton.setOnAction(event -> primaryStage.setScene(mainScene));

        return new Scene(travellerGrid, 400, 400);
    }

    public static void main(String[] args) {
        launch(args);
    }
}