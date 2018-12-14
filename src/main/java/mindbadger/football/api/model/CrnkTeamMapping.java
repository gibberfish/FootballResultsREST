package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import mindbadger.configuration.ApplicationContextProvider;
import mindbadger.football.domain.DomainObjectFactory;
import mindbadger.football.domain.TeamMapping;
import org.apache.log4j.Logger;

@JsonApiResource(type = "team_mapping")
public class CrnkTeamMapping {
    private static Logger LOG = Logger.getLogger(CrnkTeamMapping.class);

    private TeamMapping teamMapping;

    private DomainObjectFactory domainObjectFactory;

    private String id;

    public CrnkTeamMapping () {
        this.domainObjectFactory = (DomainObjectFactory)
                ApplicationContextProvider.getApplicationContext().getBean("domainObjectFactory");
        this.teamMapping = domainObjectFactory.createTeamMapping();
        createId();
    }

    public CrnkTeamMapping (TeamMapping teamMapping) {
        this.teamMapping = teamMapping;
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
        return teamMapping.getDialect();
    }

    public void setDialect(String dialect) {
        teamMapping.setDialect(dialect);
        createId();
    }

    @JsonProperty("sourceId")
    public Integer getSourceId() {
        return teamMapping.getSourceId();
    }

    public void setSourceId(Integer sourceId) {
        this.teamMapping.setSourceId(sourceId);
        createId();
    }

    @JsonProperty("fraId")
    public Integer getFraId() {
        return teamMapping.getFraId();
    }

    public void setFraId(Integer fraId) {
        this.teamMapping.setFraId(fraId);
        createId();
    }

    @JsonIgnore
    public TeamMapping getTeamMapping() {
        return teamMapping;
    }

    @JsonIgnore
    public void setTeamMapping(TeamMapping teamMapping) {
        this.teamMapping = teamMapping;
        createId();
    }

    private void createId () {
        this.id = teamMapping.getDialect() + "_" +
                teamMapping.getSourceId() + "_" + teamMapping.getFraId();
    }
}
