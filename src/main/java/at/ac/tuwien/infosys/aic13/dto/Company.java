package at.ac.tuwien.infosys.aic13.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import at.ac.tuwien.infosys.aic13.dao.DTO;


@Entity
@Table(name="company")
public class Company implements DTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7917510612718054608L;
	
	@Id
    @Column(name="id")
    @GeneratedValue
    private Long id;
	
	@Column(name="name")
    private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public Company(){
		
	}
	
	
	

}
