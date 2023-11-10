import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

fun sendEmail(senderEmail: String, senderPassword: String, recipientEmail: String, subject: String, message: String) {
    val properties = Properties()
    properties["mail.smtp.auth"] = "true"
    properties["mail.smtp.host"] = "smtp.elasticemail.com"
    properties["mail.smtp.port"] = "2525"
    properties["mail.smtp.starttls.enable"] = "true"

    val session = Session.getInstance(properties, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(senderEmail, senderPassword)
        }
    })

    try {
        val mimeMessage = MimeMessage(session)
        mimeMessage.setFrom(InternetAddress(senderEmail))
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail))
        mimeMessage.subject = subject
        mimeMessage.setText(message)

        Transport.send(mimeMessage)
        println("Email sent successfully.")
    } catch (e: MessagingException) {
        e.printStackTrace()
    }
}

fun send() {
    val senderEmail = "yash092509@gmail.com" // Replace with your Elastic Email SMTP username
    val senderPassword = "72961037F4083B01DCFCCABAF083FFD9735C" // Replace with your Elastic Email SMTP password
    val recipientEmail = "21335@iiitu.ac.in"
    val subject = "Hello from Kotlin"
    val message = "This is a test email sent using Elastic Email SMTP"

    sendEmail(senderEmail, senderPassword, recipientEmail, subject, message)
}






