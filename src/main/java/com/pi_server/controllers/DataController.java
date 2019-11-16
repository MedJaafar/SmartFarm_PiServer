package com.pi_server.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.pi.server.models.Etat_Systeme;
import com.pi_server.mdbServices.NextSequenceService;
import com.pi_server.mongoRepositories.Etat_SystemeRepository;

@RestController
public class DataController {

	@Autowired 
	NextSequenceService nextsequenceService;
	@Autowired
	Etat_SystemeRepository etatrepository;
	
	@ResponseBody
    @RequestMapping("/insert")
    public String testInsert() {
        
		Etat_Systeme etat = new Etat_Systeme();
		etat.setIdEtat(nextsequenceService.getNextSequence("customSequences"));
		etat.setDate_heure(new Date());
		etat.setPostType(0);
		etat.setTemperature(20.25);
		etat.setHumidity(50);
		etat.setWater(false);
        
		etatrepository.save(etat);
        return "Inserted: " + etat;
    }
}
