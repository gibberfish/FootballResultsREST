package mindbadger.football.api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import io.crnk.core.engine.registry.ResourceRegistry;
import io.crnk.core.exception.BadRequestException;
import mindbadger.football.api.repository.CrnkSeasonRepository;
import mindbadger.football.domain.DomainObjectFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkSeason;
import mindbadger.football.domain.Season;
import mindbadger.football.repository.SeasonRepository;

@Component
public class CrnkSeasonRepositoryImpl extends ResourceRepositoryBase<CrnkSeason, Integer> 
	implements CrnkSeasonRepository {

	Logger LOG = Logger.getLogger (CrnkSeasonRepositoryImpl.class);

	protected CrnkSeasonRepositoryImpl() {
		super(CrnkSeason.class);
	}

	@Autowired
	private DomainObjectFactory domainObjectFactory;

	@Autowired
	private SeasonRepository seasonRepository;

	@Override
	public synchronized ResourceList<CrnkSeason> findAll(QuerySpec querySpec) {
		LOG.debug("*********************** CrnkSeasonRepositoryImpl.findAll");

		Iterable<Season> seasons = seasonRepository.findAll();
		
    	List<CrnkSeason> katharsisSeasons = new ArrayList<CrnkSeason> ();
    	for (Season season : seasons) {
    		katharsisSeasons.add(new CrnkSeason(season));
    	}
		
		return querySpec.apply(katharsisSeasons);
	}

	@Override
	public <S extends CrnkSeason> S save(S resource) {
		LOG.debug("*********************** CrnkSeasonRepositoryImpl.save");

		//Season newSeason = domainObjectFactory.createSeason(resource.getId());
		//seasonRepository.save(newSeason);

		seasonRepository.save(resource.getSeason());

		return resource;
	}

	@Override
	public <S extends CrnkSeason> S create(S resource) {
		LOG.debug("*********************** CrnkSeasonRepositoryImpl.create");

		if (seasonRepository.findOne(resource.getId()) != null) {
			throw new BadRequestException("Season already exists");
		}
		return save(resource);
	}

	@Override
	public CrnkSeason findOne(Integer id, QuerySpec querySpec) {
		LOG.debug("*********************** CrnkSeasonRepositoryImpl.findOne");

		return super.findOne(id, querySpec);
	}

	@Override
	public void delete(Integer id) {
		LOG.debug("*********************** CrnkSeasonRepositoryImpl.delete");

		super.delete(id);
	}

	protected CrnkSeasonRepositoryImpl(Class<CrnkSeason> resourceClass) {
		super(resourceClass);
	}

	@Override
	public Class<CrnkSeason> getResourceClass() {
		LOG.debug("*********************** CrnkSeasonRepositoryImpl.getResourceClass");
		return super.getResourceClass();
	}

	@Override
	public ResourceList<CrnkSeason> findAll(Iterable<Integer> ids, QuerySpec querySpec) {
		LOG.debug("*********************** CrnkSeasonRepositoryImpl.findAll");
		return super.findAll(ids, querySpec);
	}

	@Override
	public void setResourceRegistry(ResourceRegistry resourceRegistry) {
		LOG.debug("*********************** CrnkSeasonRepositoryImpl.setResourceRegistry");
		super.setResourceRegistry(resourceRegistry);
	}
}
