package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class CountryNode {
  String country;
  String continent;
  int fuelCost;
  List<String> neighbours;

  public CountryNode(String country, String continent, int fuelCost, List<String> neighbours) {
    this.country = country;
    this.continent = continent;
    this.fuelCost = fuelCost;
    this.neighbours = new ArrayList<>();
  }
}
