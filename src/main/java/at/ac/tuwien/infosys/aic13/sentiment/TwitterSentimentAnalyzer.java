
package at.ac.tuwien.infosys.aic13.sentiment;

import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQuery;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQueryResult;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public interface TwitterSentimentAnalyzer {
    public PublicSentimentQueryResult analyze(PublicSentimentQuery sentimentQuery);
}
