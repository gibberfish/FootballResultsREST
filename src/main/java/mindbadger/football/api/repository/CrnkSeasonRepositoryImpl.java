package mindbadger.football.api.repository;

import java.util.ArrayList;
import java.util.List;

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
}
