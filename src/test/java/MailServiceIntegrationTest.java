import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Test;
import service.*;

import javax.mail.MessagingException;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by roof on 11/18/15.
 */
public class MailServiceIntegrationTest {
    private static final int SMTP_TEST_PORT = 3025;
    public GreenMail greenMail;

    private String[] RECIPIENT = {"roof@odd-e.co.th", "roofimon@gmail.com"};
    public void sendEmailViaGoogleSMTP() {
        SMTPAccount smtpAccount = new SMTPAccount("massive.mail3r@gmail.com", "N0mif00rA", "smtp.gmail.com", 587);
        GmailSession gmailSession = new GmailSession(smtpAccount);
        LegcyEmailService mailService = new LegcyEmailService();
        mailService.setGmailSession(gmailSession);
        mailService.send(RECIPIENT);
    }
    @Test
    public void sendEmailViaGreenMailSMTP() {
        greenMail = new GreenMail(new ServerSetup(SMTP_TEST_PORT, null, "smtp"));
        greenMail.start();
        SMTPAccount smtpAccount = new SMTPAccount("fake@greenmail.com", "*******", "localhost", 3025);
        GmailSession gmailSession = new GmailSession(smtpAccount);
        LegcyEmailService mailService = new LegcyEmailService();
        mailService.setGmailSession(gmailSession);
        mailService.send(RECIPIENT);
        assertEquals("Your transaction is completed !!!", GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
        greenMail.stop();
    }

}
