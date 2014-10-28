package net.kuznetsovs;

import java.io.*;
import java.util.*;

public class Main {

    private static final ArrayList<String> words = new ArrayList<String>(40000);

    private static final String[] A =
            {"silent", "listen", "William Shakespeare", "I am a weakish speller", "Bucket Head", "Death Cube K"};


    protected static class Key {

        private final int[] letters = new int[26];
        private final int hash;

        public Key(String w) {
            char[] s = w.toLowerCase().toCharArray();
            int h = 0;
            for (char c : s) {
                if ('a' <= c && c <= 'z') {
                    letters[c - 'a']++;
                }
            }
            for (int c : letters) {
                h = h * letters.length + c;
            }
            hash = h;
        }

        public int hashCode() {
            return hash;
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof Key)) {
                return false;
            }
            Key k = (Key)o;
            for (int i = 0; i < letters.length; i++) {
                if (letters[i] != k.letters[i]) {
                    return false;
                }
            }
            return true;
        }

    }

    private static void close(Closeable c) throws IOException {
        if (c != null) {
            c.close();
        }
    }


    private static void readWords() throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("words.txt")));
            String line = reader.readLine();
            while (line != null) {
                words.add(line);
                line = reader.readLine();
            }
        } finally {
            close(reader);
        }
    }


    public static void main(String[] args) throws IOException {
        readWords();
        Collections.addAll(words, A);
        HashMap<Key, Integer> map = new HashMap<Key, Integer>(A.length * 2);
        for (int i = 0; i < words.size(); i++) {
            Key k = new Key(words.get(i));
            Integer index = map.get(k);
            if (index == null) {
                map.put(k, i);
            } else {
                System.out.println("\"" + words.get(i) + "\" is anagram of \"" + words.get(index) + "\"");
            }
        }
    }

}

/*
    time java net.kuznetsovs.Main
    real	0m0.578s
    user	0m0.531s
    sys 	0m0.089s
*/
