# TOMCAT SERVERS
role :webserver, "carra-core"

namespace :carra do
  desc "Push out shrine-config-test.xml"
  task :pushconfig do
    run "mkdir -p #{tomcat_home}conf/Catalina/localhost/"
    file = File.read("config/shrine-config-test.xml")
    put file, "#{tomcat_home}conf/Catalina/localhost/shrine-cell.xml"
  end
end