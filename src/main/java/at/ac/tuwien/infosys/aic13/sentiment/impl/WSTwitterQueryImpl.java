
package at.ac.tuwien.infosys.aic13.sentiment.impl;

import at.ac.tuwien.infosys.aic13.sentiment.TwitterQuery;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.LoggerFactory;
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
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WSTwitterQueryImpl.class);
    private static final TwitterFactory factory = new TwitterFactory();

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
        
        boolean run = true;
        try {
            QueryResult result = twitter.search(q);
            
            do {
                list.addAll(result.getTweets());
                
                for (Status s : result.getTweets()) {
                    if (s.getId() < maxId) {
                        maxId = s.getId();
                    }
                }
                
                if (result.getTweets().size() == 0) {
                    run = false;
                }
                
                q.setMaxId(maxId);
                result = twitter.search(q);
            } while (run);
            

            return list;
        } catch (TwitterException ex) {
            logger.error("Failed to retrieve tweets.", ex);
            return new ArrayList<>();
        }
    }
}
