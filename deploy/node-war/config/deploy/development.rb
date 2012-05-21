# TOMCAT SERVERS
role :webserver, "carra-test-1",
        "carra-test-2",
        "carra-test-3"


namespace :carra do
  desc "Push out shrine-config.xml"
  task :pushconfig do
    file = File.read("config/shrine-config-test.xml")
    put file, "#{tomcat_home}conf/Catalina/localhost/shrine-cell.xml"
  end
end