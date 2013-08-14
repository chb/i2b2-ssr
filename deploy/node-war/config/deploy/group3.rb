# TOMCAT SERVERS
role :webserver,
     "carra-node-41-production",
     "carra-node-42-production",
     "carra-node-43-production",
     "carra-node-44-production",
     "carra-node-45-production",
     "carra-node-46-production",
     "carra-node-47-production",
     "carra-node-48-production",
     "carra-node-49-production",
     "carra-node-50-production",
     "carra-node-51-production",
     "carra-node-52-production",
     "carra-node-53-production",
     "carra-node-54-production",
     "carra-node-55-production",
     "carra-node-56-production",
     "carra-node-57-production",
     "carra-node-58-production",
     "carra-node-59-production",
     "carra-node-60-production"

namespace :carra do
  desc "Push out shrine-config-production.xml"
  task :pushconfig do
    run "mkdir -p #{tomcat_home}conf/Catalina/localhost/"
    file = File.read("config/shrine-config-production.xml")
    put file, "#{tomcat_home}conf/Catalina/localhost/shrine-cell.xml"
  end
end
