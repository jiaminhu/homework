package edu.neu.madcourse.nuillegalbronze.boggle;
import java.io.*;
import java.util.*;

// String prefix tree.
public class Trie extends AbstractCollection<String> implements Set<String> {
    private static final int ABC = 'Z' - 'A' + 1;
    
    /**
    Reads the given file, treating it as a dictionary containing one word per line. 
    Returns a trie (prefix tree) collection of all words in the dictionary.
    */
    public static Trie fromFile(String filename) {
        Trie trie = new Trie();
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNext()) {
                    trie.add(in.next());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return trie;
    }

    private TrieNode root = new TrieNode();
    private int size = 0;
    
    // Adds the following word to the prefix tree.
    public boolean add(String word) {
        TrieNode node = this.walk(word, true);
        if (!node.wholeWord) {
            node.wholeWord = true;
            this.size++;
        }
        return true;
    }
    
    // Returns whether the given string represents a prefix or complete word
    // in this tree.
    public boolean containsPrefix(String prefix) {
        return this.walk(prefix, false) != null;
    }
    
    // Required by Java Collection interface.
    public boolean contains(Object word) {
        TrieNode node = this.walk((String) word, false);
        return node != null && node.wholeWord;
    }
    
    // helper method used by add and contains
    private TrieNode walk(String word, boolean create) {
        if (word == null) {
            throw new NullPointerException();
        }

        TrieNode node = this.root;

        for (char ch : word.toCharArray()) {
            if (ch < 'A' || ch > 'Z') {
                throw new IllegalArgumentException();
            }
            
            int index = ch - 'A';
            if (node.children[index] == null) {
                if (create) {
                    node.children[index] = new TrieNode();
                } else {
                    return null;
                }
            }
            node = node.children[index];
        }
        
        return node;
    }
    
    // Required by Java Collection interface.
    public Iterator<String> iterator() {
        throw new UnsupportedOperationException();
    }
    
    // Returns the number of words in this tree.
    public int size() {
        return this.size;
    }
    
    // Represents a single node of a prefix tree.
    private class TrieNode {
        public TrieNode[] children = new TrieNode[ABC];
        
        // set to true if this node represents the end of a word
        public boolean wholeWord = false;
    }
}
