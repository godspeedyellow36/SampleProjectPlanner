package com.spp.www.items;

/**
 * Model to hold result of each process
 *
 */
public class ResultItem extends BaseItem {

	private String status;
	private String code;
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
