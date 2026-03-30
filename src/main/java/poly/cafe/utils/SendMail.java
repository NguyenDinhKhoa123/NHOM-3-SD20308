package poly.cafe.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class SendMail {
    // Tài khoản gửi mail (App Password)
    private static final String FROM_EMAIL = "khoa1662008@gmail.com";
    private static final String PASSWORD = "iwlwssfwmgbyyxix";

    public static boolean sendEmail(String toEmail, String subject, String content) {
        try {
            // Cấu hình mail server
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // Tạo session với authenticator
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                }
            });

            // Tạo message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, "PolyCafe System")); // Thêm tên người gửi
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(content);

            // Gửi email
            Transport.send(message);
            System.out.println("Email sent successfully to: " + toEmail);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Gửi email chào mừng khi đăng ký
    public static void sendWelcomeEmail(String toEmail, String fullname) {
        String subject = "Chào mừng bạn đến với PolyCafe!";
        String content = "Xin chào " + fullname + ",\n\n"
                + "Cảm ơn bạn đã đăng ký tài khoản tại hệ thống PolyCafe.\n"
                + "Tài khoản của bạn đã được tạo thành công. Bây giờ bạn có thể trải nghiệm đặt món và nhận nhiều ưu đãi.\n\n"
                + "Trân trọng,\n"
                + "Đội ngũ PolyCafe";
        sendEmail(toEmail, subject, content);
    }

// Gửi email khi quên mật khẩu (Cấp lại mật khẩu)
    public static void sendPasswordEmail(String toEmail, String newPassword) {
        String subject = "Yêu cầu cấp lại mật khẩu - PolyCafe";
        String content = "Xin chào,\n\n"
                + "Hệ thống đã tự động reset mật khẩu của bạn.\n"
                + "Mật khẩu mới của bạn là: " + newPassword + "\n\n"
                + "Vui lòng đăng nhập và đổi lại mật khẩu ngay để bảo đảm an toàn.\n\n"
                + "Trân trọng,\n"
                + "Đội ngũ PolyCafe";
        sendEmail(toEmail, subject, content);
    }
}