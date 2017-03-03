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
        System.out.println(wordCount+" "+posTagCount+" "+scTagCount+" "+nerTagCount);
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
        int nerId=t.getIndex();

        if(ws.exists(s.getWords().get(k).getWord())) {
            int wordId = ws.getWordId(s.getWords().get(k).getWord());
            feature.set(offset + wordId * nerTagCount + nerId, 1);
        }
        offset += wordCount * nerTagCount;

        //1.1 previous word+Tag

        if(k>0 && ws.exists(s.getWords().get(k-1).getWord())) {
            int wordId = ws.getWordId(s.getWords().get(k-1).getWord());
            feature.set(offset + wordId * nerTagCount + nerId, 1);
        }
        offset += wordCount * nerTagCount;

        //1.2 next word+Tag
        if(k!=s.getWords().size()-1 && ws.exists(s.getWords().get(k+1).getWord())) {
            int wordId = ws.getWordId(s.getWords().get(k+1).getWord());
            feature.set(offset + wordId * nerTagCount + nerId, 1);
        }
        offset += wordCount * nerTagCount;

        //1.3 previous 2 word+Tag

        if(k>1 && ws.exists(s.getWords().get(k-2).getWord())) {
            int wordId = ws.getWordId(s.getWords().get(k-2).getWord());
            feature.set(offset + wordId * nerTagCount + nerId, 1);
        }
        offset += wordCount * nerTagCount;

        //1.4 next 2 word+Tag
        if(k<s.getWords().size()-2 && ws.exists(s.getWords().get(k+2).getWord())) {
            int wordId = ws.getWordId(s.getWords().get(k+2).getWord());
            feature.set(offset + wordId * nerTagCount + nerId, 1);
        }
        offset += wordCount * nerTagCount;

        //1.3 word+tags
        Tag posTag=s.getWords().get(k).getPosTag();
        Tag scTag=s.getWords().get(k).getSCTag();
/*
        if(posTag!=null && scTag !=null) {
            int posId = posTag.getIndex();
            int scId = scTag.getIndex();
            if (ws.exists(s.getWords().get(k).getWord())) {
                int wordId = ws.getWordId(s.getWords().get(k).getWord());
                feature.set(offset + wordId * nerTagCount*scTagCount*posTagCount+
                        posId*nerTagCount*scTagCount+
                        scId*nerTagCount+
                        nerId, 1);
            }
        }
        offset += wordCount * nerTagCount*scTagCount*posTagCount;
*/

        //2.1 Unigram tag: nerTag

        feature.set(offset+nerId, 1);
        offset+=nerTagCount;

        //2.2: Bigram tag features: nerTag*nerTag

        int preNerId=tp.getIndex();
        feature.set(offset+nerId*nerTagCount+preNerId, 1);
        offset+=nerTagCount*nerTagCount;

        //third: unigram TagTag features: posTagCount*scTagCount*nerTagCount

        //Tag posTag=s.getWords().get(k).getPosTag();
        //Tag scTag=s.getWords().get(k).getSCTag();
        if(posTag!=null && scTag !=null) {
            int posId = posTag.getIndex();
            int scId = scTag.getIndex();
            feature.set(offset + posId * scTagCount * nerTagCount + scId * nerTagCount + nerId, 1);
        }
        offset += posTagCount * scTagCount * nerTagCount;
        if(posTag!=null) {
            int posId = posTag.getIndex();
            feature.set(offset + posId * nerTagCount + nerId, 1);
        }
        offset += posTagCount * nerTagCount;

        //3.2 unigram previous TagTag features
        if(k>0) {
            Tag pposTag = s.getWords().get(k-1).getPosTag();
            Tag pscTag = s.getWords().get(k-1).getSCTag();
            if (pposTag != null && pscTag != null) {
                int posId = pposTag.getIndex();
                int scId = pscTag.getIndex();
                feature.set(offset + posId * scTagCount * nerTagCount + scId * nerTagCount + nerId, 1);
            }
        }
        offset += posTagCount * scTagCount * nerTagCount;

        //3.3 unigram next TagTag features
        if(k<s.getWords().size()-1) {
            Tag pposTag = s.getWords().get(k+1).getPosTag();
            Tag pscTag = s.getWords().get(k+1).getSCTag();
            if (pposTag != null && pscTag != null) {
                int posId = pposTag.getIndex();
                int scId = pscTag.getIndex();
                feature.set(offset + posId * scTagCount * nerTagCount + scId * nerTagCount + nerId, 1);
            }
        }
        offset += posTagCount * scTagCount * nerTagCount;

        //fourth: (lowercase) 2-prefixTag feature: 27*27*nerTagCount
        int prefix=0;
        String wd=s.getWords().get(k).getWord();
        for(int i=0;i<Math.min(2, wd.length());i++)
            prefix=prefix*27+lower(wd.charAt(i));
        feature.set(offset+prefix*nerTagCount+nerId, 1);
        offset+=27*27*nerTagCount;

        //fifth: 3-suffixTag feature: 27*27*27*nerTagCount
        int suffix=0;
        wd=s.getWords().get(k).getWord();
        for(int i=0;i<Math.min(3, wd.length());i++)
            suffix=suffix*27+lower(wd.charAt(wd.length()-i-1));
        feature.set(offset+suffix*nerTagCount+nerId, 1);
        offset+=27*27*27*nerTagCount;


        //sixth: length: 15*nerTagCount
        feature.set(offset+Math.min(14, s.getWords().get(k).getWord().length())*nerTagCount+nerId, 1);
        offset+=15*nerTagCount;

        //seventh: contains how many capital: 4*nerTagCount
        String w=s.getWords().get(k).getWord();
        int cc=0;
        for(int i=0;i<Math.min(15, w.length()); i++)
            if(w.charAt(i)>='A' && w.charAt(i)<='Z')cc++;
        feature.set(offset+Math.min(3, cc)*nerTagCount+nerId, 1);
        offset+=nerTagCount*4;

        //contains symbols: nerTagCount
        for(int i=0;i<Math.min(15, w.length()); i++)
            if(!((w.charAt(i)>='A' && w.charAt(i)<='Z') || (w.charAt(i)>='a' && w.charAt(i)<='z') || (w.charAt(i)>='0' && w.charAt(i)<='9')))
                feature.set(offset+nerId, 1);
        offset+=nerTagCount;

        //contains number
        for(int i=0;i<Math.min(15, w.length()); i++)
            if(w.charAt(i)>='0' && w.charAt(i)<='9')
                feature.set(offset+nerId, 1);
        offset+=nerTagCount;

        return feature;
    }
}
