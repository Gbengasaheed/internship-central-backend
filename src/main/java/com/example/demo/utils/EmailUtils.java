package com.example.demo.utils;

import com.example.demo.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
@RequiredArgsConstructor
@Component
public class EmailUtils {
    private final JavaMailSender mailSender;

    public void sendWelcomeEmail(String userName, String confirmationUrl, String userEmail) throws MessagingException {
        String subject = "Welcome to Internship Central";
        String messageContent = generateWelcomeHtmlContent(userName,confirmationUrl);
        sendEmail(subject,userEmail,messageContent);
    }

    public void sendMessageEmail(User intern, User recruiter, String message) throws MessagingException {
        String subject = "Attention: You have a new message from an Intern: "+intern.getFirstName();
        String messageContent = sendChatContent(intern, recruiter, message);
        sendEmail(subject,recruiter.getEmail(),messageContent);
    }

    private void sendEmail(String subject, String receiverEmail, String messageContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("internshipcentraluk@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, receiverEmail);
        message.setSubject(subject);
        message.setContent(messageContent, "text/html; charset=utf-8");

        mailSender.send(message);
    }



    private String sendChatContent(User intern, User recruiter, String message) {
        String cssStyles = "<style>\n" +
                "    body { font-family: Arial, sans-serif; background-color: #f2f2f2; }\n" +
                "    .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border: 2px solid #ffa500; }\n" +
                "    h1 { color: #ffa500; font-size: 24px; margin-bottom: 20px; }\n" +
                "    p { color: #000000; font-size: 16px; line-height: 1.5; }\n" +
                "    a { color: #ffa500; text-decoration: none; }\n" +
                "    a:hover { text-decoration: underline; }\n" +
                "</style>";

        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                cssStyles +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1 style=\"border-bottom: 2px solid #ffa500; padding-bottom: 10px;\">Hello, " + recruiter.getFirstName() + "!</h1>\n" +
                "        <p>You have a message from " + intern.getFirstName() + " whose email is " + intern.getEmail() + "</p>\n" +
                "        <p>" + message + "</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

    private String generateWelcomeHtmlContent(String userName, String confirmationUrl) {

        String cssStyles = "<style>\n" +
                "    body { font-family: Arial, sans-serif; background-color: #f2f2f2; }\n" +
                "    .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border: 2px solid #ffa500; }\n" +
                "    h1 { color: #ffa500; font-size: 24px; margin-bottom: 20px; }\n" +
                "    p { color: #000000; font-size: 16px; line-height: 1.5; }\n" +
                "    a { color: #ffa500; text-decoration: none; }\n" +
                "    a:hover { text-decoration: underline; }\n" +
                "</style>";

        // HTML content for the email
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                cssStyles +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1 style=\"border-bottom: 2px solid #ffa500; padding-bottom: 10px;\">Hello, " + userName + "!</h1>\n" +
                "        <p>Welcome to Internship Central! We are thrilled to have you join our community of aspiring interns and dedicated employers. Our platform connects talented students with exciting internship opportunities in various industries across the United Kingdom.</p>\n" +
                "        <p>Internship Central's user-friendly interface makes it easy to find and apply for internships that align with your interests and aspirations. Additionally, our rich repository of career resources will empower recruiters to add internship openings.</p>\n" +
                "        <p>" + confirmationUrl + "</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

}
