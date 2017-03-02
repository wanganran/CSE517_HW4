package com.company;

/**
 * Created by anranw on 2/26/17.
 */
public class SCTag extends Tag {
    private static TagBase tb=new TagBase();
    public static int getTagCount(){return tb.getTagCount();}
    @Override
    protected TagBase getTagBase() {
        return tb;
    }

    private SCTag(String tagString) {
        super(tagString);
    }

    public static Tag getTag(String tag){
        return tb.getTag(tag, SCTag::new);
    }
    public static void setReadOnly(boolean ro){
        tb.setReadOnly(ro);
    }
}
