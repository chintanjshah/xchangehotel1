package com.xchangehotel.service;

import org.springframework.stereotype.Service;

import com.xchangehotel.model.User;

@Service("userService")
public class UserService extends BaseApiService {
	
	public String getLogin(User user)
	{
        try
        {
        	return generateURIParameters(user);
        	
        }catch(Exception e)
        {
        	return e.getMessage();
        }
	}
}
