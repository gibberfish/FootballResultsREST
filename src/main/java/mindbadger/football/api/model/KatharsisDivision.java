package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import mindbadger.football.domain.Division;

@JsonApiResource(type = "divisions")
public class KatharsisDivision {
	
	private Division division;
	
	public KatharsisDivision (Division division) {
		this.division = division;
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
		return division.getDivisionId();
	}
	public void setId(String id) {
		division.setDivisionId(id);
	}

	//TODO Even though we don't want to expose this directly, Katharsis requires this to work!!!
	@JsonIgnore
	public Division getDivision() {	return division; }

	//TODO Even though we don't want to expose this directly, Katharsis requires this to work!!
	@JsonIgnore
	public void setDivision(Division division) {	this.division = division;	}
}
