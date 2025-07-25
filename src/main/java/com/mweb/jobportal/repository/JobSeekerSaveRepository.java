package com.mweb.jobportal.repository;

import com.mweb.jobportal.entity.JobPostActivity;
import com.mweb.jobportal.entity.JobSeekerProfile;
import com.mweb.jobportal.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {

    List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);
    // Check if the job is already saved by this user
    boolean existsByJobAndUserId(JobPostActivity job, JobSeekerProfile user);

}
