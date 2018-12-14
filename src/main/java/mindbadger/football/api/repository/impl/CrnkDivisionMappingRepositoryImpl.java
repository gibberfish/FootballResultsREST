package mindbadger.football.api.repository.impl;

import io.crnk.core.exception.BadRequestException;
import io.crnk.core.exception.ResourceNotFoundException;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkDivisionMapping;
import mindbadger.football.api.repository.CrnkDivisionMappingRepository;
import mindbadger.football.domain.DivisionMapping;
import mindbadger.football.domain.MappingId;
import mindbadger.football.repository.DivisionMappingRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrnkDivisionMappingRepositoryImpl extends ResourceRepositoryBase<CrnkDivisionMapping, String>
        implements CrnkDivisionMappingRepository {
    private static Logger LOG = Logger.getLogger(CrnkDivisionMappingRepositoryImpl.class);

    @Autowired
    private DivisionMappingRepository divisionMappingRepository;

    protected CrnkDivisionMappingRepositoryImpl() {super(CrnkDivisionMapping.class);}
    protected CrnkDivisionMappingRepositoryImpl(Class<CrnkDivisionMapping> resourceClass) {
        super(resourceClass);
    }

    @Override
    public ResourceList<CrnkDivisionMapping> findAll(QuerySpec querySpec) {
        Iterable<DivisionMapping> divisionMappings = divisionMappingRepository.findAll();
        List<CrnkDivisionMapping> crnkDivisionMappings = new ArrayList<>();
        divisionMappings.forEach((divisionMapping -> {
            crnkDivisionMappings.add(new CrnkDivisionMapping(divisionMapping));
        }));

        return querySpec.apply(crnkDivisionMappings);
    }

    @Override
    public CrnkDivisionMapping findOne(String id, QuerySpec querySpec) {
        String[] split = id.split("_");
        if (split.length != 3) {
            throw new ResourceNotFoundException("Division does not exist");
        }

        MappingId divisionMappingId = new MappingId();
        divisionMappingId.setDialect(split[0]);
        divisionMappingId.setSourceId(Integer.parseInt(split[1]));
        divisionMappingId.setFraId(Integer.parseInt(split[2]));

        DivisionMapping divisionMapping = divisionMappingRepository.findOne(divisionMappingId);

        if (divisionMapping == null) {
            throw new ResourceNotFoundException("Division Mapping does not exist");
        }

        return new CrnkDivisionMapping(divisionMapping);
    }

    @Override
    public <S extends CrnkDivisionMapping> S save(S resource) {
        DivisionMapping divisionMapping = divisionMappingRepository.save(resource.getDivisionMapping());
        return (S) new CrnkDivisionMapping(divisionMapping);
    }

    @Override
    public <S extends CrnkDivisionMapping> S create(S resource) {
        MappingId divisionMappingId = new MappingId();
        divisionMappingId.setDialect(resource.getDialect());
        divisionMappingId.setSourceId(resource.getSourceId());
        divisionMappingId.setFraId(resource.getFraId());

        DivisionMapping divisionMapping = divisionMappingRepository.findOne(divisionMappingId);
        if (divisionMapping != null) {
            throw new BadRequestException("Division Mapping already exists");
        }

        //resource.setId(divisionMappingId);
        return save(resource);
    }

    @Override
    public void delete(String id) {
        String[] split = id.split("_");
        if (split.length != 3) {
            throw new ResourceNotFoundException("Division does not exist");
        }

        MappingId divisionMappingId = new MappingId();
        divisionMappingId.setDialect(split[0]);
        divisionMappingId.setSourceId(Integer.parseInt(split[1]));
        divisionMappingId.setFraId(Integer.parseInt(split[2]));

        DivisionMapping divisionMapping = divisionMappingRepository.findOne(divisionMappingId);

        if (divisionMapping == null) {
            throw new ResourceNotFoundException("Division Mapping does not exist");
        }

        divisionMappingRepository.delete(divisionMapping);
    }
}
