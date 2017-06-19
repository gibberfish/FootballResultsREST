package mindbadger.football.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.annotations.JsonApiDelete;
import io.katharsis.repository.annotations.JsonApiFindAll;
import io.katharsis.repository.annotations.JsonApiFindAllWithIds;
import io.katharsis.repository.annotations.JsonApiResourceRepository;
import io.katharsis.repository.annotations.JsonApiSave;
import mindbadger.football.api.model.KatharsisDivision;
import mindbadger.football.domain.Division;
import mindbadger.football.repository.DivisionRepository;

@Component
@JsonApiResourceRepository(KatharsisDivision.class)
@Validated
public class KatharsisDivisionRepository {
	@Autowired
	private DivisionRepository divisionRepository;
	
	@JsonApiSave
    public <S extends KatharsisDivision> S save(S entity) {
		return null;
	}
	
    @JsonApiFindAll
    public Iterable<KatharsisDivision> findAll(QueryParams requestParams) {
    	Iterable<Division> divisions = divisionRepository.findAll();
    	
    	List<KatharsisDivision> katharsisDivisions = new ArrayList<KatharsisDivision> ();
    	for (Division division : divisions) {
    		katharsisDivisions.add(new KatharsisDivision(division));
    	}
    	return katharsisDivisions;
    }
    
    @JsonApiFindAllWithIds
    public Iterable<KatharsisDivision> findAll(Iterable<Long> taskIds, QueryParams requestParams) {
    	return null;
    }
    
    @JsonApiDelete
    public void delete(Long taskId) {
    }
}
