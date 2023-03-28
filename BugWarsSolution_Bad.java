package com.company;

public class BugWarsSolution_Bad {



    private int[] fuel;
    private int[] pos;
    private int numberOfCities;

    private int tripLength(int city1Pos, int city2Pos) {
        return Math.abs(city1Pos - city2Pos);
    }

    public int solution(int[] A, int[] X) {
        // write your code in Java SE 8

        fuel = A;
        pos = X;
        numberOfCities = X.length;

        int nextStartingCityRight;
        int nextStartingCityLeft;

        int[] tripInfo;

        int maxNumberOfCitiesVisited = 0;
        int numberOfCitiesVisited;

        for (int i = 0; i < numberOfCities; i++) {

            nextStartingCityRight = i;
            tripInfo = tripToTheRight(nextStartingCityRight);


            if ((numberOfCitiesVisited = tripInfo[0]) > maxNumberOfCitiesVisited) {
                maxNumberOfCitiesVisited = numberOfCitiesVisited;
            }

            nextStartingCityLeft = numberOfCities - i - 1;
            tripInfo = tripToTheLeft(nextStartingCityLeft);

            if ((numberOfCitiesVisited = tripInfo[0]) > maxNumberOfCitiesVisited) {
                maxNumberOfCitiesVisited = numberOfCitiesVisited;
            }

        }

        return maxNumberOfCitiesVisited;
    }

    private int[] tripToTheRight(int startingCity) {

        int[] returnInfo = new int[2];

        int numberOfCitiesVisited = 1;
        int currentCityNumber = startingCity;
        int fuelInTheTank = fuel[currentCityNumber];
        int tripLength;
        int potentialLargestNumberOfCitiesVisited = 0;

        while (currentCityNumber < numberOfCities - 1) {
            if (startingCity > 0 && (tripLength = tripLength(pos[currentCityNumber], pos[startingCity - 1])) <= fuelInTheTank) {
                int checkForMoreCities;
                if ((checkForMoreCities = numberOfCitiesVisited + checkLeft(startingCity - 1, fuelInTheTank - tripLength)) > potentialLargestNumberOfCitiesVisited) {
                    potentialLargestNumberOfCitiesVisited = checkForMoreCities;
                }
            }

            if ((tripLength = tripLength(pos[currentCityNumber], pos[currentCityNumber + 1])) <= fuelInTheTank) {
                fuelInTheTank -= tripLength;
                numberOfCitiesVisited++;
                currentCityNumber++;
                fuelInTheTank += fuel[currentCityNumber];
            } else break;
        }

        if (startingCity > 0 && (tripLength = tripLength(pos[currentCityNumber], pos[startingCity - 1])) <= fuelInTheTank) {
            fuelInTheTank -= tripLength;
            numberOfCitiesVisited += checkLeft(startingCity - 1, fuelInTheTank);
        }

        returnInfo[0] = Math.max(numberOfCitiesVisited, potentialLargestNumberOfCitiesVisited);
        returnInfo[1] = currentCityNumber;

        return returnInfo;
    }

    private int[] tripToTheLeft(int startingCity) {

        int[] returnInfo = new int[2];

        int numberOfCitiesVisited = 1;
        int currentCityNumber = startingCity;
        int fuelInTheTank = fuel[currentCityNumber];
        int tripLength;
        int potentialLargestNumberOfCitiesVisited = 0;

        while (currentCityNumber > 0) {
            if (startingCity < numberOfCities - 1 && (tripLength = tripLength(pos[currentCityNumber], pos[startingCity + 1])) <= fuelInTheTank) {
                int checkForMoreCities;
                if ((checkForMoreCities = numberOfCitiesVisited + checkRight(startingCity + 1, fuelInTheTank - tripLength)) > potentialLargestNumberOfCitiesVisited) {
                    potentialLargestNumberOfCitiesVisited = checkForMoreCities;
                }
            }

            if ((tripLength = tripLength(pos[currentCityNumber], pos[currentCityNumber - 1])) <= fuelInTheTank) {
                fuelInTheTank -= tripLength;
                numberOfCitiesVisited++;
                currentCityNumber--;
                fuelInTheTank += fuel[currentCityNumber];
            } else break;
        }

        if (startingCity < numberOfCities - 1 && (tripLength = tripLength(pos[currentCityNumber], pos[startingCity + 1])) <= fuelInTheTank) {
            fuelInTheTank -= tripLength;
            numberOfCitiesVisited += checkRight(startingCity + 1, fuelInTheTank);
        }

        returnInfo[0] = Math.max(numberOfCitiesVisited, potentialLargestNumberOfCitiesVisited);
        returnInfo[1] = currentCityNumber;

        return returnInfo;
    }

    private int checkRight(int startingCity, int startingFuel) {

        int numberOfCitiesVisited = 1;
        int currentCityNumber = startingCity;
        int fuelInTheTank = startingFuel + fuel[currentCityNumber];
        int tripLength;

        while (currentCityNumber < numberOfCities - 1) {
            if ((tripLength = tripLength(pos[currentCityNumber], pos[currentCityNumber + 1])) <= fuelInTheTank) {
                fuelInTheTank -= tripLength;
                numberOfCitiesVisited++;
                currentCityNumber++;
                fuelInTheTank += fuel[currentCityNumber];
            } else {
                break;
            }
        }

        return numberOfCitiesVisited;
    }

    private int checkLeft(int startingCity, int startingFuel) {

        int numberOfCitiesVisited = 1;
        int currentCityNumber = startingCity;
        int fuelInTheTank = startingFuel + fuel[currentCityNumber];
        int tripLength;

        while (currentCityNumber > 0) {
            if ((tripLength = tripLength(pos[currentCityNumber], pos[currentCityNumber - 1])) <= fuelInTheTank) {
                fuelInTheTank -= tripLength;
                numberOfCitiesVisited++;
                currentCityNumber--;
                fuelInTheTank += fuel[currentCityNumber];
            } else {
                break;
            }
        }

        return numberOfCitiesVisited;
    }
}


