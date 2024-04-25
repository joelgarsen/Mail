

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class EscenarioDePartida.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class EscenarioDePartida
{
    private MailServer gmailServer;
    private MailClient mailClie1;
    private MailClient mailClie2;

    /**
     * Default constructor for test class EscenarioDePartida
     */
    public EscenarioDePartida()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        gmailServer = new MailServer();
        mailClie1 = new MailClient(gmailServer, "pepe@gmail.com");
        mailClie2 = new MailClient(gmailServer, "maria@gmail.com");
        mailClie1.sendMailItem("maria@gmail.com", "Hola María, soy Pepe!");
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}
