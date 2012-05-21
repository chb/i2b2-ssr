#!/usr/bin/ruby
require 'net/smtp'

@@mail_server = 'tummms.chboston.org'
@@receipients = ['justin.quan@childrens.harvard.edu','david.ortiz@childrens.harvard.edu']

def email(msg)
  email_content = <<EOM
From: healthchecker@carranet.net
To: #{@@receipients.join(',')}
Subject: [Carra Monitor] ImportPipeline Healthcheck alert!!!!

#{msg}
EOM

  Net::SMTP.start(@@mail_server) do |smtp|
    smtp.send_message email_content, "justin.quan@childrens.harvard.edu", @@receipients
  end
end

email("importpipeline exited")
