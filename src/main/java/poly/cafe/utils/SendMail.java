package poly.cafe.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class SendMail {

    // TODO: Chuyển sang biến môi trường hoặc file config để bảo mật hơn
    private static final String FROM_EMAIL = "khoa1662008@gmail.com";
    private static final String PASSWORD = "iwlwssfwmgbyyxix";

    public static boolean sendEmail(String toEmail, String subject, String content) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, "PolyCafe System"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sendWelcomeEmail(String toEmail, String fullname) {
        String subject = "Chào mừng bạn đến với PolyCafe!";
        String content = "Xin chào " + fullname + ",\n\n"
                + "Cảm ơn bạn đã đăng ký tài khoản tại hệ thống PolyCafe.\n"
                + "Tài khoản của bạn đã được tạo thành công.\n\n"
                + "Trân trọng,\nĐội ngũ PolyCafe";
        sendEmail(toEmail, subject, content);
    }

    public static void sendPasswordEmail(String toEmail, String newPassword) {
        String subject = "Yêu cầu cấp lại mật khẩu - PolyCafe";
        String content = "Xin chào,\n\n"
                + "Hệ thống đã tự động reset mật khẩu của bạn.\n"
                + "Mật khẩu mới của bạn là: " + newPassword + "\n\n"
                + "Vui lòng đăng nhập và đổi lại mật khẩu ngay.\n\n"
                + "Trân trọng,\nĐội ngũ PolyCafe";
        sendEmail(toEmail, subject, content);
    }
}