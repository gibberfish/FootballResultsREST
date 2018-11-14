package mindbadger.football.api.repository.impl;

import io.crnk.core.engine.registry.ResourceRegistry;
import io.crnk.core.exception.BadRequestException;
import io.crnk.core.queryspec.FilterSpec;
import mindbadger.football.api.repository.CrnkSeasonDivisionRepository;
import mindbadger.football.api.repository.CrnkSeasonRepository;
import mindbadger.football.api.repository.utils.SeasonUtils;
import mindbadger.football.api.util.SourceIdParser;
import mindbadger.football.domain.Division;
import mindbadger.football.domain.DomainObjectFactory;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.repository.SeasonRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkSeason;
import mindbadger.football.api.model.CrnkSeasonDivision;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrnkSeasonDivisionRepositoryImpl extends ResourceRepositoryBase<CrnkSeasonDivision, String>
	implements CrnkSeasonDivisionRepository {
	Logger LOG = Logger.getLogger (CrnkSeasonRepositoryImpl.class);

	@Autowired
	private SeasonUtils seasonUtils;

	@Autowired
	private SeasonRepository seasonRepository;

	@Autowired
	private DomainObjectFactory domainObjectFactory;
	
	protected CrnkSeasonDivisionRepositoryImpl() {
		super(CrnkSeasonDivision.class);
	}

	@Override
	public synchronized ResourceList<CrnkSeasonDivision> findAll(QuerySpec querySpec) {
		LOG.debug("*********************** CrnkSeasonDivisionRepositoryImpl.findAll");

		List<CrnkSeasonDivision> katharsisSeasonDivisions = new ArrayList<>();

		for (FilterSpec filter : querySpec.getFilters()) {
			LOG.debug("   filter: " + filter);
			LOG.debug("      expression: " + filter.getExpression());
			LOG.debug("      operator  : " + filter.getOperator());
			LOG.debug("      value     : " + filter.getValue());
			LOG.debug("      attribute : " + filter.getAttributePath().get(0));

			String value = filter.getValue().toString();
			value = value.replace("[", "");
			value = value.replace("]", "");

			if (filter.getAttributePath().size() > 0 && "id".equals(filter.getAttributePath().get(0))) {
				LOG.debug("  findAll filtering on id.");
				katharsisSeasonDivisions.add(findOne(value, querySpec));
			}
		}

		return querySpec.apply(katharsisSeasonDivisions);
	}

	@Override
	public ResourceList<CrnkSeasonDivision> findAll(Iterable<String> ids, QuerySpec querySpec) {
		LOG.debug("*********************** CrnkSeasonDivisionRepositoryImpl.findAll with ids");
		return super.findAll(ids, querySpec);
//		List<CrnkSeasonDivision> katharsisSeasonDivisions = new ArrayList<>();
//		for (String id : ids) {
//
//		}
//		throw new RuntimeException();
	}

	@Override
	public CrnkSeasonDivision findOne(String id, QuerySpec querySpec) {
		LOG.debug("*********************** CrnkSeasonDivisionRepositoryImpl.findOne");

		SeasonDivision seasonDivision = seasonUtils.getSeasonDivisionFromCrnkId(id);

		return seasonDivision == null ? null : new CrnkSeasonDivision(seasonDivision);
	}

	protected CrnkSeasonDivisionRepositoryImpl(Class<CrnkSeasonDivision> resourceClass) {
		super(resourceClass);
	}

	@Override
	public Class<CrnkSeasonDivision> getResourceClass() {
		LOG.debug("*********************** CrnkSeasonDivisionRepositoryImpl.getResourceClass");
		return super.getResourceClass();
	}

	@Override
	public <S extends CrnkSeasonDivision> S save(S resource) {
		LOG.debug("*********************** CrnkSeasonDivisionRepositoryImpl.save");
		Season existingSeason = seasonRepository.findOne(resource.getSeason().getId());
		Division newDivisionToAddToSeason = resource.getDivision().getDivision();
		int position = resource.getPosition();

		if (existingSeason == null) {
			throw new BadRequestException("Season doesn't exist");
		}

		if (newDivisionToAddToSeason == null) {
			throw new BadRequestException("Division doesn't exist");
		}

		SeasonDivision newSeasonDivision =
				domainObjectFactory.createSeasonDivision(existingSeason, newDivisionToAddToSeason, position);

		existingSeason.getSeasonDivisions().add(newSeasonDivision);

		seasonRepository.save(existingSeason);

		return resource;
	}

	@Override
	public <S extends CrnkSeasonDivision> S create(S resource) {
		LOG.debug("*********************** CrnkSeasonDivisionRepositoryImpl.create");

		Division newDivisionToAddToSeason = resource.getDivision().getDivision();
		Season existingSeason = seasonRepository.findOne(resource.getSeason().getId());

		if (existingSeason == null) {
			throw new BadRequestException("Season doesn't exist");
		}

		for (SeasonDivision seasonDivision : existingSeason.getSeasonDivisions()) {
			if (newDivisionToAddToSeason.equals(seasonDivision.getDivision())) {
				throw new BadRequestException("Division in Season already exists");
			}
		}

		return save(resource);
	}

	@Override
	public void delete(String id) {
		LOG.debug("*********************** CrnkSeasonDivisionRepositoryImpl.delete");

		Integer seasonId = SourceIdParser.parseSeasonId(id);
		String divisionId = SourceIdParser.parseDivisionId(id);

		Season existingSeason = seasonRepository.findOne(seasonId);
		if (existingSeason == null) {
			throw new BadRequestException("Season doesn't exist");
		}

		boolean found = false;
		for (SeasonDivision seasonDivision : existingSeason.getSeasonDivisions()) {
			if (divisionId.equals(seasonDivision.getDivision().getDivisionId())) {
				found = true;
				existingSeason.getSeasonDivisions().remove(seasonDivision);
			}
		}

		if (!found) {
			throw new BadRequestException("SeasonDivision doesn't exist");
		}

		seasonRepository.save(existingSeason);
	}

	@Override
	public void setResourceRegistry(ResourceRegistry resourceRegistry) {
		LOG.debug("*********************** CrnkSeasonDivisionRepositoryImpl.setResourceRegistry");
		super.setResourceRegistry(resourceRegistry);
	}

}
