#!/usr/bin/ruby -w

require 'optparse'
require 'net/http'
require 'uri'
require 'ftools'


class PeerGroupFetcher

  @@debug = true

  def initialize(arguments)
    @lastFile = ""
    @options = {
            :ols_uri => 'http://localhost:8183',
            :peergroup_path => '/peergroups/',
            :output_file => 'test.xml'
    }


    pts = OptionParser.new do |opts|
      opts.on('-f', '--file FILE', String) do |file|
        puts "Outputting to: " + file
        @options[:output_file] = file
      end
      opts.on('-u', '--uri URI', String) do |uri|
        puts "Searching for OLS at: " + uri
        @options[:ols_uri] = uri
      end
      opts.on('-s', '--sleep SLEEP', Integer) do |sleep|
        puts "Sleep interval is: " + sleep
      end

      opts.on('-h', '--help') { output_help }
    end

    pts.parse(arguments)

    @url = URI.parse(@options[:ols_uri])
  end

  def output_help
    puts "Usage Program -f <OUTPUTFILE.XML> -u <URI_OF_OLS>"
    exit
  end

  def updateFile
    begin
      res = Net::HTTP.start(@url.host, @url.port) { |http|
        http.get(@options[:peergroup_path])
      }


      if (@@debug)
        puts res.body
      end

      if (isDifferent res.body)
        writeFile res.body
      end
       @lastFile = res.body
    rescue
      puts "Error connecting to: " +  @options[:ols_uri] + ""
      puts "Exiting"
      exit
    end


  end

  def isDifferent(newFile)
    if (@lastFile == nil)
      return true
    end

    #this only works if the last file was nil, need to do some sort of XML diff
    if (@lastFile.eql?(newFile))
      puts "File Unchanged"
      return false

    else
      return true
    end
  end

  def writeFile(fileContents)
    tempFile = @options[:output_file] + '.tmp'

    File.open(tempFile, 'w') do |f|
      f.write(fileContents)

    end
    File.move(tempFile, @options[:output_file])
  end

end


$fetcher = PeerGroupFetcher.new(ARGV)

loop do
  $fetcher.updateFile
  sleep 30

end