package mindbadger.football.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkDivision;
import mindbadger.football.api.model.CrnkFixture;
import mindbadger.football.api.model.CrnkSeason;
import mindbadger.football.api.model.CrnkSeasonDivision;
import mindbadger.football.domain.Division;
import mindbadger.football.domain.Fixture;
import mindbadger.football.repository.DivisionRepository;
import mindbadger.football.repository.FixtureRepository;

@Component
public class CrnkFixtureRepositoryImpl extends ResourceRepositoryBase<CrnkFixture, String>
	implements CrnkFixtureRepository {
	
	@Autowired
	private FixtureRepository fixtureRepository;
	
	protected CrnkFixtureRepositoryImpl() {
		super(CrnkFixture.class);
	}

	@Override
	public synchronized ResourceList<CrnkFixture> findAll(QuerySpec querySpec) {
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
}
