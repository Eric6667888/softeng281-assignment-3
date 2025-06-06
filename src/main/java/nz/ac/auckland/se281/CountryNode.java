package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class CountryNode {
  private String name;
  private String continent;
  private int fuelCost;
  private List<String> neighbours;

  public CountryNode(String name, String continent, int fuelCost) {
    this.name = name;
    this.continent = continent;
    this.fuelCost = fuelCost;
    this.neighbours = new ArrayList<>();
  }

  public List<String> getNeighbours() {
    return neighbours;
  }

  public String getContinent() {
    return continent;
  }

  public int getFuelCost() {
    return fuelCost;
  }

  public String getName() {
    return name;
  }
}
