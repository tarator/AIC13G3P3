package at.ac.tuwien.infosys.aic13.sentiment.impl;

import at.ac.tuwien.infosys.aic13.model.SentimentQuery;
import at.ac.tuwien.infosys.aic13.model.SentimentQueryResult;
import at.ac.tuwien.infosys.aic13.sentiment.TweetClassifier;
import at.ac.tuwien.infosys.aic13.sentiment.TwitterQuery;
import at.ac.tuwien.infosys.aic13.sentiment.TwitterSentimentAnalyzer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public class TwitterSentimentAnalyzerImpl implements TwitterSentimentAnalyzer {

    private static final TwitterQuery twitterQuery = new WSTwitterQueryImpl();

    @Override
    public SentimentQuery analyze(SentimentQuery sentimentQuery) {
        
        SentimentQueryResult result = new SentimentQueryResult();
        
        List<Status> stati = twitterQuery.getTweets(
                sentimentQuery.getCompany().getName(), 
                sentimentQuery.getFrom(), 
                sentimentQuery.getTo());
        
        if (stati.isEmpty()) {
            Logger.getLogger(TwitterSentimentAnalyzerImpl.class.getName())
                    .log(Level.INFO, String.format("Twitter returned empty list of tweets"));
            result.setSentimentValue(0.5);
            result.setNumberOfTweets(0);
            sentimentQuery.setProcessed(true);
            sentimentQuery.setResult(result);
            return sentimentQuery;
        }
        
        TweetClassifier classifier = null;
        try {
            classifier = new ClassifierImpl();
        } catch (Exception ex) {
            Logger.getLogger(TwitterSentimentAnalyzerImpl.class.getName())
                    .log(Level.SEVERE, "Failed to initialize Classifier.", ex);
            result.setSentimentValue(0.5);
            result.setNumberOfTweets(0);
            sentimentQuery.setProcessed(true);
            sentimentQuery.setResult(result);
            return sentimentQuery;
        }
        
        int positive = 0, neutral = 0, negative = 0;
        
        for (Status status : stati) {
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
                    Logger.getLogger(TwitterSentimentAnalyzerImpl.class.getName())
                    .log(Level.SEVERE, "Unexpected behavior: default case in switch.");
                    break;
            }
        }
        
        result.setNumberOfTweets(stati.size());
        double sr = (positive + (0.5 * neutral)) / (positive + neutral + negative);
        result.setSentimentValue( Math.round(sr * 100.0) / 100.0 );
        
        sentimentQuery.setProcessed(true);
        sentimentQuery.setResult(result);
        Logger.getLogger(TwitterSentimentAnalyzerImpl.class.getName())
                    .log(Level.INFO, String.format("Found Tweets: pos=%s, neutral=%s, negative=%s", positive, neutral, negative));

        return sentimentQuery;
    }   
}
