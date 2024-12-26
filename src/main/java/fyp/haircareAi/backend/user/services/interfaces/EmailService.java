package fyp.haircareAi.backend.user.services.interfaces;

public interface EmailService {
    public boolean sendEnail(String to,String subject,String body);
}
