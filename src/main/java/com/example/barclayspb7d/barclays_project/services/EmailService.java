package com.example.barclayspb7d.barclays_project.services;

import com.example.barclayspb7d.barclays_project.entities.EmailDetails;

// Interface
public interface EmailService {
 
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);
 
    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}