package com.spp.www.services;

import com.spp.www.constants.CoreConstants;
import com.spp.www.constants.SuccessMessageConstants;
import com.spp.www.items.ResultItem;
import com.spp.www.items.response.BaseResponseItem;

public class BaseService {

	public BaseResponseItem baseResponseItem;
	
	public void generateErrorResult(String code, String message) {
		ResultItem result = new ResultItem();
		result.setStatus(CoreConstants.ERROR);
		result.setCode(code);
		result.setMessage(message);
		getBaseResponseItem().setResult(result);		
	}

	public BaseResponseItem getBaseResponseItem() {
		if (null == baseResponseItem) {
			baseResponseItem = new BaseResponseItem();
			ResultItem result = new ResultItem();
			result.setStatus(CoreConstants.SUCCESS);
			result.setCode(CoreConstants.SUCCESS);
			result.setMessage(SuccessMessageConstants.SUCCESSFUL_PROCESS);
			baseResponseItem.setResult(result);
		}
		return baseResponseItem;
	}

	public void setBaseResponseItem(BaseResponseItem baseResponseItem) {
		this.baseResponseItem = baseResponseItem;
	}

}
