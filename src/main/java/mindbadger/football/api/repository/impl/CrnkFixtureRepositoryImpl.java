package mindbadger.football.api.repository.impl;

import io.crnk.core.exception.BadRequestException;
import io.crnk.core.exception.ResourceNotFoundException;
import io.crnk.core.queryspec.FilterSpec;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.ConflictException;
import mindbadger.football.api.NotImplementedException;
import mindbadger.football.api.ValidationException;
import mindbadger.football.api.model.CrnkFixture;
import mindbadger.football.api.repository.CrnkFixtureRepository;
import mindbadger.football.api.repository.utils.SeasonUtils;
import mindbadger.football.domain.Fixture;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.repository.FixtureRepository;
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
	private SeasonUtils seasonUtils;
	
	protected CrnkFixtureRepositoryImpl() {
		super(CrnkFixture.class);
	}

	@Override
	public synchronized ResourceList<CrnkFixture> findAll(QuerySpec querySpec) {
		LOG.debug("******************************************");
		LOG.debug("  FIND ALL Fixture");
		LOG.debug("******************************************");

		List<String> attributesSupplied = new ArrayList<>();

		for (FilterSpec filterSpec : querySpec.getFilters()) {
			for (String path : filterSpec.getAttributePath()) {
				attributesSupplied.add(path);
			}
		}

		Iterable<Fixture> fixtures = null;

		//TODO Call optimised queries for certain combinations of filters
//		if (attributesSupplied.contains("seasonNumber") && ) {
//			fixtureRepository
//		}

		fixtures = fixtureRepository.findAll();

		List<CrnkFixture> katharsisFixtures = new ArrayList<CrnkFixture>();
		for (Fixture fixture : fixtures) {
			katharsisFixtures.add(new CrnkFixture(fixture));
		}

		return querySpec.apply(katharsisFixtures);
	}

	@Override
	public ResourceList<CrnkFixture> findAll(Iterable<String> ids, QuerySpec querySpec) {
		LOG.debug("******************************************");
		LOG.debug("  FIND ALL Fixture with id");
		LOG.debug("     ids=" + ids);
		LOG.debug("******************************************");
		return super.findAll(ids, querySpec);
	}

	@Override
	public CrnkFixture findOne(String id, QuerySpec querySpec) {
		LOG.debug("******************************************");
		LOG.debug("  FIND ONE Fixture");
		LOG.debug("     id=" + id);
		LOG.debug("******************************************");
		Fixture fixture = fixtureRepository.findOne(id);
		if (fixture == null) {
			throw new ResourceNotFoundException("No matching Fixture found");
		}
		return new CrnkFixture(fixture);
	}

	@Override
	public ResourceList<CrnkFixture> findFixturesBySeasonDivisionAndDate(String seasonDivisionId, Calendar fixtureDate, QuerySpec querySpec) {
		LOG.debug("******************************************");
		LOG.debug("  FIND Fixture by season, division & date");
		LOG.debug("     seasonDivisionId=" + seasonDivisionId);
		LOG.debug("     fixtureDate=" + fixtureDate);
		LOG.debug("******************************************");

		SeasonDivision seasonDivision = seasonUtils.getSeasonDivisionFromCrnkId(seasonDivisionId);

		List<Fixture> fixtures = fixtureRepository.getFixturesForDivisionInSeasonOnDate(seasonDivision, fixtureDate);

		List<CrnkFixture> crnkFixtures = new ArrayList<>();
		fixtures.stream().forEach(fixture -> {
			crnkFixtures.add(new CrnkFixture(fixture));
		});
		return querySpec.apply(crnkFixtures);
	}

	@Override
	public <S extends CrnkFixture> S save(S resource) {
		LOG.debug("******************************************");
		LOG.debug("  SAVE Fixture");
		LOG.debug("     Fixture=" + resource.getFixture());
		LOG.debug("******************************************");

		return (S) saveFixture(resource);
	}

	@Override
	public <S extends CrnkFixture> S create(S resource) {
		LOG.debug("******************************************");
		LOG.debug("  CREATE Fixture");
		LOG.debug("     Fixture=" + resource.getFixture());
		LOG.debug("******************************************");		Fixture fixture = fixtureRepository.findOne(resource.getId());
		if (fixture != null) {
			throw new ConflictException("Fixture already exists");
		}
		return (S) saveFixture(resource);
	}

	@Override
	public void delete(String id) {
		LOG.debug("******************************************");
		LOG.debug("  DELETE Fixture");
		LOG.debug("     id=" + id);
		LOG.debug("******************************************");
		Fixture fixture = fixtureRepository.findOne(id);
		if (fixture == null) {
			throw new ResourceNotFoundException("Fixture not found");
		}
		fixtureRepository.delete(fixture);
	}

	private CrnkFixture saveFixture (CrnkFixture resource) {
		Fixture existingFixture = fixtureRepository.findOne(resource.getId());

		if (resource.getFixture() == null) {
			throw new ValidationException("Fixture object cannot be null");
		}

		if (resource.getFixture().getFixtureDate() == null &&
				(resource.getFixture().getHomeGoals() != null || resource.getFixture().getAwayGoals() != null)) {
			throw new ValidationException("Fixture cannot have a score without a fixture date");
		}

		if (resource.getFixture().getSeasonDivision() == null ||
				resource.getFixture().getSeasonDivision().getSeason() == null) {
			throw new ValidationException("Must supply a Season");
		}

		if (resource.getFixture().getSeasonDivision().getDivision() == null) {
			throw new ValidationException("Must supply a Division");
		}

		if (resource.getFixture().getHomeTeam() == null) {
			throw new ValidationException("Must supply a Home Team");
		}

		if (resource.getFixture().getAwayTeam() == null) {
			throw new ValidationException("Must supply an Away Team");
		}

		if (existingFixture != null && existingFixture.getSeasonDivision() != null &&
				!existingFixture.getSeasonDivision().getSeason().
						equals(resource.getFixture().getSeasonDivision().getSeason())) {
			throw new ValidationException("Cannot update Season for an existing fixture");
		}

		if (existingFixture != null && existingFixture.getSeasonDivision() != null &&
				!existingFixture.getSeasonDivision().getDivision().
						equals(resource.getFixture().getSeasonDivision().getDivision())) {
			throw new ValidationException("Cannot update Division for an existing fixture");
		}

		if (existingFixture != null &&
				!existingFixture.getHomeTeam().
						equals(resource.getFixture().getHomeTeam())) {
			throw new ValidationException("Cannot update Home Team for an existing fixture");
		}

		if (existingFixture != null &&
				!existingFixture.getAwayTeam().
						equals(resource.getFixture().getAwayTeam())) {
			throw new ValidationException("Cannot update Away Team for an existing fixture");
		}

		Fixture savedFixture = fixtureRepository.save(resource.getFixture());
		return new CrnkFixture(savedFixture);
	}
}
