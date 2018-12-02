package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import mindbadger.configuration.ApplicationContextProvider;
import mindbadger.football.domain.Division;
import mindbadger.football.domain.DomainObjectFactory;
import org.apache.log4j.Logger;

@JsonApiResource(type = "divisions")
public class CrnkDivision {
	private static Logger LOG = Logger.getLogger(CrnkDivision.class);

	private Division division;

	private DomainObjectFactory domainObjectFactory;

	public CrnkDivision (Division division) {
		this.division = division;
	}

	public CrnkDivision () {
		this.domainObjectFactory = (DomainObjectFactory)
				ApplicationContextProvider.getApplicationContext().getBean("domainObjectFactory");
		division = domainObjectFactory.createDivision();
	}

	@JsonProperty("divisionName")	
	public String getDivisionName() {
		return division.getDivisionName();
	}
	public void setDivisionName(String divisionName) {
		division.setDivisionName(divisionName);
	}
	
	@JsonApiId
	public String getId() {
		if (division == null) return null;
		return division.getDivisionId();
	}
	public void setId(String id) {
		division.setDivisionId(id);
	}

	//TODO Even though we don't want to expose this directly, Crnk requires this to work!!!
	@JsonIgnore
	public Division getDivision() {	return division; }

	//TODO Even though we don't want to expose this directly, Crnk requires this to work!!
	@JsonIgnore
	public void setDivision(Division division) {	this.division = division;	}
}
