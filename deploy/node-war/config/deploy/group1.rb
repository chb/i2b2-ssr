# TOMCAT SERVERS
role :webserver, "carra-node-production",
     "carra-node-2-production",
     "carra-node-3-production",
     "carra-node-4-production",
     "carra-node-5-production",
     "carra-node-6-production",
     "carra-node-7-production",
     "carra-node-8-production",
     "carra-node-9-production",
     "carra-node-10-production",
     "carra-node-11-production",
     "carra-node-12-production",
     "carra-node-13-production",
     "carra-node-14-production",
     "carra-node-15-production",
     "carra-node-16-production",
     "carra-node-17-production",
     "carra-node-18-production",
     "carra-node-19-production",
     "carra-node-20-production"

namespace :carra do
  desc "Push out shrine-config-production.xml"
  task :pushconfig do
    run "mkdir -p #{tomcat_home}conf/Catalina/localhost/"
    file = File.read("config/shrine-config-production.xml")
    put file, "#{tomcat_home}conf/Catalina/localhost/shrine-cell.xml"
  end
end
