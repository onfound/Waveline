package com.company;


import java.util.ArrayList;
import java.util.Random;

class TextForPlay {
    private ArrayList<String> text = new ArrayList<>();

    TextForPlay() {
        text.add("Come on");
        text.add("Dance with me");
        text.add("Winter is coming");
        text.add("Already frozen");
        text.add("Tap space to play");
        text.add("Feel the cold?");
        text.add("Keep moving!");
        text.add("Happy curving");

    }
    String getText(){
        return text.get(new Random().nextInt(text.size()-1));
    }
}
