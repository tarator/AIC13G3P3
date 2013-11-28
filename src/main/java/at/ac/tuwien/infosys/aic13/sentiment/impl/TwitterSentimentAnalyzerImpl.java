
package at.ac.tuwien.infosys.aic13.sentiment.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQuery;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQueryResult;
import at.ac.tuwien.infosys.aic13.sentiment.Classifier;
import at.ac.tuwien.infosys.aic13.sentiment.TwitterQuery;
import at.ac.tuwien.infosys.aic13.sentiment.TwitterSentimentAnalyzer;
import at.ac.tuwien.infosys.aic13.sentiment.WEKAFileProvider;
import at.ac.tuwien.infosys.cloudscale.annotations.ByValueParameter;
import at.ac.tuwien.infosys.cloudscale.annotations.CloudObject;
import at.ac.tuwien.infosys.cloudscale.annotations.DestructCloudObject;
import at.ac.tuwien.infosys.cloudscale.annotations.FileDependency;
import at.ac.tuwien.infosys.cloudscale.annotations.FileDependency.FileAccess;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
@CloudObject
@FileDependency(dependencyProvider = WEKAFileProvider.class, accessType = FileAccess.ReadOnly)
public class TwitterSentimentAnalyzerImpl implements TwitterSentimentAnalyzer {

    private static final TwitterQuery twitterQuery = new WSTwitterQueryImpl();
    private static final Logger logger = LoggerFactory.getLogger(TwitterSentimentAnalyzerImpl.class);
    
    @Override
    @DestructCloudObject
    public PublicSentimentQueryResult analyze(@ByValueParameter PublicSentimentQuery sentimentQuery) {
        logger.debug("Analyzing query.");
        
        PublicSentimentQueryResult result = new PublicSentimentQueryResult();

        Classifier classifier = null;
        int positive = 0, neutral = 0, negative = 0;
        
        try {
            classifier = new ClassifierImpl();
        } catch (Exception ex) {
            logger.error("Failed to initialize Classifier.", ex);
            return result;
        }

        logger.debug("Send twitter query for company {}.", sentimentQuery.getCompany().getName().toString());
        List<Status> tweets = twitterQuery.getTweets(
                sentimentQuery.getCompany().getName().toString(),
                sentimentQuery.getFrom(),
                sentimentQuery.getTo());
        logger.debug("Received list of {} tweets.", tweets.size());
        result.setNumberOfTweets(tweets.size());

        if (result.getNumberOfTweets() == 0) {
            result.setSentimentValue(0.5);
            return result;
            
        }
        
        for (Status status : tweets) {
            switch (classifier.classify(status.getText())) {
                case Positive:
                    positive++;
                    break;
                case Neutral:
                    neutral++;
                    break;
                case Negative:
                    negative++;
                    break;
                default:
                    //TODO Logging
                    break;
            }
        }
        logger.info("Found Tweets: pos="+positive+", neutral="+neutral+", negative="+negative );
        double sr = (positive + (0.5 * neutral)) / (positive + neutral + negative);
        result.setSentimentValue( Math.round(sr * 100.0) / 100.0 );
        return result;
    }
    
}
