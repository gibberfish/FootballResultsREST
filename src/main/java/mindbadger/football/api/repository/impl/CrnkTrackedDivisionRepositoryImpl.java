package mindbadger.football.api.repository.impl;

import io.crnk.core.exception.BadRequestException;
import io.crnk.core.exception.ResourceNotFoundException;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkTrackedDivision;
import mindbadger.football.api.repository.CrnkTrackedDivisionRepository;
import mindbadger.football.domain.TrackedDivisionId;
import mindbadger.football.domain.TrackedDivision;
import mindbadger.football.repository.TrackedDivisionRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrnkTrackedDivisionRepositoryImpl extends ResourceRepositoryBase<CrnkTrackedDivision, String>
        implements CrnkTrackedDivisionRepository {
    private static Logger LOG = Logger.getLogger(CrnkTrackedDivisionRepositoryImpl.class);

    @Autowired
    private TrackedDivisionRepository trackedDivisionRepository;

    protected CrnkTrackedDivisionRepositoryImpl() {super(CrnkTrackedDivision.class);}
    protected CrnkTrackedDivisionRepositoryImpl(Class<CrnkTrackedDivision> resourceClass) {
        super(resourceClass);
    }

    @Override
    public ResourceList<CrnkTrackedDivision> findAll(QuerySpec querySpec) {
        Iterable<TrackedDivision> trackedDivisions = trackedDivisionRepository.findAll();
        List<CrnkTrackedDivision> crnkTrackedDivisions = new ArrayList<>();
        trackedDivisions.forEach((trackedDivision -> {
            crnkTrackedDivisions.add(new CrnkTrackedDivision(trackedDivision));
        }));

        return querySpec.apply(crnkTrackedDivisions);
    }

    @Override
    public CrnkTrackedDivision findOne(String id, QuerySpec querySpec) {
        String[] split = id.split("_");
        if (split.length != 2) {
            throw new ResourceNotFoundException("Tracked Division does not exist");
        }

        TrackedDivisionId trackedDivisionId = new TrackedDivisionId();
        trackedDivisionId.setDialect(split[0]);
        trackedDivisionId.setSourceId(Integer.parseInt(split[1]));

        TrackedDivision trackedDivision = trackedDivisionRepository.findOne(trackedDivisionId);

        if (trackedDivision == null) {
            throw new ResourceNotFoundException("Tracked Division does not exist");
        }

        return new CrnkTrackedDivision(trackedDivision);
    }

    @Override
    public <S extends CrnkTrackedDivision> S save(S resource) {
        TrackedDivision trackedDivision = trackedDivisionRepository.save(resource.getTrackedDivision());
        return (S) new CrnkTrackedDivision(trackedDivision);
    }

    @Override
    public <S extends CrnkTrackedDivision> S create(S resource) {
        TrackedDivisionId trackedDivisionId = new TrackedDivisionId();
        trackedDivisionId.setDialect(resource.getDialect());
        trackedDivisionId.setSourceId(resource.getSourceId());

        TrackedDivision trackedDivision = trackedDivisionRepository.findOne(trackedDivisionId);
        if (trackedDivision != null) {
            throw new BadRequestException("Tracked Division already exists");
        }

        return save(resource);
    }

    @Override
    public void delete(String id) {
        String[] split = id.split("_");
        if (split.length != 2) {
            throw new ResourceNotFoundException("Tracked Division does not exist");
        }

        TrackedDivisionId trackedDivisionId = new TrackedDivisionId();
        trackedDivisionId.setDialect(split[0]);
        trackedDivisionId.setSourceId(Integer.parseInt(split[1]));

        TrackedDivision trackedDivision = trackedDivisionRepository.findOne(trackedDivisionId);

        if (trackedDivision == null) {
            throw new ResourceNotFoundException("Tracked Division does not exist");
        }

        trackedDivisionRepository.delete(trackedDivision);
    }
}
