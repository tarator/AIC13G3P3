package at.ac.tuwien.infosys.aic13;

import java.util.Date;

import at.ac.tuwien.infosys.aic13.cloudscale.workers.DummyWorker;
import at.ac.tuwien.infosys.aic13.publicdto.PublicCompany;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQuery;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQueryResult;

public class Main {

	public static void main(String[] args){
		PublicSentimentQuery query = new PublicSentimentQuery();
		PublicCompany company = new PublicCompany();
		company.setName("BMW");
		company.setCreationDate(new Date());
		query.setCompany(company);
		
		DummyWorker x = new DummyWorker();
		PublicSentimentQueryResult result = x.doTheAnalysisStuff(query);
		System.out.println(result.getNumberOfTweets());
		System.out.println(result.getSentimentValue());
	}
}
