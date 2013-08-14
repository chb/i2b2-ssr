# TOMCAT SERVERS
role :webserver, 
     "ibd-node-1-production",
     "ibd-node-2-production",
     "ibd-node-3-production"

namespace :carra do
  desc "Push out shrine-config-production.xml"
  task :pushconfig do
    run "mkdir -p #{tomcat_home}conf/Catalina/localhost/"
    file = File.read("config/shrine-config-production.xml")
    put file, "#{tomcat_home}conf/Catalina/localhost/shrine-cell.xml"
  end
end
