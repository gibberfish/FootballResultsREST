package mindbadger.football.api.model;


import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import mindbadger.configuration.ApplicationContextProvider;
import mindbadger.football.domain.DivisionMapping;
import mindbadger.football.domain.DomainObjectFactory;
import org.apache.log4j.Logger;

@JsonApiResource(type = "division_mapping")
public class CrnkDivisionMapping {
    private static Logger LOG = Logger.getLogger(CrnkDivisionMapping.class);

    private DivisionMapping divisionMapping;

    private DomainObjectFactory domainObjectFactory;

    private String id;
//    private String dialect;

    public CrnkDivisionMapping () {
        this.domainObjectFactory = (DomainObjectFactory)
                ApplicationContextProvider.getApplicationContext().getBean("domainObjectFactory");
        this.divisionMapping = domainObjectFactory.createDivisionMapping();
        createId();
    }

    public CrnkDivisionMapping (DivisionMapping divisionMapping) {
        this.divisionMapping = divisionMapping;
        createId();
    }

    @JsonApiId
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("dialect")
    public String getDialect() {
        return divisionMapping.getDialect();
    }

    public void setDialect(String dialect) {
        divisionMapping.setDialect(dialect);
        createId();
    }

    @JsonProperty("sourceId")
    public Integer getSourceId() {
        return divisionMapping.getSourceId();
    }

    public void setSourceId(Integer sourceId) {
        this.divisionMapping.setSourceId(sourceId);
        createId();
    }

    @JsonProperty("fraId")
    public Integer getFraId() {
        return divisionMapping.getFraId();
    }

    public void setFraId(Integer fraId) {
        this.divisionMapping.setFraId(fraId);
        createId();
    }

    @JsonIgnore
    public DivisionMapping getDivisionMapping() {
        return divisionMapping;
    }

    @JsonIgnore
    public void setDivisionMapping(DivisionMapping divisionMapping) {
        this.divisionMapping = divisionMapping;
        createId();
    }

    private void createId () {
        this.id = divisionMapping.getDialect() + "_" +
                divisionMapping.getSourceId() + "_" + divisionMapping.getFraId();
    }
}
