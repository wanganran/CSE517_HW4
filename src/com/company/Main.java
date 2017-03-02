package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Input train=new Input();
        List<Sentence> sentenceList=train.readFile(new File("/Users/wanganran/Downloads/conll03_ner/eng.train.small"));
        //System.out.println(omit.getNonZeros().size());
        FeatureVec w=Trainer.train(sentenceList, train.getWordStat());

        int wordCount=train.getWordStat().getCount();
        int posTagCount=POSTag.getTagCount();
        int scTagCount=SCTag.getTagCount();
        int nerTagCount=NERTag.getTagCount();
        System.out.println(wordCount+" "+posTagCount+" "+scTagCount+" "+nerTagCount);

        POSTag.setReadOnly(true);
        SCTag.setReadOnly(true);
        NERTag.setReadOnly(true);

        Input test=new Input();
        List<Sentence> testList=test.readFile(new File("/Users/wanganran/Downloads/conll03_ner/eng.dev.small"));
        List<Tag[]> tags=new ArrayList<>();

        wordCount=train.getWordStat().getCount();
        posTagCount=POSTag.getTagCount();
        scTagCount=SCTag.getTagCount();
        nerTagCount=NERTag.getTagCount();
        System.out.println(wordCount+" "+posTagCount+" "+scTagCount+" "+nerTagCount);

        for(int i=0;i<testList.size();i++){
            tags.add(Trainer.predict(testList.get(i), train.getWordStat(), w));
        }

        wordCount=train.getWordStat().getCount();
        posTagCount=POSTag.getTagCount();
        scTagCount=SCTag.getTagCount();
        nerTagCount=NERTag.getTagCount();
        System.out.println(wordCount+" "+posTagCount+" "+scTagCount+" "+nerTagCount);

        POSTag.setReadOnly(false);
        SCTag.setReadOnly(false);
        NERTag.setReadOnly(false);

        testList=test.readFile(new File("/Users/wanganran/Downloads/conll03_ner/eng.dev.small"));
        Output.writeFile(new File("/Users/wanganran/Downloads/conll03_ner/result.dev.small"), testList, tags);
    }
}
