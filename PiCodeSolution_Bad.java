package com.company;

import java.util.ArrayList;
import java.util.LinkedList;

public class PiCodeSolution_Bad {
    public int solution(String P, String Q) {
        int alphabetSize = 26;
        int numberOfLettersUsed = 27;

        ArrayList<Boolean>[] usedLetters = new ArrayList[alphabetSize];
        ArrayList<boolean[]> letterCombinations = new ArrayList<>();
        letterCombinations.add(new boolean[alphabetSize]);
        LinkedList<boolean[]> newCombinationsToAdd = new LinkedList<>();

        // ascii a-z characters are 97-122, so we can map them to the usedLetters tab by doing (char-97)

        for (int i = 0; i < P.length(); i++){
            char charP = (char)(P.charAt(i) - 97);
            char charQ = (char)(Q.charAt(i) - 97);


            for (boolean[] letterCombination:letterCombinations) {
                if (!letterCombination[charP] && !letterCombination[charQ]){
                    boolean[] copy = new boolean[alphabetSize];
                    System.arraycopy(letterCombination, 0, copy, 0, alphabetSize);
                    copy[charP] = true;
                    newCombinationsToAdd.add(copy);
                    letterCombination[charQ] = true;
                }
            }

            letterCombinations.addAll(newCombinationsToAdd);
            newCombinationsToAdd.clear();
        }

        for (boolean[] letterCombination:letterCombinations) {
            int numberOfLettersUsedHere = 0;
            for(int i = 0; i < alphabetSize; i++){
                if (letterCombination[i]) { numberOfLettersUsedHere++; }
            }
            if (numberOfLettersUsedHere < numberOfLettersUsed) {numberOfLettersUsed = numberOfLettersUsedHere; }
        }
        return numberOfLettersUsed;
    }
}
