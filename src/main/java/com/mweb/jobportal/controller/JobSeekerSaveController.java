package com.mweb.jobportal.controller;

import com.mweb.jobportal.entity.JobPostActivity;
import com.mweb.jobportal.entity.JobSeekerProfile;
import com.mweb.jobportal.entity.JobSeekerSave;
import com.mweb.jobportal.entity.Users;
import com.mweb.jobportal.services.JobPostActivityService;
import com.mweb.jobportal.services.JobSeekerProfileService;
import com.mweb.jobportal.services.JobSeekerSaveService;
import com.mweb.jobportal.services.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class JobSeekerSaveController {
    private final UsersService usersService;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final JobPostActivityService jobPostActivityService;
    private final JobSeekerSaveService jobSeekerSaveService;

    public JobSeekerSaveController(UsersService usersService, JobSeekerProfileService jobSeekerProfileService, JobPostActivityService jobPostActivityService, JobSeekerSaveService jobSeekerSaveService) {
        this.usersService = usersService;
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.jobPostActivityService = jobPostActivityService;
        this.jobSeekerSaveService = jobSeekerSaveService;
    }

    @PostMapping("job-details/save/{id}")
    public String save(@PathVariable("id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.findByEmail(currentUsername);
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);

            if (seekerProfile.isPresent() && jobPostActivity != null) {
                JobSeekerSave newSave = new JobSeekerSave();
                newSave.setJob(jobPostActivity);
                newSave.setUserId(seekerProfile.get());

                jobSeekerSaveService.addNew(newSave);
            } else {
                throw new RuntimeException("User not found");
            }
        }
        return "redirect:/dashboard/";
    }


    @GetMapping("saved-jobs/")
    public String savedJobs(Model model){

        List<JobPostActivity> jobPost = new ArrayList<>();
        Object currentUserProfile =  usersService.getCurrentUserProfile();

        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidatesJob((JobSeekerProfile) currentUserProfile);
        for (JobSeekerSave jobSeekerSave: jobSeekerSaveList){
            jobPost.add(jobSeekerSave.getJob());

        }

        model.addAttribute("jobPost", jobPost);
        model.addAttribute("user", currentUserProfile);

        return "saved-jobs";

    }
}
