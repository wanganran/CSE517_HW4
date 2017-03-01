package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanganran on 1/28/17.
 */
public abstract class Tag {
    protected abstract TagBase getTagBase();
    private String tagString;
    protected Tag(String tagString){
        this.tagString=tagString;
    }
    public int getIndex(){
        return getTagBase().tagIndex.get(tagString);
    }


    @Override
    public int hashCode() {
        return tagString.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(this.getClass()) && ((Tag)obj).tagString.equals(tagString);
    }

    @Override
    public String toString() {
        return tagString;
    }
}
