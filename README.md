### README ###

Author: Anran Wang 1623602

## Description ##

The code is written in Java and the project can be imported into IntellijIdea. It should be successfully compiled by Java 1.8 (I think Java 1.7 is also OK) and run all the experiments for both the two programming assignments and output the results.
If you have IntellijIdea installed, just import this directory and you'll be fine. Otherwise, you can run "java com.company.Main" under out/production/CSE517_HW4 directory and it should also run perfectly.

## Source Code Files ##

There are several classes in this project. I will briefly introduce them in this section.

* Main

This class is the entrance of the program and will call a sequence of experiments including the training and testing.

* Input

This is the class that reads the file and parse the file to a list of arrays of Words.

* FeatureFactory

This is the class which has the function Phi to generate feature vectors given x, i and two NER tags.
 
* FeatureVec
 
This is the data structure of a feature vector, using sparse representation.

* HMM

This is the class that implements Viterbi algorithm.

* Trainer

This is the class that implements the actual recursive training process and also the predicting procedure.

* Word, WordStat

These are the classes that represent an actual word including its tags, and some statistics of the words.

* Sentence

List of Words.

* NERTag, SCTag, POSTag, Tag, TagBase

Data structures of different tags.

## Potential Modifications of Parameters ##

Many (hyper)parameters can be changed in the source code.

* Dataset path: in the Main class, change the strings of path.

* Feature templates: in the FeatureFactory class, add or remove the feature definitions in getFeatures method.

* Iteration number: in the Trainer class, change the variable T.

