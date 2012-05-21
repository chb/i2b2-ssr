# TOMCAT SERVERS
role :webserver, "carra-core-production"

namespace :carra do

  desc "Push out shrine-config-production.xml"
  task :pushconfig do
    run "mkdir -p #{tomcat_home}conf/Catalina/localhost/"
    file = File.read("config/shrine-config-production.xml")
    put file, "#{tomcat_home}conf/Catalina/localhost/shrine-cell.xml"
  end
end