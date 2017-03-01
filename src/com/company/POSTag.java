package com.company;

/**
 * Created by anranw on 2/26/17.
 */
public class POSTag extends Tag{
    private static TagBase tb=new TagBase();
    public static int getTagCount(){return tb.getTagCount();}
    @Override
    protected TagBase getTagBase() {
        return tb;
    }

    private POSTag(String tagString) {
        super(tagString);
    }

    public static Tag getTag(String tag){
        return tb.getTag(tag, POSTag::new);
    }
}
