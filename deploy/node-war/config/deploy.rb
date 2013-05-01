#!/usr/bin/
#capistrano multistage
require 'capistrano/ext/multistage'
set :default_stage, "development"
set :stages, %w(       production staging development group1 group2 group3 ibd     )

load 'deploy'

set :application, "carranet"

default_run_options[:pty] = true


# DEPLOYMENT SCHEME
set :scm, :none
set :deploy_via, :copy
set :repository do
  fetch(:deploy_from)
end


# LOCAL
set :war, "target/shrine-cell.war"
set :jdbc_driver_jar, "target/shrine-cell/WEB-INF/lib/h2-*"

set :tomcat_home, "/home/carra/shrine-dist-1.7-SNAPSHOT/tomcat/"
set :i2b2_home, "/home/carra/jboss-4.2.2.GA/"


# USER / SHELL
set :user, "carra" # the user to run remote commands as
set :use_sudo, false

set :deploy_from do
  dir = "/tmp/prep_#{release_name}"
  system("mkdir -p #{dir}")
  dir
end

# this is capistrano's default location.
# depending on the permissions of the server
# you may need to create it and chown it
set :deploy_to do
  "/u/apps/#{application}"
end

#
# simple interactions with the tomcat server
#
namespace :tomcat do

  desc "start tomcat"
  task :start do
    run "nohup #{tomcat_home}bin/startup.sh"
  end

  desc "stop tomcat"
  task :stop do
    run "#{tomcat_home}bin/shutdown.sh"
  end

  desc "stop and start tomcat"
  task :restart do
    tomcat.stop
    sleep 10
    carra.pushconfig
    tomcat.start
  end

  desc "tail :tomcat_home/logs/*.log and logs/catalina.out"
  task :tail do
    stream "tail -f #{tomcat_home}/logs/*.log #{tomcat_home}/logs/catalina.out"
  end
end

namespace :i2b2 do
  desc "startup i2b2"
  task :startup do
    run  "nohup #{i2b2_home}bin/run.sh"
  end

  desc "shutdown i2b2"
  task :shutdown do
    run "#{i2b2_home}bin/shutdown.sh -S"
  end

  desc "clear i2b2 logs"
  task :clearlogs do
    run "rm #{i2b2_home}/server/default/log/*"
  end

end


#
# link the current/whatever.war into our webapps/whatever.war
#
after 'deploy:setup' do
  cmd = "ln -sf #{deploy_to}/current/`basename #{war}` #{tomcat_home}/webapps/`basename #{war}`"
  puts cmd
  run cmd

end


# collect up our war into the deploy_from folder
# notice that all we're doing is a copy here,
# so it is pretty easy to swap this out for
# a wget command, which makes sense if you're
# using a continuous integration server like
# bamboo. (more on this later).
before 'deploy:update_code' do
  unless (war.nil?)
    puts "get war"
    puts system("cp #{war} #{deploy_from}")
  end
  unless (jdbc_driver_jar.nil?)
    puts "get jdbc driver jar"
    puts system("cp #{jdbc_driver_jar} #{deploy_from}")
  end
  puts system("ls -l #{deploy_from}")
end

# restart tomcat
namespace :deploy do
  task :restart do
    tomcat.stop
    sleep 2
    run "rm -rf #{tomcat_home}/webapps/shrine-cell/"
    carra.pushconfig
    carra.push_jdbc_driver
    tomcat.start
  end
end

#
# Disable all the default tasks that
# either don't apply, or I haven't made work.
#
namespace :deploy do
  [:upload, :cold, :start, :stop, :migrate, :migrations, :finalize_update].each do |default_task|
    desc "[internal] disabled"
    task default_task do
      # disabled
    end
  end

  namespace :web do
    [:disable, :enable].each do |default_task|
      desc "[internal] disabled"
      task default_task do
        # disabled
      end
    end
  end

  namespace :pending do
    [:default, :diff].each do |default_task|
      desc "[internal] disabled"
      task default_task do
        # disabled
      end
    end
  end


end

namespace :carra do
  desc "push out jdbc driver jar"
  task :push_jdbc_driver do
    cmd = "cp #{deploy_to}/current/`basename #{jdbc_driver_jar}` #{tomcat_home}/lib/"
    puts cmd
    run cmd
  end

  desc "update catalina.sh"
  task :update_catalina do
    file = File.read("config/catalina.sh")
    put file, "/home/carra/shrine/tomcat/bin/catalina.sh"
  end

  desc "Destroy all processes that look like JAVA on the box, only use as a last resort"
  task :kill_every_pid do
	run "pkill java"
  end

  desc "Update the keystore.xml, these should all be the same anyways"
  task :updates_spin_keystore_config do
    file = File.read("config/keystore.xml")
    put file, "/home/carra/.spin/conf/keystore.xml"
  end

  desc 'delete the shrine database'
  task :restetshrinedb do
    run("rm -rf /home/carra/db/")

  end

  desc "get installed verions of stuff"
  task :getversions do
    run "ls -l /u/apps/carranet/"
  end

  desc "Task to get uptime"
  task :uptime do
    run "cat /proc/meminfo"
  end
end
