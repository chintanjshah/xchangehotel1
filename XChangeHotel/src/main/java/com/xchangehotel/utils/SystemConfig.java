package com.xchangehotel.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Class containing all configuration and usage
 * related values.
 * 
 * @author Kunal Shah
 *
 */
@Service("systemConfig")
public class SystemConfig
{	
	
	@Value("${common_doxonomy_rest_api}")
	public String COMMON_DOXONOMY_REST_API;
	
	@Value("${api_uri_login}")
	public String loginapi;
	
//	@Value("${protocol}")
//	public String serverprotocol;	
	
	@Value("${httpPort}")
	public String serverport;
	
//	@Value("${truststorePath}")
//	public String certificatepath;
	
	@Value("${api-key}")
	public String apikey;
	
	@Value("${api-secret}")
	public String apisecret;
}
