package com.elai.common.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author:
 * @date:
 * @description:
 */

@Component
@ConfigurationProperties(prefix = "third.api")
public class ThirdProperties {

	private String ckUrl;

	private String xxjobUrl;

	private String localUrl;


	public ThirdProperties() {
	}

	public String getCkUrl() {
		return ckUrl;
	}

	public void setCkUrl(String ckUrl) {
		this.ckUrl = ckUrl;
	}

	public String getXxjobUrl() {
		return xxjobUrl;
	}

	public void setXxjobUrl(String xxjobUrl) {
		this.xxjobUrl = xxjobUrl;
	}

	public String getLocalUrl() {
		return localUrl;
	}

	public void setLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}
}
