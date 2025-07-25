package com.mweb.jobportal.services;

import com.mweb.jobportal.entity.JobPostActivity;
import com.mweb.jobportal.entity.JobSeekerProfile;
import com.mweb.jobportal.entity.JobSeekerSave;
import com.mweb.jobportal.repository.JobSeekerSaveRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveService {

    private final JobSeekerSaveRepository jobSeekerSaveRepository;

    public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId) {
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
        return jobSeekerSaveRepository.findByJob(job);
    }
    @Transactional
    public void addNew(JobSeekerSave jobSeekerSave) {
        if (!jobSeekerSaveRepository.existsByJobAndUserId(jobSeekerSave.getJob(), jobSeekerSave.getUserId())) {
            jobSeekerSaveRepository.save(jobSeekerSave);
        }
    }
}
