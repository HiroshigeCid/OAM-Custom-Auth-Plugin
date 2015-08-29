package com.auth.plugin;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.ws.BindingProvider;

import com.auth.example.AuthenticateService;
import com.auth.example.GenericAuthService;
import com.sun.xml.ws.client.BindingProviderProperties;

import oracle.security.am.plugin.ExecutionStatus;
import oracle.security.am.plugin.MonitoringData;
import oracle.security.am.plugin.PluginConfig;
import oracle.security.am.plugin.authn.AbstractAuthenticationPlugIn;
import oracle.security.am.plugin.authn.AuthenticationContext;
import oracle.security.am.plugin.authn.AuthenticationException;
import oracle.security.am.plugin.authn.CredentialParam;

public class GenericAuthPlugin extends AbstractAuthenticationPlugIn{

	private Logger log = Logger.getLogger(this.getClass().getName());
	private String idUser;
	private String password;
	private String requestTime;
	private String connectTime;
	
	
	@Override
	public String getDescription() {
		return "This is an example of Generic Authentication Plugin";
	}

	@Override
	public Map<String, MonitoringData> getMonitoringData() {		
		return Collections.emptyMap();
	}

	@Override
	public boolean getMonitoringStatus() {
		return false;
	}

	@Override
	public String getPluginName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public int getRevision() {
		return 0;
	}
	
	/*
	 * In this method we can read the init configuration attributes
	 */
	@Override
	public ExecutionStatus initialize(PluginConfig config){
		super.initialize(config);	
		
		ExecutionStatus status = ExecutionStatus.FAILURE;
		
		idUser   	=   (String)config.getParameter("gen_auth_iduser");		
		password 	=   (String)config.getParameter("gen_auth_password");
		requestTime = 	(String)config.getParameter("gen_auth_request_time");
		connectTime = 	(String)config.getParameter("gen_auth_connect_time");
		
		status = ExecutionStatus.SUCCESS;		
		return status;
	}

	/*
	 * Here we can override the configuration attributes with the getParamFromContext and process
	 * the authentication flow that consumes the web service.  
	 */
	@Override
	public ExecutionStatus process(AuthenticationContext context)
			throws AuthenticationException {
		log.info("Authentication process starting ... ");
		
		ExecutionStatus status = ExecutionStatus.FAILURE;
		try{
			
			Integer rtime = (connectTime!=null&& !connectTime.trim().isEmpty()) ? Integer.valueOf(connectTime):3000;
	        Integer ctime = (requestTime!=null&& !requestTime.trim().isEmpty()) ? Integer.valueOf(requestTime):3000;	      
	        idUser 	 = getParamFromContext(context,"gen_auth_iduser");
			password = getParamFromContext(context, "gen_auth_password");
			Integer user = (idUser.isEmpty() || !idUser.matches("[0-9]+"))?null:Integer.valueOf(idUser);
	        if(user==null || password==null){
	        	throw new IllegalArgumentException("Your information has an invalid format");
	        }
			AuthenticateService service = new AuthenticateService();
			GenericAuthService port = service.getGenericAuthServicePort();
			Map<String,Object> requestContext = ((BindingProvider)service).getRequestContext();		
			requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, rtime);
			requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, ctime);						
			
			boolean result = port.authenticateUser(user, password);
			if(result){
				status = ExecutionStatus.SUCCESS;
			}
			
		}catch(Exception e){
			log.severe(e.getMessage());
		}
		log.info("Authentication process ending ... ");
		return status;
	}

	@Override
	public void setMonitoringStatus(boolean status) {
		
	}
	
	/*
	 * This method allow us override at runtime the init params with his value at authentication 
	 * context. 
	 */
	private String getParamFromContext(AuthenticationContext cont, String param){
		CredentialParam tmp = cont.getCredential().getParam(param);
		if(tmp!=null && tmp.getValue()!=null){
			String t = (String)tmp.getValue();
			return t;		
		}else{
			return "";
		}
	}
}
