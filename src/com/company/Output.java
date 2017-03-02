package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by wanganran on 3/1/17.
 */
public class Output {
    public static void writeFile(File f, List<Sentence> sentences, List<Tag[]> tags) throws IOException{
        FileWriter writer=new FileWriter(f);
        for(int i=0;i<sentences.size();i++){
            Sentence s=sentences.get(i);
            for(int j=0;j<s.getWords().size()-1;j++) {
                Word w = s.getWords().get(j);
                writer.write(w.getWord() + " " + w.getPosTag().toString() + " " + w.getSCTag().toString() + " " + w.getNerTag().toString() + " " + tags.get(i)[j].toString()+"\n");
            }
            writer.write("\n");
        }
        writer.close();
    }
}
