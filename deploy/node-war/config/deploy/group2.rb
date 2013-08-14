# TOMCAT SERVERS
role :webserver, 
     "carra-node-21-production",
     "carra-node-22-production",
     "carra-node-23-production",
     "carra-node-24-production",
     "carra-node-25-production",
     "carra-node-26-production",
     "carra-node-27-production",
     "carra-node-28-production",
     "carra-node-29-production",
     "carra-node-30-production",
     "carra-node-31-production",
     "carra-node-32-production",
     "carra-node-33-production",
     "carra-node-34-production",
     "carra-node-35-production",
     "carra-node-36-production",
     "carra-node-37-production",
     "carra-node-38-production",
     #"carra-node-39-production",
     "carra-node-40-production"

namespace :carra do
  desc "Push out shrine-config-production.xml"
  task :pushconfig do
    run "mkdir -p #{tomcat_home}conf/Catalina/localhost/"
    file = File.read("config/shrine-config-production.xml")
    put file, "#{tomcat_home}conf/Catalina/localhost/shrine-cell.xml"
  end
end
