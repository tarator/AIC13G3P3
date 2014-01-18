/*
   Copyright 2013 Philipp Leitner

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   Modified by Roman Khassraf <roman at khassraf.at>
*/
package at.ac.tuwien.infosys.aic13.sentiment.impl;

import at.ac.tuwien.infosys.aic13.sentiment.TweetClassifier;
import at.ac.tuwien.infosys.aic13.sentiment.SentimentType;
import java.util.LinkedList;
import java.util.List;
import classifier.ClassifierBuilder;
import classifier.IClassifier;
import classifier.WeightedMajority;
import classifier.WekaClassifier;

public class ClassifierImpl implements TweetClassifier
{
        private final WeightedMajority classifier;
       
        public ClassifierImpl() throws Exception
        {
            List<IClassifier> classifiers = new LinkedList<>();
            ClassifierBuilder cb = new ClassifierBuilder ();
            WekaClassifier wc1 = cb.retrieveClassifier  ("weka.classifiers.bayes.NaiveBayes");
            WekaClassifier wc2 = cb.retrieveClassifier  ("weka.classifiers.trees.J48");
            WekaClassifier wc3 = cb.retrieveClassifier  ("weka.classifiers.functions.VotedPerceptron");
            
            classifiers.add (wc1);
            classifiers.add (wc2);
            classifiers.add (wc3);
            classifier = new WeightedMajority (classifiers);
        }
       
        @Override
        public SentimentType classify(String text)
        {
            try
            {
                String polarity = classifier.weightedClassify(text).getPolarity();
                if(polarity.equals("4"))
                    return SentimentType.Positive;

                if(polarity.equals("0"))
                    return SentimentType.Negative;

                return SentimentType.Neutral;
            }
            catch(Exception ex)
            {
                return SentimentType.Neutral;
            }
        }
       
}
