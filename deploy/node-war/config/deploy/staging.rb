# TOMCAT SERVERS
role :webserver, "carra-node-1-staging",
        "carra-node-2-staging"


namespace :carra do
  desc "Push out shrine-config.xml"
  task :pushconfig do
    file = File.read("config/shrine-config-test.xml")
    put file, "#{tomcat_home}conf/Catalina/localhost/shrine-cell.xml"
  end
end
