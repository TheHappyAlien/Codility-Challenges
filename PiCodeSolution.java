package com.company;

import java.util.*;

class PiCodeSolution {

    public int solution(String P, String Q) {

        //region Setting variables
        int length = P.length();
        int alphabetLength = 26;
        int numberOfLettersUsed = 0;
        int iteratorPos = 0;

        byte[] obligatoryLettersMatrix = new byte[alphabetLength];
        LinkedList<Integer> obligatoryLetters = new LinkedList<>();
        byte [][] letterPositions = new byte[alphabetLength][length];

        // This list will hold unfinished potential solutions.
        LinkedList<LinkedList<Integer>> solutions = new LinkedList<>();
        LinkedList<LinkedList<Integer>> solutions2 = new LinkedList<>();


        LinkedList<HashSet<Character>> lastUsedSets = new LinkedList<>();
        LinkedList<HashSet<Character>> lastUsedSets2 = new LinkedList<>();
        HashSet<HashSet<Character>> usedLetterSets = new HashSet<>();
        //endregion

        //region Starting conditions
        lastUsedSets.add(new HashSet<>());

        LinkedList<Integer> startingPoint = new LinkedList<>();
        for (int i = 0; i < length; i++){ startingPoint.add(i);}

        for (int i = 0; i < length; i++){
            char charP = P.charAt(i);
            char charQ = Q.charAt(i);

            if (!(charP == charQ)) { letterPositions[charQ % alphabetLength][i] = 1; }
            else if (obligatoryLettersMatrix[charP % alphabetLength] == 0) {
                obligatoryLetters.add(charP % alphabetLength);
                obligatoryLettersMatrix[(charP % alphabetLength)] = 1;
            }

            letterPositions[charP % alphabetLength][i] = 1;
        }
        //endregion

        //region Logic

        for (int obligatoryLetter:obligatoryLetters) {
            numberOfLettersUsed++;
            Iterator<Integer> startSolutionIterator = startingPoint.iterator();

            byte[] obligatoryLetterPositions = letterPositions[obligatoryLetter];
            while (startSolutionIterator.hasNext()) {
                if (obligatoryLetterPositions[startSolutionIterator.next()] == 1) {startSolutionIterator.remove();}
            }
        }

        if (startingPoint.isEmpty()) { return numberOfLettersUsed; }

        solutions.add(startingPoint);

        while (numberOfLettersUsed < alphabetLength) {
            numberOfLettersUsed++;
            Iterator<HashSet<Character>> lastUsedSetsIterator = lastUsedSets.iterator();

            for (LinkedList<Integer> potentialSolution : solutions) {
                int emptyIndex = potentialSolution.getFirst();

                char letterToTry1 = P.charAt(emptyIndex);
                char letterToTry2 = Q.charAt(emptyIndex);

                HashSet<Character> currentCharacterSet1 = new HashSet<>(lastUsedSetsIterator.next());
                HashSet<Character> currentCharacterSet2 = new HashSet<>(currentCharacterSet1);

                currentCharacterSet2.add(letterToTry2);
                currentCharacterSet1.add(letterToTry1);


                LinkedList<Integer> newPotentialSolution = new LinkedList<>(potentialSolution);

                Iterator<Integer> potentialSolutionIterator = potentialSolution.iterator();
                byte[] letterPositionMatrix = letterPositions[letterToTry1 % alphabetLength];

                if (usedLetterSets.add(currentCharacterSet1)){

                    while (potentialSolutionIterator.hasNext()) {
                        if (letterPositionMatrix[potentialSolutionIterator.next()] == 1) {potentialSolutionIterator.remove();}
                    }

                    if (potentialSolution.isEmpty()) { return numberOfLettersUsed; }
                    solutions2.add(potentialSolution);
                    lastUsedSets2.add(currentCharacterSet1);
                }



                // Alternate route
                potentialSolutionIterator = newPotentialSolution.iterator();

                letterPositionMatrix = letterPositions[letterToTry2 % alphabetLength];
                if (usedLetterSets.add(currentCharacterSet2)){

                    while (potentialSolutionIterator.hasNext()) {
                        if (letterPositionMatrix[potentialSolutionIterator.next()] == 1) {potentialSolutionIterator.remove();}
                    }

                    if (newPotentialSolution.isEmpty()) { return numberOfLettersUsed; }
                    solutions2.add(newPotentialSolution);
                    lastUsedSets2.add(currentCharacterSet2);
                }
            }

            solutions.clear();
            solutions.addAll(solutions2);
            solutions2.clear();

            lastUsedSets.clear();
            lastUsedSets.addAll(lastUsedSets2);
            lastUsedSets2.clear();

        }
        //endregion
        return 0;
    }
}