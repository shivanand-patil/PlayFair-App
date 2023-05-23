package com.example.cnslab;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class PlayfairDecode {
    String key;
    String plainText;
    char[][] matrix = new char[5][5];

    public PlayfairDecode(String key, String plainText) {
        // convert all the characters to lowercase
        this.key = key.toLowerCase();
        this.plainText = plainText.toLowerCase();
    }

    // function to remove duplicate characters from the key
    public void cleanPlayFairKey() {
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        StringBuilder newKey = new StringBuilder();

        for (int i = 0; i < key.length(); i++) {
            set.add(key.charAt(i));
        }

        for (Character character : set) {
            newKey.append(character);
        }

        key = newKey.toString();
    }

    // function to generate playfair cipher key table
    public void generateCipherKey() {
        Set<Character> set = new HashSet<>();

        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) == 'j') {
                continue;
            }
            set.add(key.charAt(i));
        }

        // remove repeated characters from the cipher key
        StringBuilder tempKey = new StringBuilder(key);

        for (int i = 0; i < 26; i++) {
            char ch = (char) (i + 97);
            if (ch == 'j') {
                continue;
            }

            if (!set.contains(ch)) {
                tempKey.append(ch);
            }
        }

        // create cipher key table
        for (int i = 0, idx = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = tempKey.charAt(idx++);
            }
        }

        System.out.println("Playfair Cipher Key Matrix:");
        for (int i = 0; i < 5; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

    // function to preprocess plaintext
    public String formatPlainText() {
        StringBuilder message = new StringBuilder();
        int len = plainText.length();

        for (int i = 0; i < len; i++) {
            // if plaintext contains the character 'j', replace it with 'i'
            if (plainText.charAt(i) == 'j') {
                message.append('i');
            } else {
                message.append(plainText.charAt(i));
            }
        }

        // if two consecutive characters are same, then insert character 'x' in between them
        for (int i = 0; i < message.length() - 1; i += 2) {
            if (message.charAt(i) == message.charAt(i + 1)) {
                message.insert(i + 1, 'x');
            }
        }

        // make the plaintext of even length
        if (message.length() % 2 == 1) {
            message.append('x'); // dummy character
        }

        return message.toString();
    }

    // function to group every two characters
    public String[] formPairs(String message) {
        int len = message.length();
        String[] pairs = new String[len / 2];

        for (int i = 0, cnt = 0; i < len / 2; i++) {
            pairs[i] = message.substring(cnt, cnt + 2);
            cnt += 2;
        }

        return pairs;
    }

    // function to get the position of a character in the key table
    public int[] getCharPos(char ch) {
        int[] keyPos = new int[2];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == ch) {
                    keyPos[0] = i;
                    keyPos[1] = j;
                    return keyPos;
                }
            }
        }

        return keyPos;
    }

    public String decryptMessage() {
        String message = formatPlainText();
        String[] msgPairs = formPairs(message);
        StringBuilder decText = new StringBuilder();

        for (String msgPair : msgPairs) {
            char ch1 = msgPair.charAt(0);
            char ch2 = msgPair.charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);

            // if both characters are in the same row
            if (ch1Pos[0] == ch2Pos[0]) {
                ch1Pos[1] = (ch1Pos[1] - 1 + 5) % 5;
                ch2Pos[1] = (ch2Pos[1] - 1 + 5) % 5;
            }
            // if both characters are in the same column
            else if (ch1Pos[1] == ch2Pos[1]) {
                ch1Pos[0] = (ch1Pos[0] - 1 + 5) % 5;
                ch2Pos[0] = (ch2Pos[0] - 1 + 5) % 5;
            }
            // if both characters are in different rows and columns
            else {
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }

            // get the corresponding plaintext characters from the key matrix
            decText.append(matrix[ch1Pos[0]][ch1Pos[1]]).append(matrix[ch2Pos[0]][ch2Pos[1]]);
        }

        System.out.println(decText);
        return decText.toString();
    }
}
