#!/bin/ruby

require 'rubygems'
require 'oci8'
require 'machine_loader'

require 'erb'

class CarraMachine
	attr_accessor :address, :port, :username, :password, :sid

	def initialize(address, port, username, password, sid)
		@address = address
		@port = port
		@username = username
		@password = password
		@sid = sid
	end

	def oracle_connection_string
		"#{@address}:#{@port}/#{@sid}"
	end

	def sql_plus_connection_string
		#<username>[/<password>][@<connect_identifier>
		"#{@username}/#{@password}@#{@address}:#{@port}/#{@sid}"
	end

 	def get_binding
      binding
    end
	
end

machine_list = Array.new

for i in 1..60 do
  machine_list.push(CarraMachine.new("chdev1", "1521", "rc_carra_vm#{i}_project1", "chracdev","CHRACDEV"))
end


for i in machine_list
	machine_loader = MachineLoader.new(i)
	puts "Kicking off loader"
	machine_loader.load_machine
end

