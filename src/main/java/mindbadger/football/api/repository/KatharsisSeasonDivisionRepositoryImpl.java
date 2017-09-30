package mindbadger.football.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.KatharsisSeason;
import mindbadger.football.api.model.KatharsisSeasonDivision;

@Component
public class KatharsisSeasonDivisionRepositoryImpl extends ResourceRepositoryBase<KatharsisSeasonDivision, String>
	implements KatharsisSeasonDivisionRepository {

	@Autowired
	private KatharsisSeasonRepository seasonRepository;
	
	protected KatharsisSeasonDivisionRepositoryImpl() {
		super(KatharsisSeasonDivision.class);
	}

	@Override
	public synchronized ResourceList<KatharsisSeasonDivision> findAll(QuerySpec querySpec) {
		throw new RuntimeException();
	}

	@Override
	public ResourceList<KatharsisSeasonDivision> findAll(Iterable<String> ids, QuerySpec querySpec) {
		throw new RuntimeException();
	}

	@Override
	public KatharsisSeasonDivision findOne(String id, QuerySpec querySpec) {
		KatharsisSeason season = seasonRepository.findOne(parseSeasonIdFromSourceId(id), querySpec);
		for (KatharsisSeasonDivision seasonDivision : season.getSeasonDivisions()) {
			if (id.equals(seasonDivision.getId())) {
				return seasonDivision;
			}
		}
		return null;
	}
	
    private Integer parseSeasonIdFromSourceId (String id) {
    	String[] idSplit = id.split("-");
    	return Integer.parseInt(idSplit[0]);
    }
}
