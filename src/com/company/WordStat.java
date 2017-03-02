package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anranw on 2/26/17.
 */
public class WordStat {
    private ArrayList<String> textList=new ArrayList<String>();
    private Map<String, Integer> textIndex=new HashMap<String,Integer>();

    public WordStat(){}
    public void add(Word w){
        if(!textIndex.containsKey(w.getWord())){
            textList.add(w.getWord());
            textIndex.put(w.getWord(), textList.size()-1);
        }
    }
    public int getCount(){
        return textList.size();
    }
    public boolean exists(String w){
        return textIndex.containsKey(w);
    }
    public int getWordId(String w){
        return textIndex.get(w);
    }
}
