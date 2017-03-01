package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * Created by anranw on 2/26/17.
 */
public class TagBase {
    public Map<String, Tag> tags=new HashMap<>();
    public Map<String, Integer> tagIndex=new HashMap<>();
    public ArrayList<Tag> tagList=new ArrayList<>();
    public int IND=0;
    public int getTagCount(){
        return IND;
    }
    public Tag getTag(String tagString, Function<String, Tag> alloc){
        if(tags.containsKey(tagString))return tags.get(tagString);
        else{
            Tag tag=alloc.apply(tagString);
            tags.put(tagString, tag);
            tagList.add(tag);
            tagIndex.put(tagString, IND++);
            return tag;
        }
    }
    public Tag getTag(int ind){
        return tagList.get(ind);
    }
}
