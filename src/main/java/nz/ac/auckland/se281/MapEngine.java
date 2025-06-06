package nz.ac.auckland.se281;

import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  private List<String> countries;
  private List<String> adjacencies;

  public MapEngine() {
    // add other code here if you wan
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {

    this.countries = Utils.readCountries();
    this.adjacencies = Utils.readAdjacencies();
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
    MessageCli.COUNTRY_INFO.printMessage(
        country, continent, value, "[" + String.join(", ", neighbours) + "]");
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
        break; // Exit the loop if the country information is successfully displayed
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(e.getMessage());
      }
    }

    // Show information about the country

  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
