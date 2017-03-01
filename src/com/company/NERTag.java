package com.company;

/**
 * Created by anranw on 2/26/17.
 */
public class NERTag extends Tag{
    private static TagBase tb=new TagBase();


    public static int getTagCount(){return tb.getTagCount();}

    @Override
    protected TagBase getTagBase() {
        return tb;
    }

    private NERTag(String tagString) {
        super(tagString);
    }

    public static Tag getTag(String tag){
        return tb.getTag(tag, NERTag::new);
    }
    public static Tag[] getAllTags(){
        Tag[] result=new Tag[tb.getTagCount()];
        for(int i=0;i<tb.getTagCount();i++)
            result[i]=tb.getTag(i);
        return result;
    }
}
