package com.quinbay.issues;

import com.quinbay.issues.service.IssueService;
import com.quinbay.issues.service.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SchedulerComponent {

    @Autowired
    IssueService issueService;

    @Scheduled(fixedRate = 6*60*60*1000) public void scheduleTask()
    {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        System.out.println(
                "Fixed rate Scheduler: Task running at - "
                        + strDate);

        issueService.checkForOverdues();
        issueService.checkForIncomplete();
    }
}
