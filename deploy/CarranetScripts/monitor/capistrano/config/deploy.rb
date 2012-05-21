#!/usr/bin/ruby
#capistrano multistage
require 'capistrano/ext/multistage'
set :default_stage, "development"
set :stages, %w(       production development       )

load 'deploy'

set :application, "monitoring"

default_run_options[:pty] = true

# DEPLOYMENT SCHEME
set :scm, :none
set :deploy_via, :copy
set :repository do
  fetch(:deploy_from)
end


# LOCAL
set :jboss_checker, "../jboss_checker.rb"
set :jboss_cron, "../cron/jboss_cron"

# REMOTE
set :monitoring_dir, "/home/carra/monitoring"
set :cron_dir, "/etc/cron.d"

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
# link the current/whatever to deployment_path/whatever
#
before 'deploy:symlink' do
  monitoring.setup
  monitoring.install
end


# collect up our war into the deploy_from folder
# notice that all we're doing is a copy here,
# so it is pretty easy to swap this out for
# a wget command, which makes sense if you're
# using a continuous integration server like
# bamboo. (more on this later).
before 'deploy:update_code' do
  monitoring.deploy
end

namespace :monitoring do
  task :setup do
    run "mkdir -p #{monitoring_dir}"
    setup_jboss
  end

  task :setup_jboss, :roles => :jboss do
    # TODO: this is kind of crazy to run every time, but i don't like the 'cold' task as it doesn't match well
    run "#{sudo} apt-get install -y libopenssl-ruby"
  end

  task :deploy do
    deploy_jboss
    deploy_core
    puts "deploy folder contents:"
    system("ls -l #{deploy_from}")
  end

  task :deploy_jboss, :roles => :jboss do
    deploy_if_exists(jboss_checker)
    deploy_if_exists(jboss_cron)
  end

  task :deploy_core, :roles => :core do
    run "echo noop"
  end

  task :install do
    install_jboss
  end

  task :install_jboss, :roles => :jboss do
    run "#{sudo} chown --dereference root:root #{release_path}/`basename #{jboss_cron}`"
    create_symlink(jboss_checker, monitoring_dir)
    create_symlink(jboss_cron, cron_dir, true)
  end
end



def create_symlink(app, dst, use_sudo=false)
  cmd = "ln -sf #{deploy_to}/current/`basename #{app}` #{dst}/`basename #{app}`"
  if use_sudo then
    cmd = "#{sudo} " + cmd
  end

  run cmd
end

def deploy_if_exists(filename)
  if(File.exists?(filename))
    cmd = "cp #{filename} #{deploy_from}"
    puts cmd
    system cmd
  else
    puts "ERROR: You tried to deploy a file that does not exist: #{filename}"
  end
end


namespace :deploy do
  task :restart do
    # noop
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