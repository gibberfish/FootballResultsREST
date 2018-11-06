package mindbadger.football.api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import io.crnk.core.engine.registry.ResourceRegistry;
import io.crnk.core.exception.BadRequestException;
import mindbadger.football.api.repository.CrnkSeasonRepository;
import mindbadger.football.domain.DomainObjectFactory;
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

	protected CrnkSeasonRepositoryImpl() {
		super(CrnkSeason.class);
	}

	@Autowired
	private DomainObjectFactory domainObjectFactory;

	@Autowired
	private SeasonRepository seasonRepository;

	@Override
	public synchronized ResourceList<CrnkSeason> findAll(QuerySpec querySpec) {
		Iterable<Season> seasons = seasonRepository.findAll();
		
    	List<CrnkSeason> katharsisSeasons = new ArrayList<CrnkSeason> ();
    	for (Season season : seasons) {
    		katharsisSeasons.add(new CrnkSeason(season));
    	}
		
		return querySpec.apply(katharsisSeasons);
	}

	@Override
	public <S extends CrnkSeason> S save(S resource) {
		Season newSeason = domainObjectFactory.createSeason(resource.getId());
		seasonRepository.save(newSeason);
		return resource;
	}

	@Override
	public <S extends CrnkSeason> S create(S resource) {
		if (seasonRepository.findOne(resource.getId()) != null) {
			throw new BadRequestException("Season already exists");
		}
		return save(resource);
	}

	@Override
	public CrnkSeason findOne(Integer id, QuerySpec querySpec) {
		return super.findOne(id, querySpec);
	}

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}

	@Override
	public void setResourceRegistry(ResourceRegistry resourceRegistry) {
		super.setResourceRegistry(resourceRegistry);
	}
}
