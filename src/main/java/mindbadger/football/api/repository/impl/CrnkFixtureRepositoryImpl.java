package mindbadger.football.api.repository.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class CrnkFixtureRepositoryImpl extends ResourceRepositoryBase<CrnkFixture, String>
	implements CrnkFixtureRepository {
	
	@Autowired
	private FixtureRepository fixtureRepository;

	@Autowired
	private SeasonRepository seasonRepository;
	
	protected CrnkFixtureRepositoryImpl() {
		super(CrnkFixture.class);
	}

	@Override
	public synchronized ResourceList<CrnkFixture> findAll(QuerySpec querySpec) {

		System.out.println(querySpec.toString());

		System.out.println("Filters:");
		for (FilterSpec spec : querySpec.getFilters()) {
			System.out.println(spec);
		}

		throw new RuntimeException();
	}

	@Override
	public ResourceList<CrnkFixture> findAll(Iterable<String> ids, QuerySpec querySpec) {
		throw new RuntimeException();
	}

	@Override
	public CrnkFixture findOne(String id, QuerySpec querySpec) {
		Fixture fixture = fixtureRepository.findOne(id);
		return new CrnkFixture(fixture);
	}

	@Override
	public ResourceList<CrnkFixture> findFixturesBySeasonDivisionAndDate(String seasonDivisionId, Calendar fixtureDate, QuerySpec querySpec) {

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
}
