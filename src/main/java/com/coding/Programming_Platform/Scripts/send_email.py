import smtplib
import sys
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

def send_email(recipient, subject, body):
    sender_email = "perumpatayans@gmail.com"
    sender_password = "lvxw mskz zufs adit"

    msg = MIMEMultipart()
    msg["From"] = sender_email
    msg["To"] = recipient
    msg["Subject"] = subject
    msg.attach(MIMEText(body, "plain"))

    try:
        server = smtplib.SMTP("smtp.gmail.com", 587)
        server.starttls()
        server.login(sender_email, sender_password)
        server.sendmail(sender_email, recipient, msg.as_string())
        server.quit()
        print("Email sent successfully")
    except Exception as e:
        print("Error:", str(e))

if __name__ == "__main__":
    if len(sys.argv) != 4:
        print("Usage: python,send_email.py,recipient,subject,body")
    else:
        send_email(sys.argv[1], sys.argv[2], sys.argv[3])
