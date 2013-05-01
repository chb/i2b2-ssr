# TOMCAT SERVERS
role :webserver, "ibd-core-production"

namespace :carra do

  desc "Push out shrine-config-ibd.xml"
  task :pushconfig do
    run "mkdir -p #{tomcat_home}conf/Catalina/localhost/"
    file = File.read("config/shrine-config-ibd.xml")
    put file, "#{tomcat_home}conf/Catalina/localhost/shrine-cell.xml"
  end
end