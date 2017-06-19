package mindbadger.football.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mindbadger.football.api.model.KatharsisDivision;

@RestController
@RequestMapping("/divisionold")
public class DivisionController {

	@RequestMapping(method = RequestMethod.GET)
	public KatharsisDivision getDivision (@RequestParam(value="id") String id) {
//		if ("0".equals(id)) {
//			return new KatharsisDivision ("0", "Premier");
//		} else if ("1".equals(id)) {
//			return new KatharsisDivision ("1", "Championship");
//		} else {
			throw new RuntimeException ("No such team");
//		}
	}
}
