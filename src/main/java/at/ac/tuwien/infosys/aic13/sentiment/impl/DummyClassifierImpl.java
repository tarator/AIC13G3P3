
package at.ac.tuwien.infosys.aic13.sentiment.impl;

import at.ac.tuwien.infosys.aic13.sentiment.Classifier;
import at.ac.tuwien.infosys.aic13.sentiment.SentimentType;
import java.util.Random;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public class DummyClassifierImpl implements Classifier {

    @Override
    public SentimentType classify(String text) {
        int random = new Random().nextInt(3);
        switch (random) {
            case 0:
                return SentimentType.Positive;
            case 1:
                return SentimentType.Neutral;
            case 2:
                return SentimentType.Negative;
            default:
                return SentimentType.Neutral;
        }
    }
}
