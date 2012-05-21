#!/usr/bin/ruby
#capistrano multistage
require 'capistrano/ext/multistage'
set :default_stage, "development"
set :stages, %w(       production development       )

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

set :tomcat_home, "/home/carra/tomcat/"
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
  "~/u/apps/#{application}"
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
    run "nohup #{tomcat_home}bin/shutdown.sh"
  end

  desc "await tomcat shutdown"

  desc "stop and start tomcat"
  task :restart do
    tomcat.stop
    sleep 2
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
    run "nohup #{i2b2_home}bin/run.sh &"
  end

  desc "shutdown i2b2"
  task :shutdown do
    run "#{i2b2_home}bin/shutdown.sh -S"
  end

  desc "clear i2b2 logs"
  task :clearlogs do
    run "rm #{i2b2_home}server/default/log/*"
  end

end


#
# link the current/whatever.war into our webapps/whatever.war
#
before 'deploy:symlink' do
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

#  task :cold do
#    puts "install tomcat"
#    run("wget http://www.fightrice.com/mirrors/apache/tomcat/tomcat-6/v6.0.32/bin/apache-tomcat-6.0.32.tar.gz")
#    run("tar -xzvf apache-tomcat-6.0.32.tar.gz")
#    run("ln -s apache-tomcat-6.0.32 tomcat")
#    # setup conf/server.xml (add connector, add deployOnStartup="true")
#    # setup lib/keystore.xml
#
#  end

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

  desc "Push out shrine-config.xml"
  task :pushconfig do
    run "mkdir -p #{tomcat_home}conf/Catalina/localhost/"
    file = File.read("config/shrine-config.xml")
    put file, "#{tomcat_home}conf/Catalina/localhost/shrine-cell.xml"
  end

  desc "push out jdbc driver jar"
  task :push_jdbc_driver do
    cmd = "cp #{deploy_to}/current/`basename #{jdbc_driver_jar}` #{tomcat_home}/lib/"
    puts cmd
    run cmd
  end

  desc "push out keystore.xml"
  task :push_keystore_config do
    keystore_path = "#{tomcat_home}/lib/keystore.xml"
    if not remote_file_exists?(keystore_path)
      upload("config/keystore.xml", keystore_path)
    end
  end

  desc 'delete the shrine database'
  task :restetshrinedb do
    run("rm -rf /home/carra/.spin/db/db/derby/shrine/")

  end

end

def remote_file_exists?(full_path)
  'true' ==  capture("if [ -e #{full_path} ]; then echo 'true'; fi").strip
end