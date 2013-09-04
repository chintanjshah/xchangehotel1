package com.xchangehotel.utils;

import org.apache.log4j.Logger;
import org.restlet.Client;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;

/**
 * This class is used to perform various
 * tasks related to Rest API call
 * @author Saumil Shah
 *
 */
public class BaseResource
{	
	private static final Logger log = Logger.getLogger(BaseResource.class);
	/**
     * This method creates Client object to call REST-API with SSL support
     * and required certificate.
     */    
    public static Client getRestletClient(SystemConfig systemConfig) {
    		log.debug(BaseResource.class+":Client() invoke");
    		
    		Client client = null;
    		
    		try
    		{
	            Component comp = new Component();
	            comp.getClients().add(Protocol.HTTP);            
	            Server server = comp.getServers()
	                            .add(Protocol.HTTP, Integer.parseInt(systemConfig.serverport));
	            client = new Client(server.getContext(), Protocol.HTTP);
    		}catch(Exception e)
    		{
    			log.debug(BaseResource.class+":Client() catch exit");
    			return client;
    		}
    		
            log.debug(BaseResource.class+":Client() exit");
            
            return client;
    }
}
