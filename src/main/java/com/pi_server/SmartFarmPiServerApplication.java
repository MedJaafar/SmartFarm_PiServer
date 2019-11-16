package com.pi_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.pi.server.models.FarmSystem;
import com.pi.server.models.SystemURL;
import com.pi.server.utilities.StringsUtilities;
import com.pi_server.mdbServices.NextSequenceService;
import com.pi_server.mongoRepositories.FarmSystemRepository;
import com.pi_server.mongoRepositories.SystemUrlRepository;

@EnableScheduling
@SpringBootApplication
public class SmartFarmPiServerApplication {
	
	private static final String NGROK_HTTP_COMMAND = "/home/pi/ngrok/ngrok http 8080";
	private static final String NGROK_CURL_COMMAND = "curl -s localhost:4040/api/tunnels";
	
	@Autowired 
	private NextSequenceService nextsequenceService;
	@Autowired
	private SystemUrlRepository  urlRepository;
	@Autowired
	private FarmSystemRepository systemRepository;
	
	/*/Spring Launcher/	*/ 
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(SmartFarmPiServerApplication.class, args);	
	}
	/*/Spring Launcher/	*/
	
	// MongoDB CONFIGURATION 
	@Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoMappingContext context) {
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
        return mongoTemplate;
    }
	
	// Deploy using ngRok after startup -> Store the dynamic URL in MongoDB
	@EventListener(ApplicationReadyEvent.class)
	public void ngRokAfterStartup() throws IOException, InterruptedException  {
		Runtime runtime = Runtime.getRuntime();
		String line;
		String outputText ="";
		String sysCode = StringsUtilities.getSystemNameCode();
		// Prepare to reader from the output file
		Process command1 = runtime.exec(NGROK_HTTP_COMMAND);
		Thread.sleep(5000);  // attend to first command to respond
		Process command2 = runtime.exec(NGROK_CURL_COMMAND);
		BufferedReader br = new BufferedReader(new InputStreamReader(command2.getInputStream()));
		while((line = br.readLine()) != null) {
			outputText = outputText + line;
		}
		br.close();
		// Update url date -> MongoDB
		String url = StringsUtilities.getngrokUrl(outputText);
		SystemURL systemUrl = new SystemURL();
		String systemUrlId = Integer.toString(nextsequenceService.getNextSequence(NextSequenceService.SYSTEM_URL_SEC));
		systemUrl.setIdUrl(systemUrlId);
		systemUrl.setUpdateTime(new Date());
		systemUrl.setUrl(url);
		Optional<FarmSystem> systemFarm = systemRepository.findBySystemCode(sysCode);
		systemFarm.ifPresent(system -> {
			systemUrl.setSystemId(system.getSystemID());
		});
		urlRepository.save(systemUrl);
		command2.waitFor();
		command1.waitFor();		
	}
}



