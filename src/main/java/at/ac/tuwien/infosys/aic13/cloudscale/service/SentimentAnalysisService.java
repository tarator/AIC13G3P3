package at.ac.tuwien.infosys.aic13.cloudscale.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.Date;

import at.ac.tuwien.infosys.aic13.publicdto.PublicCompany;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQuery;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQueryResult;

public abstract class SentimentAnalysisService implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(SentimentAnalysisService.class);

	private  Connection connection;
	private boolean stopService = false;
	private static final Object connectionLock = new Object();
	
	
	/**
	 * This method starts the CloudWorker and sets the result after the worker is done.
	 * May have a long runtime! THe query should be marked as processed before.
	 * If an error occurs while running the Sentiment analysis and no SentimentQueryResult can be created, the query must be marked as unprocessed.
	 * An optimal implementation should start the work in a thread here.
	 */
	public abstract void runSentimentAnalysis();
	
	public void stopService() {
		this.stopService = true;
	}
	
	private Connection getConnection(){
		if(connection == null){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				 // Setup the connection with the DB
				connection = DriverManager.getConnection("jdbc:mysql://localhost/AIC13G3P3?user=AIC13G3P3&password=AIC13G3P3");
			} catch (ClassNotFoundException | SQLException e) {
				logger.error("Error while creating database connection (QQG5K5).", e);
			}
		}
		return connection;
	}
	
	/**
	 * This method reads the next unprocessed query from the database and marks it as processed.
	 * @return
	 */
	protected PublicSentimentQuery getNextQuery(){
		PublicSentimentQuery qry = null;
		synchronized(connectionLock){
			try {
				Statement stmt = getConnection().createStatement();
				ResultSet res = stmt.executeQuery("select s.id as id, s.dateFrom as dateFrom, s.dateTo as dateTo, c.name as name from sentimentQuery AS s, company as c where s.processed = 0 and s.company = c.id limit 0,1");
				if(res.next()){
					qry = new PublicSentimentQuery();
					Date dateFrom = res.getDate("dateFrom");
					Date dateTo = res.getDate("dateTo");
					
					PublicCompany company = new PublicCompany();
					company.setName(res.getString("name"));
					qry.setCompany(company);
					qry.setFrom(dateFrom);
					qry.setTo(dateTo);
					qry.setQueryId(res.getLong("id"));
				}
				res.close();
				stmt.close();
				
				//Mark as processed
				if(qry != null && qry.getQueryId() != null){
					PreparedStatement pstmt = connection.prepareStatement("update sentimentQuery SET processed=? where id = ?");
					pstmt.setBoolean(1, true);
					pstmt.setLong(2, qry.getQueryId());
					pstmt.executeUpdate();
					pstmt.close();
					logger.info("Query \""+qry+"\" marked as processed.");
				}
			} catch (SQLException e) {
				logger.error("Error while getting SentimentQuery or marking it as 'processed'.", e);
			}
		}// END of synchronized-block
		return qry;
	}
	
	/**
	 * This method writes the result back to the database.
	 * @param result
	 */
	protected void writeResult(PublicSentimentQueryResult result){
		synchronized(connectionLock){
			
			try {
				PreparedStatement pstmt = connection.prepareStatement("insert into sentimentQueryResult values (?,?,?)");
				pstmt.setLong(1, result.getQueryId());
				pstmt.setInt(2, result.getNumberOfTweets());
				pstmt.setDouble(3, result.getSentimentValue());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				logger.error("Error while writing SentimentQueryResult (77K1PG).", e);
			}
		}// END of synchronized-block
		logger.info("SentimentQueryResult written to DB (7ZS89Q): "+result);
	}
	
	@Override
	public void run() {
//		long delay = Long.valueOf(g3p3Properties.getProperty("g3p3.sentimentnalysis.delayms", "250"));
		long delay = 2000;
		while(!stopService){
			try{
				runSentimentAnalysis();
			}catch(Exception e){
				logger.error("Error while performing dummy analysis. (RPZWSU)", e);
			}
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				logger.warn("Thread.sleep interrupted. This may be ignored (0MPTFS).", e);
			}
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
