package com.auth.libs.plugin;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

import oracle.security.am.plugin.ExecutionStatus;
import oracle.security.am.plugin.MonitoringData;
import oracle.security.am.plugin.authn.AbstractAuthenticationPlugIn;
import oracle.security.am.plugin.authn.AuthenticationContext;
import oracle.security.am.plugin.authn.AuthenticationException;


public class GenericAuthPluginLibraries extends AbstractAuthenticationPlugIn {

	private Logger log = Logger.getLogger(this.getClass().getName());
	
	@Override
	public String getDescription() {
		return "This is an example of Generic Authentication Library Plugin";
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

	@Override
	public ExecutionStatus process(AuthenticationContext contexxt)
			throws AuthenticationException {
		log.info("Exporting libraries ... ");
		return ExecutionStatus.SUCCESS;
	}

	@Override
	public void setMonitoringStatus(boolean status) {
		
	}
	
}
