
package at.ac.tuwien.infosys.aic13.sentiment.impl;

import at.ac.tuwien.infosys.aic13.sentiment.TwitterQuery;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public class WSTwitterQueryImpl implements TwitterQuery {
    
    private static final TwitterFactory factory = new TwitterFactory();
    private static final int TWITTER_PAGES = 5;

    @Override
    public List<Status> getTweets(String query, Date from, Date until) {
        
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        String sfrom = sdf.format(from);
        String suntil = sdf.format(until);
        
        List<Status> list = new ArrayList<>();
        
        Twitter twitter = factory.getInstance();
        long maxId = Long.MAX_VALUE;
        
        Query q = new Query(query);
        q.setSince(sfrom);
        q.setUntil(suntil);
        q.setCount(100);
        q.setMaxId(maxId);
        
        int counter = TWITTER_PAGES;
        try {
            QueryResult result = twitter.search(q);
            
            do {
                List<Status> tweets = result.getTweets();
                for(Status tweet : tweets) {
                    list.add(tweet);
                    if (tweet.getId() < maxId) {
                        maxId = tweet.getId();
                    }
                }
                q.setMaxId(maxId);
                result = twitter.search(q);
                counter--;
            } while (result.getTweets().size() > 0 && counter > 0);
            
           
            return list;
        } catch (TwitterException ex) {
            Logger.getLogger(WSTwitterQueryImpl.class.getName()).log(Level.SEVERE, null, ex);
            return list;
        }
    }
}
