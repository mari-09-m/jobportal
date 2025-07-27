package com.mweb.jobportal.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "job"})
})
public class JobSeekerSave implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) // No cascade here
    @JoinColumn(name = "userId", referencedColumnName = "user_account_id", nullable = false)
    private JobSeekerProfile userId;

    @ManyToOne(fetch = FetchType.LAZY) // No cascade here
    @JoinColumn(name = "job", referencedColumnName = "jobPostId", nullable = false)
    private JobPostActivity job;

    public JobSeekerSave() {
    }

    public JobSeekerSave(Integer id, JobSeekerProfile userId, JobPostActivity job) {
        this.id = id;
        this.userId = userId;
        this.job = job;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JobSeekerProfile getUserId() {
        return userId;
    }

    public void setUserId(JobSeekerProfile userId) {
        this.userId = userId;
    }

    public JobPostActivity getJob() {
        return job;
    }

    public void setJob(JobPostActivity job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "JobSeekerSave{" +
                "id=" + id +
                ", userId=" + (userId != null ? userId.getUserAccountId() : "null") +
                ", jobPostId=" + (job != null ? job.getJobPostId() : "null") +
                '}';
    }
}
