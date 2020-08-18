@Grab('com.sun.mail:javax.mail')
import java.time.LocalDateTime

import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

import groovy.cli.commons.CliBuilder

import static javax.mail.Message.RecipientType

/**
 * This script sends test email.  Use https://mailcatcher.me/ to create a test local SMTP server
 * to catch all emails.
 */
def cli = new CliBuilder(usage: 'send_test_email.groovy')
cli.t(longOpt: 'to', args: 1, defaultValue: 'recipient@example.com', 'To field of email, defaults to recipient@example.com')
cli.f(longOpt: 'from', args: 1, defaultValue: 'sender@example.com', 'From field of email, defaults to sender@example.com')
cli.h(longOpt: 'smtpHost', args: 1, defaultValue: 'localhost', 'SMTP server, defaults to localhost')
cli.p(longOpt: 'smtpPort', args: 1, defaultValue: '1025', 'SMTP port, defaults to 1025')

def options = cli.parse(args)

if (!options) {
  System.exit(1)
}

String to = options.t
String from = options.f
String host = options.h
String port = options.p

Properties properties = System.getProperties();
properties.setProperty("mail.smtp.host", host);
properties.setProperty("mail.smtp.port", port);
Session session = Session.getDefaultInstance(properties);
MimeMessage message = new MimeMessage(session);
message.setFrom(new InternetAddress(from));
message.addRecipient(RecipientType.TO, new InternetAddress(to));
message.setSubject("Test email ${LocalDateTime.now()}");
message.setText("Test message");
Transport.send(message);
