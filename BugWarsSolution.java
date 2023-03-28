package com.company;

public class BugWarsSolution {

    int[][] fuelMatrix;

    private int distance(int city1, int city2, int[] positionMatrix) {
        return Math.abs(positionMatrix[city1] - positionMatrix[city2]);
    }

    private void updateFuel(int startCity, int endCity, int newFuelAmmount) {
        if (fuelMatrix[startCity][endCity] < newFuelAmmount) {
            fuelMatrix[startCity][endCity] = newFuelAmmount;
        }
    }

    public int solution(int[] fuel, int[] position) {
        int maxNumberOfCitiesVisited = 0;
        int numberOfCities = position.length;

        //region Setting up the fuel matrix
        fuelMatrix = new int[numberOfCities][numberOfCities];

        for (int startCity = 0; startCity < numberOfCities; startCity++) {
            for (int endCity = 0; endCity < numberOfCities; endCity++) {

                // Add the fuel in each city on the diagonal of the matrix.
                if (startCity == endCity) {fuelMatrix[startCity][endCity] = fuel[startCity];}
                else {fuelMatrix[startCity][endCity] = -1;} // -1 will be the flag, that the city is unreachable.
            }
        }
        //endregion

        // Move in a diagonal fashion.
        for (int offset = 0; offset < numberOfCities; offset++) {
            for (int city1 = 0; city1 + offset < numberOfCities; city1++) {
                int city2 = city1 + offset;
                int distance;

                if (city2 < numberOfCities - 1) {
                    // Updating the max ammount of fuel that we can have after travelling to the city to the right of city2 (from city1)

                    // Checking if we can keep going right from city1 to city2+1.
                    if (fuelMatrix[city1][city2] >= (distance = distance(city2, city2 + 1, position))) {
                        updateFuel(city1, city2 + 1, fuelMatrix[city1][city2] + fuel[city2 + 1] - distance);
                    }

                    // Check if it would be better to go to the left (from city2 to city1), and then go to the right (to city2 + 1).
                    if (fuelMatrix[city2][city1] >= (distance = distance(city1, city2 + 1, position))) {
                        updateFuel(city1, city2 + 1, fuelMatrix[city2][city1] + fuel[city2 + 1] - distance);
                    }
                }

                if (city1 != 0) {
                    // Updating the max ammount of fuel that we can have after travelling to the city to the left of city1 (from city2)

                    // Checking if we can keep going left from city2 to city1-1.
                    if (fuelMatrix[city2][city1] >= (distance = distance(city1, city1 - 1, position))) {
                        updateFuel(city2, city1 - 1, fuelMatrix[city2][city1] + fuel[city1 - 1] - distance);
                    }

                    // Check if it would be better to go to the right (from city1 to city2), and then go to the left (to city1 - 1).
                    if (fuelMatrix[city1][city2] >= (distance = distance(city2, city1 - 1, position))) {
                        updateFuel(city2, city1 - 1, fuelMatrix[city1][city2] + fuel[city1 - 1] - distance);
                    }
                }
            }
        }

        // Check how far apart *number-wise* are the left and right-most cities (all the cities in between had to have been visited, as it would be wasteful to skip them)
        // Save the largest difference, it is the largest number of cities that can be visited.
        for (int city1 = 0; city1 < numberOfCities; city1++) {
            for (int city2 = 0; city2 < numberOfCities; city2++) {
                if (fuelMatrix[city1][city2] >= 0) {
                    maxNumberOfCitiesVisited = Math.max(maxNumberOfCitiesVisited, Math.abs(city2 - city1) + 1);
                }
            }
        }

        return maxNumberOfCitiesVisited;
    }
}
