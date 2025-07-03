package com.projet5safetynet.safetynet.service;

import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.repository.DataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
	@Autowired
	private DataService dataService;

    public List<MedicalRecord> getAllMedicalRecords() {
        return dataService.getDataBean().getMedicalrecords();
    }
}
