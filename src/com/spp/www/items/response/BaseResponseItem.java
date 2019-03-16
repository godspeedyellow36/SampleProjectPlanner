package com.spp.www.items.response;

import com.spp.www.items.ResultItem;

public class BaseResponseItem {

	private ResultItem result;

	public ResultItem getResult() {
		if (null == result) {
			result = new ResultItem();
		}
		return result;
	}

	public void setResult(ResultItem result) {
		this.result = result;
	}

}
