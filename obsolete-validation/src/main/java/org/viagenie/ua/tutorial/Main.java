package org.viagenie.ua.tutorial;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.icann.ua.TestRunner;

public class Main extends TestRunner {

  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  public static void main(String[] args) {
    new Main().doMain(args);
  }

  public void run() throws Exception {
    if (isEmailValid()) {
      subscribe();
    } else {
      throw new Exception("Invalid email address, please review it and submit again");
    }
  }

  private void subscribe() throws Exception {
    Properties props = System.getProperties();

    props.put("mail.smtp.host", "localhost");
    props.put("mail.mime.allowutf8", true);
    props.put("mail.smtp.port", 1025);

    Session session = Session.getInstance(props, null);

    MimeMessage msg = new MimeMessage(session);
    //set message headers
    msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
    msg.addHeader("format", "flowed");
    msg.addHeader("Content-Transfer-Encoding", "8bit");

    msg.setFrom(new InternetAddress("testing-java@eai.com", "NoReply-JD"));

    msg.setReplyTo(InternetAddress.parse("testing-java@eai.com", false));

    msg.setSubject("X.com subscription!", "UTF-8");

    msg.setText("Congratulation, you are subscribed to X.com!", "UTF-8");

    msg.setSentDate(new Date());

    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
    Transport.send(msg);

    System.out.println("Email sent successfully!!");
  }

  public boolean isEmailValid() {
    try {
      if (noValidation) {
        return true;
      }

      Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
      return matcher.find();
    } catch (Exception e) {
      return false;
    }
  }
}
