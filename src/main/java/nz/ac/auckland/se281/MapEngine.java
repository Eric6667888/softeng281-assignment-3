package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  private List<String> countries;
  private List<String> adjacencies;
  private String country;
  private String continent;
  private String value;
  private String[] neighbours;
  private String source;
  private String destination;
  private Map<String, CountryNode> graph = new LinkedHashMap<>();

  public MapEngine() {
    // add other code here if you wan
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {

    this.countries = Utils.readCountries();
    this.adjacencies = Utils.readAdjacencies();

    for (String line : countries) {
      String[] parts = line.split(",");
      String name = parts[0].trim();
      String continent = parts[1].trim();
      int fuel = Integer.parseInt(parts[2].trim());

      CountryNode node = new CountryNode(name, continent, fuel);
      graph.put(name, node);
    }

    for (String line : adjacencies) {
      String[] parts = line.split(",");
      String country = parts[0].trim();

      CountryNode node = graph.get(country);
      for (int i = 1; i < parts.length; i++) {
        String neighbour = parts[i].trim();
        node.neighbours.add(neighbour);
      }
    }
  }

  private void checkCountryInfo(String input) throws InvalidCountryException {
    input =
        Utils.capitalizeFirstLetterOfEachWord(input); // Capitalize the first letter of each word
    String country = null;
    String continent = null;
    String value = null;
    String[] neighbours = null;

    for (String line : countries) {
      String[] parts = line.split(","); // [country, continent, value]
      String countryName = parts[0].trim();
      if (countryName.equals(input)) {
        country = countryName;
        continent = parts[1].trim();
        value = parts[2].trim();

        break; // Exit the loop once we find the country
      }
    }
    if (country == null) {
      throw new InvalidCountryException(input);
    }

    for (String line : adjacencies) {
      String[] parts = line.split(","); // [country, neighbour1, neighbour2, ...]
      String countryName = parts[0].trim();
      if (countryName.equals(input)) {
        neighbours = new String[parts.length - 1];
        for (int i = 1; i < parts.length; i++) {
          neighbours[i - 1] = parts[i].trim();
        }
        break; // Exit the loop once we find the country
      }
    }
    this.country = country;
    this.continent = continent;
    this.value = value;
    this.neighbours = neighbours;
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    while (true) {
      MessageCli.INSERT_COUNTRY.printMessage();
      String input = Utils.scanner.nextLine().trim();

      if (input.isEmpty()) {
        MessageCli.INVALID_COUNTRY.printMessage(input);
        continue; // Continue if no input is provided
      }

      try {
        checkCountryInfo(input);
        MessageCli.COUNTRY_INFO.printMessage(
            country, continent, value, "[" + String.join(", ", neighbours) + "]");
        break; // Exit the loop if the country information is successfully displayed
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(e.getMessage());
      }
    }

    // Show information about the country

  }

  private List<String> findShortestRoute(String start, String goal) {
    start = Utils.capitalizeFirstLetterOfEachWord(start);
    goal = Utils.capitalizeFirstLetterOfEachWord(goal);
    Queue<List<String>> queue = new LinkedList<>();
    Set<String> visited = new HashSet<>();

    List<String> initialPath = new ArrayList<>();
    initialPath.add(start);
    queue.add(initialPath);
    visited.add(start);

    while (!queue.isEmpty()) {
      List<String> path = queue.poll();
      String current = path.get(path.size() - 1);

      if (current.equals(goal)) {
        return path;
      }

      for (String neighbor : graph.get(current).neighbours) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          List<String> newPath = new ArrayList<>(path);
          newPath.add(neighbor);
          queue.add(newPath);
        }
      }
    }

    return null;
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    while (true) {
      MessageCli.INSERT_SOURCE.printMessage();
      String source = Utils.scanner.nextLine().trim();

      if (source.isEmpty()) {
        MessageCli.INVALID_COUNTRY.printMessage(source);
        continue;
      }

      try {
        checkCountryInfo(source);
        this.source = source;
        break;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(e.getMessage());
      }
    }
    while (true) {
      MessageCli.INSERT_DESTINATION.printMessage();
      String destination = Utils.scanner.nextLine().trim();

      if (destination.isEmpty()) {
        MessageCli.INVALID_COUNTRY.printMessage(destination);
        continue;
      }

      try {
        checkCountryInfo(destination);
        this.destination = destination;
        break;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(e.getMessage());
      }
    }
    if (source.equals(destination)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return; // No cross-border travel is required
    }
    List<String> route = findShortestRoute(source, destination);
    MessageCli.ROUTE_INFO.printMessage(route.toString());
  }
}
