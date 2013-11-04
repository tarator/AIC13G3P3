package at.ac.tuwien.infosys.aic13.publicdto;

import java.io.Serializable;
import java.util.Date;

import at.ac.tuwien.infosys.aic13.dto.Company;


public class PublicCompany implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5575973613476200317L;
	
	private String name;
	private Date creationDate;
	
	public PublicCompany(){
		
	}
	
	public PublicCompany(Company company){
		this.setName(company.getName());
		this.setCreationDate(company.getCreationDate());
	
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
