package mindbadger.football.api.repository.impl;

import io.crnk.core.exception.BadRequestException;
import io.crnk.core.queryspec.FilterSpec;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkFixture;
import mindbadger.football.api.repository.CrnkFixtureRepository;
import mindbadger.football.api.util.SourceIdParser;
import mindbadger.football.domain.Fixture;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.repository.FixtureRepository;
import mindbadger.football.repository.SeasonRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class CrnkFixtureRepositoryImpl extends ResourceRepositoryBase<CrnkFixture, String>
	implements CrnkFixtureRepository {
	Logger LOG = Logger.getLogger (CrnkFixtureRepositoryImpl.class);
	
	@Autowired
	private FixtureRepository fixtureRepository;

	@Autowired
	private SeasonRepository seasonRepository;
	
	protected CrnkFixtureRepositoryImpl() {
		super(CrnkFixture.class);
	}

	@Override
	public synchronized ResourceList<CrnkFixture> findAll(QuerySpec querySpec) {
		LOG.debug("*********************** CrnkFixtureRepositoryImpl.findAll");

		List<CrnkFixture> katharsisFixtures = new ArrayList<>();

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
				//katharsisSeasonDivisionTeams.add(findOne(value, querySpec));
			}
		}

		return querySpec.apply(katharsisFixtures);
	}

	@Override
	public ResourceList<CrnkFixture> findAll(Iterable<String> ids, QuerySpec querySpec) {
		LOG.debug("*********************** CrnkFixtureRepositoryImpl.findAll with ids");
		return super.findAll(ids, querySpec);
	}

	@Override
	public CrnkFixture findOne(String id, QuerySpec querySpec) {
		LOG.debug("*********************** CrnkFixtureRepositoryImpl.findOne");
		Fixture fixture = fixtureRepository.findOne(id);
		return new CrnkFixture(fixture);
	}

	@Override
	public ResourceList<CrnkFixture> findFixturesBySeasonDivisionAndDate(String seasonDivisionId, Calendar fixtureDate, QuerySpec querySpec) {
		LOG.debug("*********************** CrnkFixtureRepositoryImpl.findFixturesBySeasonDivisionAndDate");

		Season season = seasonRepository.findOne(SourceIdParser.parseSeasonId(seasonDivisionId));
		String divisionId = SourceIdParser.parseDivisionId (seasonDivisionId);
		SeasonDivision seasonDivision = null;
		for (SeasonDivision seasonDivisionToCheck : season.getSeasonDivisions()) {
			if (divisionId.equals(seasonDivisionToCheck.getDivision().getDivisionId())) {
				seasonDivision = seasonDivisionToCheck;
			}
		}

		List<Fixture> fixtures = fixtureRepository.getFixturesForDivisionInSeasonOnDate(seasonDivision, fixtureDate);

		List<CrnkFixture> crnkFixtures = new ArrayList<>();
		fixtures.stream().forEach(fixture -> {
			crnkFixtures.add(new CrnkFixture(fixture));
		});
		return querySpec.apply(crnkFixtures);
	}

	@Override
	public <S extends CrnkFixture> S save(S resource) {
		LOG.debug("*********************** CrnkFixtureRepositoryImpl.save");
		Fixture savedFixture = fixtureRepository.save(resource.getFixture());
		return (S) new CrnkFixture(savedFixture);
	}

	@Override
	public <S extends CrnkFixture> S create(S resource) {
		LOG.debug("*********************** CrnkFixtureRepositoryImpl.create");
		//TODO Check that the fixture is not a duplicate

		return save(resource);
	}

	@Override
	public void delete(String id) {
		LOG.debug("*********************** CrnkFixtureRepositoryImpl.delete");
		Fixture fixture = fixtureRepository.findOne(id);
		if (fixture == null) {
			throw new BadRequestException("Fixture not found");
		}
		fixtureRepository.delete(fixture);
	}
}
;