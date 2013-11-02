
package at.ac.tuwien.infosys.aic13.sentiment;

import at.ac.tuwien.infosys.aic13.dto.SentimentQuery;
import at.ac.tuwien.infosys.aic13.dto.SentimentQueryResult;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public interface TwitterSentimentAnalyzer {
    public SentimentQueryResult analyze(SentimentQuery sentimentQuery);
}
