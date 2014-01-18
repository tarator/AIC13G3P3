
package at.ac.tuwien.infosys.aic13.sentiment;

import at.ac.tuwien.infosys.aic13.model.SentimentQuery;


/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public interface TwitterSentimentAnalyzer {
    public SentimentQuery analyze(SentimentQuery sentimentQuery);
}
