package com.zzc.ss.service;

/**
 * @author JianGuo
 * on 2018/8/16
 * description:
 */
public interface JobInfoBackupService {
    /**
     * 备份{@link com.zzc.ss.entity.JobInfo}
     * @param jobId
     */
    void backupJobInfo(Integer jobId);
}
