package mindbadger.football.api.repository;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;
import mindbadger.football.api.model.KatharsisDivision;
import mindbadger.football.api.model.KatharsisSeason;
import mindbadger.football.domain.Division;
import mindbadger.football.domain.Season;
import mindbadger.football.repository.DivisionRepository;
import mindbadger.football.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Component
@Validated
public class KatharsisSeasonRepository extends ResourceRepositoryBase<KatharsisSeason, String> {

	protected KatharsisSeasonRepository() {
		super(KatharsisSeason.class);
	}

	@Autowired
	private SeasonRepository seasonRepository;

	@Override
	public synchronized ResourceList<KatharsisSeason> findAll(QuerySpec querySpec) {
		Iterable<Season> seasons = seasonRepository.findAll();
		
    	List<KatharsisSeason> katharsisSeasons = new ArrayList<KatharsisSeason> ();
    	for (Season season : seasons) {
    		katharsisSeasons.add(new KatharsisSeason(season));
    	}
		
		
		return querySpec.apply(katharsisSeasons);
	}
}
