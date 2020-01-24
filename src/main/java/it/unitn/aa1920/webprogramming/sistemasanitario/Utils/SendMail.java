package it.unitn.aa1920.webprogramming.sistemasanitario.Utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail extends Thread {

    public enum SendMailType {
        ESAME,
        RICETTA
    }

    private SendMailType type;
    private String receiver;

    public SendMail(SendMailType type, String receiver) {
        this.type = type;
        this.receiver = receiver;
    }

    @Override
    public void run() {
        String host = "smtp.gmail.com";
        String port = "587";
        final String username = "SenderProgWeb@gmail.com";
        final String password = "tenz hqrq fvlp zysd";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiver)
            );
            if (type == SendMailType.ESAME) {
                message.setSubject("Nuovo esame prescritto");
                message.setText("\nE' stato prescritto un nuovo esame per te!\nVisualizzalo sul sito");
            } else if (type ==  SendMailType.RICETTA) {
                message.setSubject("Nuova ricetta prescritta");
                message.setText("\nC'è una nuova ricetta da visualizzare per te!\nVisualizzala sul sito");
            }

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}