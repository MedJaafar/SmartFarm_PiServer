package com.pi_server.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mongodb.MongoException;
import com.pi.server.RaspiComponents.Raspi;
import com.pi.server.models.ConnectionSystem;
import com.pi.server.models.ConnectionSystem.connectionState;
import com.pi.server.models.FarmSystem;
import com.pi.server.models.FarmUser;
import com.pi.server.utilities.StringsUtilities;
import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi_server.mdbServices.NextSequenceService;
import com.pi_server.mongoRepositories.ConnectionSystemRepository;
import com.pi_server.mongoRepositories.FarmSystemRepository;
import com.pi_server.mongoRepositories.FarmUserRepository;

@RestController
@CrossOrigin(origins="*")
public class ConnectionController {

	private static final String TEST_SYSTEM_PATH = "/test_system/";
	private static final String RESTART_SYSTEM_PATH = "/restart_system";

	@Autowired
	ConnectionSystemRepository connectionRepository ;
	@Autowired 
	NextSequenceService nextsequenceService;
	@Autowired 
	FarmUserRepository userrepository;
	@Autowired 
	FarmSystemRepository systemrepository;

	// If this method returns "isConnected" it means that front is connected to the system. Else throw Error.
	@GetMapping(TEST_SYSTEM_PATH +"{systemId}/{usernameConnected}")
	public ConnectionSystem getConnectedSystem(@PathVariable String systemId, @PathVariable String usernameConnected) throws IOException, MongoException, ParseException {

		String cCode = StringsUtilities.getSystemNameCode();
		String userName = usernameConnected;
		ConnectionSystem connection = new ConnectionSystem();
		connection.setConnectionId(Integer.toString(nextsequenceService.getNextSequence(NextSequenceService.CONNECTION_SYSTEM_SEQ)));
		connection.setDateConnection(new Date());
		connection.setConnectionState(connectionState.ENUM_CONNECTION_REFUSED);

		FarmSystem farmSystem = systemrepository.findBySystemID(systemId);
		Optional<FarmUser> farmUser = userrepository.findByUsername(userName);
		farmUser.ifPresent(user -> {
			connection.setUserId(user.getId());
			connection.setSystemId(farmSystem.getSystemID());
			if(!user.getSystem().contains(farmSystem.getSystemID())) {
				connectionRepository.save(connection);
				throw new RuntimeException("Access Denied : User not Allowed to cotrole this System");
			}
		});
		String systemCode = farmSystem.getSystemCode();   
		if(cCode == null || systemCode == null || !cCode.equals(systemCode)) {
			connectionRepository.save(connection);
			throw new RuntimeException("Access Denied : User not Allowed to cotrole this System");
			//	
		} else {
			connection.setConnectionState(connectionState.ENUM_CONNECTION_OK);
			connectionRepository.save(connection);}
		return connection;
	}

	// Restars the App
	@PostMapping(RESTART_SYSTEM_PATH)
	public void restart() throws IOException {
		Raspi.lcd.clear();
		Raspi.lcd.write(Raspi.LCD_ROW_1, "RESTARTING",LCDTextAlignment.ALIGN_CENTER);
		Raspi.lcd.write(Raspi.LCD_ROW_2, "System",LCDTextAlignment.ALIGN_CENTER);
		System.out.println("WARNING : Restarting App !!!");

	} 
}
