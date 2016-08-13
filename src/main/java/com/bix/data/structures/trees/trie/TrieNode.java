package com.bix.data.structures.trees.trie;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Trie data structure.
 * https://en.wikipedia.org/wiki/Trie
 */
public class TrieNode {

    private Map<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        this.children = new HashMap<>();
    }

    public void addNode(String value) {
        addNodeAux(value, 0);
    }

    private void addNodeAux(String value, int idxCurrentLetter) {
        if (idxCurrentLetter == value.length()) {
            isWord = true;
        } else {
            char currentLetter = value.charAt(idxCurrentLetter);
            if (!children.containsKey(currentLetter)) {
                children.put(currentLetter, new TrieNode());
            }

            TrieNode currentLetterNode = children.get(currentLetter);
            currentLetterNode.addNodeAux(value, idxCurrentLetter + 1);
        }
    }

    public boolean removeNode(String value) {
        return removeNodeAux(value, 0);
    }

    private boolean removeNodeAux(String value, int idxCurrentCharacter) {
        if (idxCurrentCharacter == value.length()) {
            if(isWord) {
                isWord = false;
                if(children.isEmpty())
                    return true;
            }
            
            return false;
        }
        
        char currentLetter = value.charAt(idxCurrentCharacter);
        TrieNode currentLetterNode = children.get(currentLetter);
        if(currentLetterNode != null) {
            boolean shouldChildBeRemoved = currentLetterNode.removeNodeAux(value, idxCurrentCharacter + 1);

            if (shouldChildBeRemoved) {
                children.remove(currentLetter);
            }

            boolean shouldBeRemoved = children.isEmpty() && !isWord;
            return shouldBeRemoved;
        }

        return false;
    }

    public boolean contains(String value) {
        return containsAux(value, 0);
    }

    private boolean containsAux(String value, int idxCurrentLetter) {
        if(idxCurrentLetter == value.length())
            return isWord;

        char currentLetter = value.charAt(idxCurrentLetter);
        TrieNode currentLetterNode = children.get(currentLetter);

        if(currentLetterNode != null)
            return currentLetterNode.containsAux(value, idxCurrentLetter + 1);

        return false;
    }

    public List<String> startsWith(String value) {
        List<String> words = new LinkedList<>();

        TrieNode subTree = getSubTree(value, 0);
        if(subTree != null) {
            subTree.toListOfWords(value, words);
        }

        return words;
    }

    private TrieNode getSubTree(String value, int idxCurrentLetter) {
        if(idxCurrentLetter == value.length())
            return this;

        final char currentLetter = value.charAt(idxCurrentLetter);
        TrieNode currentLetterNode = children.get(currentLetter);

        if(currentLetterNode != null)
            return currentLetterNode.getSubTree(value, idxCurrentLetter + 1);

        return null;
    }

    private void toListOfWords(String value, List<String> words) {

        if(isWord)
            words.add(value);

        Set<Character> characters = children.keySet();
        for(Character character : characters) {
            final String currentValue = value + character;
            final TrieNode subTree = children.get(character);

            subTree.toListOfWords(currentValue, words);
        }
    }
}