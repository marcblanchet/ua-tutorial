package org.viagenie.ua.tutorial;


import com.ibm.icu.text.IDNA;
import com.ibm.icu.text.Normalizer2;
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

  public static void main(String[] args) {
    new Main().doMain(args);
  }

  protected void run() throws Exception {
    if (!email.matches(".*@+.*\\..*")) { // check if the email contains at least a '@' and a dot '.' before going deeper
      throw new Exception("Invalid email address, please review it and submit again");
    }

    // normalize localpart:
    String localpart = email.substring(0, email.lastIndexOf("@"));
    Normalizer2 normalizer2 = Normalizer2.getNFCInstance();
    String localpartNormalized = normalizer2.normalize(localpart);

    String domain = email.substring(email.lastIndexOf("@") + 1);
    StringBuilder output = new StringBuilder();

    IDNA validator = IDNA.getUTS46Instance(
        IDNA.NONTRANSITIONAL_TO_ASCII
            | IDNA.NONTRANSITIONAL_TO_UNICODE
            | IDNA.CHECK_BIDI
            | IDNA.CHECK_CONTEXTJ
            | IDNA.CHECK_CONTEXTO
            | IDNA.USE_STD3_RULES);
    IDNA.Info info = new IDNA.Info();

    validator.nameToASCII(domain, output, info);
    email = localpartNormalized + "@" + output.toString();

    if (isEmailValid(email)) {
      subscribe(email);
    } else {
      throw new Exception("Invalid email address, please review it and submit again");
    }
  }

  private static void subscribe(String email) throws Exception {
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

  public boolean isEmailValid(String email) {
    try {
      if (noValidation) {
        return true;
      }

      var iEmail = new InternetAddress(email);
      iEmail.validate();

      return true;
    } catch (AddressException e) {
      return false;
    }
  }
}
