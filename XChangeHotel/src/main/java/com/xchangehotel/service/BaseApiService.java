package com.xchangehotel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.restlet.Client;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xchangehotel.model.User;
import com.xchangehotel.utils.SecurityUtils;
import com.xchangehotel.utils.SystemConfig;

/**
 * This is base class which contains basic common methods to call the Rest API services.
 * 
 * @author Saumil
 *
 */
@Service("baseApiService")
public class BaseApiService 
{	
	private static final Logger log = Logger.getLogger(BaseApiService.class);
	
	@Autowired
	private SystemConfig systemConfig;
	

	/**
     * This method generates URI including all required parameters with its
     * proper values.
     */
    protected String generateURIParameters(User loggedInUser)
                    throws Exception {
            String authToken = doLogin(loggedInUser.getUsername(), SecurityUtils.getMD5(loggedInUser.getPassword()));
            String apiSignature = SecurityUtils.getMD5(loggedInUser.getApiKey() + loggedInUser.getApiSecret());// key,
                                                                           // input
            String apiKey = URLEncoder.encode(loggedInUser.getApiKey(), "UTF-8");
            apiSignature = URLEncoder.encode(apiSignature, "UTF-8");
            authToken = URLEncoder.encode(authToken, "UTF-8");
            String uriParameters = "?api-key=" + apiKey + "&signature="
                            + apiSignature + "&performer-token="
                            + authToken;
            return uriParameters;
    }

        
    /**
     * This method loads all key-value pairs from given property file-name.
     */
    protected Properties loadPropertyFile(String propertyFile)
                    throws IOException, FileNotFoundException {
            Properties prop = new Properties();
            File f = new File(propertyFile);
            prop.load(new FileReader(f));
            return prop;
    }
    /**
     * This method does login into application to get proper
     * authentication-token and returns it.
     */
	private String doLogin(String userId, String password) throws Exception {
            Representation representation = loginWithoutOTP(userId,password);
            String response = representation.getText();
            JSONObject loginResponse = JSONObject.fromObject(response);
            System.out.println(BaseApiService.class+": doLogin(): "+loginResponse.toString()); 
            String authToken = loginResponse
                            .getString("authentication-token");
            
            return authToken;
    }
	
    private Representation loginWithoutOTP(String loggedInUserId, String userPassword) throws Exception
	{
		System.out.println("In BaseApiService : loginWithoutOTP()");
		Representation representation = null;
		try{
			JSONObject jsonData = new JSONObject();
			jsonData.put("userid", loggedInUserId);
			jsonData.put("password", userPassword);	
			System.out.println("In BaseApiService : loginWithoutOTP() : systemConfig.loginapi.trim():=>"+systemConfig.COMMON_DOXONOMY_REST_API.trim() + systemConfig.loginapi.trim() + " jsonData:"+jsonData.toString());
			Client client = getRestletClient();
			ClientResource service = new ClientResource(systemConfig.COMMON_DOXONOMY_REST_API.trim() + systemConfig.loginapi.trim());
			service.setNext(client);
			representation = service.post(jsonData.toString());			
		} catch (ResourceException e){
			log.error(
					"Could not connect to REST API server. Make sure the server is running on given host address & port and REST API is deployed on it.",
					e);			
		} catch (Exception e){
			log.error(e.toString(), e);			
		}
		return representation;
	}

	public Client getRestletClient() throws Exception
	{
		Client client = null;
		try	{
			Component comp = new Component();
			comp.getClients().add(Protocol.HTTP);
			int trustPort = Integer.parseInt(systemConfig.serverport.trim());
			Server server = comp.getServers().add(Protocol.HTTP, trustPort);
			client = new Client(server.getContext(), Protocol.HTTP);
		} catch (Exception e) {
			log.error("Error in creating RestLet Client: ", e);
			throw e;
		}
		return client;
	}

}
