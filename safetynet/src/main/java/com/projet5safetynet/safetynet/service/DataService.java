package com.projet5safetynet.safetynet.service;

import org.springframework.stereotype.Service;

import com.projet5safetynet.safetynet.model.DataBean;

@Service
public class DataService {
	private DataBean dataBean;

	public DataBean getDataBean() {
		return dataBean;
	}

	public void setDataBean(DataBean dataBean) {
		this.dataBean = dataBean;
	}
}
