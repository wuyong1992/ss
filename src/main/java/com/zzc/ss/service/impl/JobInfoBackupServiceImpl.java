package com.zzc.ss.service.impl;

import com.zzc.ss.entity.JobInfo;
import com.zzc.ss.entity.JobInfoBackup;
import com.zzc.ss.repository.JobInfoBackupRepository;
import com.zzc.ss.repository.JobInfoRepository;
import com.zzc.ss.service.JobInfoBackupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author JianGuo
 * on 2018/8/16
 * description:
 */
@Service("jobInfoBackup")
public class JobInfoBackupServiceImpl implements JobInfoBackupService {

    @Autowired
    private JobInfoBackupRepository jobInfoBackupRepository;

    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Override
    public void backupJobInfo(Integer jobId) {
        JobInfo jobInfo = jobInfoRepository.getOne(jobId);
        if (jobInfo != null) {
            JobInfoBackup backup = new JobInfoBackup();
            BeanUtils.copyProperties(jobInfo, backup);
            backup.setDeleteTime(new Date());
            jobInfoBackupRepository.save(backup);
        }
    }
}
