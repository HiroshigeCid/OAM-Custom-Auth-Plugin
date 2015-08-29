/*
 * @autor Hiroshige Cid
 * @version 1.0
 * 
 */

package com.auth.example;
 

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


@Stateless
@WebService (serviceName="authenticateService")
public class GenericAuthService {
	
	//Create a dummy operation that simulates user authentication
	@WebMethod(operationName="authenticateUser")
	public boolean authenticateUser(@WebParam (name="idUsuario")Integer idUsuario, @WebParam(name="password")String password ){
		boolean result = false;
		if(idUsuario!=null && password!=null){
			if (idUsuario.equals("User123") && password.equals("easypass")){
				result = true;
			}
		}
		return result;
	}
	
}
