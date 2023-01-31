package com.quinbay.issues.service;

import com.quinbay.issues.api.MailingAPI;
import com.quinbay.issues.model.EmailDetails;
import com.quinbay.issues.model.Issues;
import com.quinbay.issues.model.ReturnData;
import com.quinbay.issues.model.Users;
import com.quinbay.issues.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class MailingService implements MailingAPI {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    IssueRepository issueRepository;

    @Value("${spring.mail.username}") String sender;

    public ReturnData getUserDetails(String userId) {
        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/viewByUserId")
                .queryParam("userId", userId)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ReturnData s = restTemplate().exchange(url, HttpMethod.GET, entity, ReturnData.class).getBody();
        return s;
    }

    public String msgForOverdue(Issues issues, Users users) {
        String message;
        message = "Hello user,\n" +
                "\n" +
                "It is seen that the issue with Issue ID #" + issues.getIssueId() + " - " + issues.getCategory() + " - " + issues.getDescription() + " - posted by user #" + users.getUserId() + " - " + users.getUserName() + " on " + issues.getCreatedDate() + " with an SLA " + issues.getSla() + " hours has OVERDUED. Kindly look into it at the earliest.\n" +
                "\n" +
                "Thank you.";
        return message;
    }

    public String msgForImmediate(Issues issues, Users users) {
        String message;
        message = "Hello Admin,\n" +
                "\n" +
                "There is an IMMEDIATE request for the issue with Issue ID #" + issues.getIssueId() + " - " + issues.getCategory() + " - " + issues.getDescription() + " - posted by user #" + users.getUserId() + " - " + users.getUserName() + " on " + issues.getCreatedDate() + " with an SLA " + issues.getSla() + " hours. Kindly look into it at the earliest.\n" +
                "\n" +
                "Thank you.";
        return message;
    }

    public String msgForCloseRequest(Issues issues, Users users) {
        String message;
        message = "Hello User,\n" +
                "\n" +
                "We see that the issue with Issue ID #" + issues.getIssueId() + " - " + issues.getCategory() + " - " + issues.getDescription() + " - posted by you on " + issues.getCreatedDate() + " with an SLA " + issues.getSla() + " hours. Kindly close the request from your side..\n" +
                "\n" +
                "Thank you.";
        return message;
    }

    public String mailForOverdue(String issueId, String userId) {

        EmailDetails details = new EmailDetails();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

//        String assigneeEmail = getUserDetails(userId).getUserData().getEmail();
//        String adminEmail = "vasunthrat@gmail.com";
//        String adminManagerEmail = getUserDetails(getUserDetails(userId).getUserData().getRepManagerId()).getUserData().getEmail();

        String assigneeEmail = "dhanushpraveen150@gmail.com";
        String adminEmail = "vasunthrat@gmail.com";
        String adminManagerEmail = "hari21032001@gmail.com";

        Issues issueDet = issueRepository.findByIssueId(issueId);
        Users assigneeDet = getUserDetails(userId).getUserData();
        Users adminManagerDet = getUserDetails(assigneeDet.getRepManagerId()).getUserData();

        msgForOverdue(issueDet, assigneeDet);

        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(assigneeEmail);
        recipients.add(adminEmail);
        recipients.add(adminManagerEmail);

        System.out.println(assigneeEmail);

        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(adminEmail);
            mimeMessageHelper.addCc(assigneeEmail);
            mimeMessageHelper.addCc(adminManagerEmail);
            mimeMessageHelper.setText(msgForOverdue(issueDet, assigneeDet));
            mimeMessageHelper.setSubject("Issue Overdue - Reg.");

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }

    public String mailForImmediates(String issueId, String userId) {

        EmailDetails details = new EmailDetails();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        String adminEmail = "vasunthrat@gmail.com";

        Issues issueDet = issueRepository.findByIssueId(issueId);
        Users assigneeDet = getUserDetails(userId).getUserData();

        msgForImmediate(issueDet, assigneeDet);

        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(adminEmail);
            mimeMessageHelper.setText(msgForImmediate(issueDet, assigneeDet));
            mimeMessageHelper.setSubject("IMMEDIATE Issue - Reg.");

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }

    public String mailForCloseRequest(String issueId) {

        EmailDetails details = new EmailDetails();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        String adminEmail = "vasunthrat@gmail.com";

        Issues issueDet = issueRepository.findByIssueId(issueId);
        Users assigneeDet = getUserDetails(issueDet.getAssigneeUserId()).getUserData();

        msgForCloseRequest(issueDet, assigneeDet);

        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(adminEmail);
            mimeMessageHelper.setText(msgForCloseRequest(issueDet, assigneeDet));
            mimeMessageHelper.setSubject("Request to Close Issue - Reg.");

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }
}
