
package at.ac.tuwien.infosys.aic13.sentiment;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public interface Classifier {
    public SentimentType classify(String text);
}
