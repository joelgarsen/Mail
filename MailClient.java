import java.util.ArrayList;
public class MailClient
{
    // The server used for sending and receiving.
    private MailServer server;
    // The user running this client.
    private String user;
    
    private MailItem lastReceivedMail;
    
    private int mensajesEnviados;
    
    private int mensajesRecibidos;
    
    private String correoMasLargo;
    
    private int longitudMaxima;

    /**
     * Create a mail client run by user and attached to the given server.
     */
    public MailClient(MailServer server, String user)
    {
        this.server = server;
        this.user = user;
        this.lastReceivedMail = null;
        this.mensajesEnviados = 0;
        this.mensajesRecibidos = 0;
        this.correoMasLargo = "";
        this.longitudMaxima = 0;
    }
    
    public String getStatus() {
        String comprobacion = "";
        if (longitudMaxima != 0) {
            comprobacion = longitudMaxima +"";
        }
        return mensajesRecibidos + "," + mensajesEnviados + "," + correoMasLargo + "," + comprobacion;
    }

    /**
     * Return the next mail item (if any) for this user.
     */
    public MailItem getNextMailItem()
    {
        MailItem item = server.getNextMailItem(user);
        if (item == null) {
            return null;
        }
        String mensaje= item.getMessage();
        String asunto = item.getSubject();
        boolean spam = (mensaje.contains("loteria") || mensaje.contains("viagra")) && !asunto.contains(user);
        if (spam) {
            return null;
        } else {
            mensajesRecibidos++;
            if(mensaje.length() > longitudMaxima){
                longitudMaxima = mensaje.length();
                correoMasLargo = item.getFrom();
            }
        }
        lastReceivedMail = item;
        return item;
    }

    /**
     * Print the next mail item (if any) for this user to the text 
     * terminal.
     */
    public void printNextMailItem()
    {
        MailItem item = server.getNextMailItem(user);
        if(item == null) {
            System.out.println("No new mail.");
        }
        else {
            item.print();
        }
    }

    /**
     * Send the given message to the given recipient via
     * the attached mail server.
     * @param to The intended recipient.
     * @param message The text of the message to be sent.
     */
    public void sendMailItem(String to,String subject, String message)
    {
        MailItem item = new MailItem(user, to, subject, message);
        server.post(item);
        mensajesEnviados++;
    }
    
    public int getNumberOfMessageInServer() {
        return server.howManyMailItems(user);
    }
    
    public void receiveAndAutorespond() {
        MailItem receivedMail = server.getNextMailItem(user);
        if (receivedMail != null ) {
            lastReceivedMail = receivedMail;
            String sujetoOriginal = receivedMail.getSubject();
            String mensajeOriginal = receivedMail.getMessage();
            
            String mensajeRespuesta = "Gracias por su mensaje. Le contestare lo antes posible. " + mensajeOriginal;
            String asunto = "RE: " + sujetoOriginal;
            
            sendMailItem(receivedMail.getFrom(), asunto, mensajeRespuesta);
        }
    }
    
     public MailItem getLastReceivedMail() { 
        return lastReceivedMail;
    }
    
}
