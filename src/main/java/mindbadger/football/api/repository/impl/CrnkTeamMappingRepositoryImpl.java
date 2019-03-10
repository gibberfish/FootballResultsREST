package mindbadger.football.api.repository.impl;

import io.crnk.core.exception.BadRequestException;
import io.crnk.core.exception.ResourceNotFoundException;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkTeamMapping;
import mindbadger.football.api.repository.CrnkTeamMappingRepository;
import mindbadger.football.domain.TeamMapping;
import mindbadger.football.domain.MappingId;
import mindbadger.football.repository.TeamMappingRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrnkTeamMappingRepositoryImpl extends ResourceRepositoryBase<CrnkTeamMapping, String>
        implements CrnkTeamMappingRepository {
    private static Logger LOG = Logger.getLogger(CrnkTeamMappingRepositoryImpl.class);

    @Autowired
    private TeamMappingRepository teamMappingRepository;

    protected CrnkTeamMappingRepositoryImpl() {super(CrnkTeamMapping.class);}
    protected CrnkTeamMappingRepositoryImpl(Class<CrnkTeamMapping> resourceClass) {
        super(resourceClass);
    }

    @Override
    public ResourceList<CrnkTeamMapping> findAll(QuerySpec querySpec) {
        List<? extends TeamMapping> teamMappings = teamMappingRepository.findAll();
        List<CrnkTeamMapping> crnkTeamMappings = new ArrayList<>();
        teamMappings.forEach((teamMapping -> {
            crnkTeamMappings.add(new CrnkTeamMapping(teamMapping));
        }));

        return querySpec.apply(crnkTeamMappings);
    }

    @Override
    public CrnkTeamMapping findOne(String id, QuerySpec querySpec) {
        String[] split = id.split("_");
        if (split.length != 3) {
            throw new ResourceNotFoundException("Team does not exist");
        }

        MappingId teamMappingId = new MappingId();
        teamMappingId.setDialect(split[0]);
        teamMappingId.setSourceId(Integer.parseInt(split[1]));
        teamMappingId.setFraId(Integer.parseInt(split[2]));

        TeamMapping teamMapping = teamMappingRepository.findOne(teamMappingId);

        if (teamMapping == null) {
            throw new ResourceNotFoundException("Team Mapping does not exist");
        }

        return new CrnkTeamMapping(teamMapping);
    }

    @Override
    public <S extends CrnkTeamMapping> S save(S resource) {
        TeamMapping teamMapping = teamMappingRepository.save(resource.getTeamMapping());
        return (S) new CrnkTeamMapping(teamMapping);
    }

    @Override
    public <S extends CrnkTeamMapping> S create(S resource) {
        MappingId teamMappingId = new MappingId();
        teamMappingId.setDialect(resource.getDialect());
        teamMappingId.setSourceId(resource.getSourceId());
        teamMappingId.setFraId(resource.getFraId());

        TeamMapping teamMapping = teamMappingRepository.findOne(teamMappingId);
        if (teamMapping != null) {
            throw new BadRequestException("Team Mapping already exists");
        }

        return save(resource);
    }

    @Override
    public void delete(String id) {
        String[] split = id.split("_");
        if (split.length != 3) {
            throw new ResourceNotFoundException("Team does not exist");
        }

        MappingId teamMappingId = new MappingId();
        teamMappingId.setDialect(split[0]);
        teamMappingId.setSourceId(Integer.parseInt(split[1]));
        teamMappingId.setFraId(Integer.parseInt(split[2]));

        TeamMapping teamMapping = teamMappingRepository.findOne(teamMappingId);

        if (teamMapping == null) {
            throw new ResourceNotFoundException("Team Mapping does not exist");
        }

        teamMappingRepository.delete(teamMapping);
    }
}
