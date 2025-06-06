package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class CountryNode {
  String name;
  String continent;
  int fuelCost;
  List<String> neighbours;

  public CountryNode(String name, String continent, int fuelCost) {
    this.name = name;
    this.continent = continent;
    this.fuelCost = fuelCost;
    this.neighbours = new ArrayList<>();
  }
}
