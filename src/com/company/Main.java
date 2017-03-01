package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Input train=new Input();
        List<Sentence> sentenceList=train.readFile(new File("/Users/wanganran/Downloads/conll03_ner/eng.dev.small"));
        FeatureVec w=Trainer.train(sentenceList, train.getWordStat());

        Input test=new Input();
        List<Sentence> testList=test.readFile(new File("/Users/wanganran/Downloads/conll03_ner/eng.test.small"));
        List<Tag[]> tags=new ArrayList<>();
        for(int i=0;i<testList.size();i++){
            tags.add(Trainer.predict(testList.get(i), train.getWordStat()));
        }
        Output.writeFile(new File("/Users/wanganran/Downloads/conll03_ner/result.test.small"), testList, tags);
    }
}
