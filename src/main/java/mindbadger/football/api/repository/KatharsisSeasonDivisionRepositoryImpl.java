package mindbadger.football.api.repository;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.KatharsisSeason;
import mindbadger.football.api.model.KatharsisSeasonDivision;
import mindbadger.football.domain.Season;
import mindbadger.football.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KatharsisSeasonDivisionRepositoryImpl extends ResourceRepositoryBase<KatharsisSeasonDivision, String>
	implements KatharsisSeasonDivisionRepository {

	protected KatharsisSeasonDivisionRepositoryImpl() {
		super(KatharsisSeasonDivision.class);
	}

	@Autowired
	private KatharsisSeasonRepository seasonRepository;

	@Override
	public synchronized ResourceList<KatharsisSeasonDivision> findAll(QuerySpec querySpec) {
		System.out.println("******** DEBUG: entered findAll for SeasonDivisionRepository");
		//Iterable<Season> seasons = seasonRepository.findAll();
		
    	List<KatharsisSeasonDivision> katharsisSeasonDivisions = new ArrayList<KatharsisSeasonDivision> ();
    	
    	//TODO This repo only has context to a season - how can we implement these methods accordingly?
    	// Don't want to have a JPA repo just for season divisions
//    	for (Season season : seasons) {
//    		katharsisSeasons.add(new KatharsisSeason(season));
//    	}
		
		
		return querySpec.apply(katharsisSeasonDivisions);
	}
}
