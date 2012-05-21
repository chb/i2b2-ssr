#!/usr/bin/ruby
require 'net/https'
require 'net/smtp'

@@mail_server = 'tummms.chboston.org'
@@receipients = ['david.ortiz@childrens.harvard.edu'];
@@prod_services = ['http://carra-core-production:8183/studies/', # ols
                   'http://carra-core-production:8182/healthcheck', # auth
                   'https://carra-core-production:8081/shrine-cell/soap/aggregate?wsdl', # broadcaster
                   'https://carra-core-production:8443/', # admin ui
                   'http://carra-webclient:8080/login.jsf', # cchmc client
                   'https://carra-proxy-1/login.jsf', # proxy
                   'https://i2b2ssr.tch.harvard.edu/login.jsf', # cname to proxy
                   'http://carra-i2b2-production:8080/ont/healthcheck' # ONT

]


@@prod_node = (1..60).map { |x|
  x == 1 ?
          "https://carra-node-production:8081/shrine-cell/soap/aggregate?wsdl" :
          x == 39 ? nil : "https://carra-node-#{x}-production:8081/shrine-cell/soap/aggregate?wsdl"
}.delete_if {|x| x == nil}

# TODO: add retries with exp backoff (when it gets annoying)
def healthcheck(uri)
  msg = ""
  begin
    req = Net::HTTP::Get.new(uri.request_uri)
    httpSession = Net::HTTP.new(uri.host, uri.port)
    httpSession.use_ssl=true if uri.scheme.eql?('https')
    res = httpSession.start { |http|
      http.request(req);
    }
    case res
      when Net::HTTPSuccess then # noop
      else msg += "non 2xx response from url #{uri}: #{res.code}\n"
    end
  rescue Exception => e
    msg += "problem checking url #{uri}: #{e}"
  end
  if not msg.empty?
    email(msg)
  end
end

def email(msg)
  email_content = <<EOM
From: healthchecker@carranet.net
To: #{@@receipients.join(',')}
Subject: [Carra Monitor] HTTP Healthcheck alert!!!!

#{msg}
EOM

  Net::SMTP.start(@@mail_server) do |smtp|
    smtp.send_message email_content, "justin.quan@childrens.harvard.edu", @@receipients
  end
end

[@@prod_node, @@prod_services].each { |urls|
  urls.each { |url|
    healthcheck(URI.parse(url))
  }
}


