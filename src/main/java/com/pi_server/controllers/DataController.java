package com.pi_server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import com.pi_server.mdbServices.NextSequenceService;


@CrossOrigin(origins="*")
@RestController
public class DataController {

	@Autowired
	NextSequenceService nextsequenceService;
	
//	@ResponseBody
//    @RequestMapping("/insert")
//    public String testInsert() {
//		Etat_Systeme etat = new Etat_Systeme();
//		etat.setIdEtat(nextsequenceService.getNextSequence("customSequences"));
//		etat.setDate_heure(new Date());
//		etat.setPostType(0);
//		etat.setTemperature(20.25);
//		etat.setHumidity(50);
//		etat.setWater(false);
//		etatrepository.save(etat);
//        return "Inserted: " + etat;
//    }
}
