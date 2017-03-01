package com.company;

/**
 * Created by anranw on 2/26/17.
 */
public class FeatureFactory {
    private int wordCount;
    private int posTagCount;
    private int scTagCount;
    private int nerTagCount;

    private WordStat ws;

    public FeatureFactory(WordStat ws){
        this.ws=ws;
        wordCount=ws.getCount();
        posTagCount=POSTag.getTagCount();
        scTagCount=SCTag.getTagCount();
        nerTagCount=NERTag.getTagCount();
    }

    private char lower(char ch){
        if(ch>='A' && ch<='Z')return (char)(ch-'A'+1);
        else if(ch>='a' && ch<='z')return (char)(ch-'a'+1);
        else return 0;
    }

    public FeatureVec getFeatures(Sentence s, int k, NERTag tp, NERTag t){ //k starts from 0

        FeatureVec feature=new FeatureVec();


        int offset=0;
        //first: WordTag features: wordCount*nerTag
        int wordId=ws.getWordId(s.getWords().get(k).getWord());
        int nerId=t.getIndex();
        feature.set(offset+wordId*nerTagCount+nerId, 1);
        offset+=wordCount*nerTagCount;

        //second: Bigram tag features: nerTag*nerTag

        int preNerId=tp.getIndex();
        feature.set(offset+nerId*nerTagCount+preNerId, 1);
        offset+=nerTagCount*nerTagCount;

        //third: unigram TagTag features: posTagCount*scTagCount*nerTagCount

        int posId=s.getWords().get(k).getPosTag().getIndex();
        int scId=s.getWords().get(k).getSCTag().getIndex();
        feature.set(offset+posId*scTagCount*nerTagCount+scId*nerTagCount+nerId,1);
        offset+=posTagCount*scTagCount*nerTagCount;

        //fourth: (lowercase) 3-prefixTag feature: 27*27*27*nerTagCount
        int prefix=0;
        String wd=s.getWords().get(k).getWord();
        for(int i=0;i<Math.min(3, wd.length());i++)
            prefix=prefix*27+lower(wd.charAt(i));
        feature.set(offset+prefix*nerTagCount+nerId, 1);
        offset+=27*27*27*nerTagCount;

        //fifth: 3-suffixTag feature: 27*27*27*nerTagCount
        int suffix=0;
        wd=s.getWords().get(k).getWord();
        for(int i=0;i<Math.min(3, wd.length());i++)
            suffix=suffix*27+lower(wd.charAt(wd.length()-i-1));
        feature.set(offset+suffix*nerTagCount+nerId, 1);
        offset+=27*27*27*nerTagCount;

        return feature;
    }
}
