
package at.ac.tuwien.infosys.aic13.sentiment;

import java.util.Date;
import java.util.List;
import twitter4j.Status;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public interface TwitterQuery {
    public List<Status> getTweets(String query, Date from, Date until);
}
