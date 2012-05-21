#!/usr/bin/env ruby
#Stupid script to setup classpaths/configs and all that stuff so running
#the carranet core services becomes a pen click operation

require 'rubygems'
require 'FileUtils'


$auth_jar = "lib/AuthServiceCore-1.1-SNAPSHOT.jar"
$auth_main_class = "edu.chip.carranet.auth.AuthApplication"

$ols_jar =  "lib/OverlayWebservice-1.1-SNAPSHOT.jar"
$ols_main_class = "edu.chip.carranet.overlay.OverlayMain"


def run_auth()
  fork do
    fork do
      exec_string = "java -cp conf/auth:" + $auth_jar +  " -server " + $auth_main_class
      system(exec_string)
    end
  end


end

def run_ols()
  fork do
    fork do
      exec_string = "java -cp conf/ols:" + $ols_jar +  " -server " + $ols_main_class
      system(exec_string)
    end
  end
end



run_auth
run_ols
