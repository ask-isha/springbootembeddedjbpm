package com.cts.spring;

import java.io.Serializable;
import java.util.Map;

public class ApiService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7922910590947567369L;
	
	public String callApi(Map<String,Object> map) throws Exception {
		System.out.println("called api service");
		System.out.println("returning from api service");
		throw new AppException("Deliberately thrown exception");
		//return null;
	}

}
