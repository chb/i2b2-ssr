#!/usr/bin/ruby
require 'net/https'
require 'net/smtp'
require 'socket'

@@mail_server = 'tummms.chboston.org'
@@receipients = ['justin.quan@childrens.harvard.edu', 'david.ortiz@childrens.harvard.edu']
@@urls_to_check = ['http://localhost:9090/i2b2/services/listServices']

# TODO: add retries with exp backoff (when it gets annoying)
def healthcheck(uri)
  begin
    req = Net::HTTP::Get.new(uri.request_uri)
    httpSession = Net::HTTP.new(uri.host, uri.port)
    httpSession.use_ssl=true if uri.scheme.eql?('https')
    res = httpSession.start { |http|
      http.request(req);
    }
    case res
      when Net::HTTPSuccess then # noop
      else email("non 2xx response from url #{uri}: #{res.code}")
    end
  rescue Exception => e
    email("problem checking url #{uri}: #{e}")
  end
end

def email(msg)
  email_content = <<EOM
From: healthchecker@carranet.net
To: #{@@receipients.join(',')}
Subject: [Carra Monitor] HTTP Healthcheck alert!!!!

#{Socket.gethostname}: #{msg}
EOM

  Net::SMTP.start(@@mail_server) do |smtp|
    smtp.send_message email_content, "justin.quan@childrens.harvard.edu", @@receipients
  end
end

[@@urls_to_check].each { |urls|
  urls.each { |url|
    healthcheck(URI.parse(url))
  }
}


