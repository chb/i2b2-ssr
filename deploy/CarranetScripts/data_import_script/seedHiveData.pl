#!/usr/bin/perl

   use File::Copy;
   use Cwd;
   use strict;
   use warnings;
   
   #Perl script that runs all the commands necessary to populate
   #an i2b2 database with test data...could come in handy


   my @USER_NAMES = 
		(
			"rc_carra_vm1_project1",
			"rc_carra_vm2_project1",
			"rc_carra_vm3_project1",
			"rc_carra_vm4_project1",
			"rc_carra_vm5_project1",
			"rc_carra_vm6_project1",
			"rc_carra_vm7_project1",
			"rc_carra_vm8_project1",
			"rc_carra_vm9_project1",
			"rc_carra_vm10_project1",
			"rc_carra_vm11_project1",
			"rc_carra_vm12_project1",
			"rc_carra_vm13_project1",
			"rc_carra_vm14_project1",
			"rc_carra_vm15_project1",
			"rc_carra_vm16_project1",
			"rc_carra_vm17_project1",
			"rc_carra_vm18_project1",
			"rc_carra_vm19_project1",
			"rc_carra_vm20_project1",
			"rc_carra_vm21_project1", 
			"rc_carra_vm22_project1", 
			"rc_carra_vm23_project1", 
			"rc_carra_vm24_project1", 
			"rc_carra_vm25_project1", 
			"rc_carra_vm26_project1", 
			"rc_carra_vm27_project1", 
			"rc_carra_vm28_project1", 
			"rc_carra_vm29_project1", 
			"rc_carra_vm30_project1", 
			"rc_carra_vm31_project1", 
			"rc_carra_vm32_project1", 
			"rc_carra_vm33_project1", 
			"rc_carra_vm34_project1", 
			"rc_carra_vm35_project1", 
			"rc_carra_vm36_project1", 
			"rc_carra_vm37_project1", 
			"rc_carra_vm38_project1", 
			"rc_carra_vm39_project1", 
			"rc_carra_vm40_project1", 
			"rc_carra_vm41_project1", 
			"rc_carra_vm42_project1", 
			"rc_carra_vm43_project1", 
			"rc_carra_vm44_project1", 
			"rc_carra_vm45_project1", 
			"rc_carra_vm46_project1", 
			"rc_carra_vm47_project1", 
			"rc_carra_vm48_project1", 
			"rc_carra_vm49_project1", 
			"rc_carra_vm50_project1", 
			"rc_carra_vm51_project1", 
			"rc_carra_vm52_project1", 
			"rc_carra_vm53_project1", 
			"rc_carra_vm54_project1", 
			"rc_carra_vm55_project1", 
			"rc_carra_vm56_project1", 
			"rc_carra_vm57_project1", 
			"rc_carra_vm58_project1", 
			"rc_carra_vm59_project1", 
			"rc_carra_vm60_project1", 
		);

   my $DATA_PATH = $ENV{'PWD'}."/sql_scripts/";
       print $ENV{'PWD'}."\n";

   #the keys of this hash are the individual subdirs and the commands we
   #need to run inside;
   my %COMMANDS = (

#      'Metadata'	     => [  "ant -f data_build.xml create_metadata_tables_release_1-5",
#				   "ant -f data_build.xml db_metadata_load_data"],

#      'Workdata'	     => [  "ant -f data_build.xml create_workdata_tables_release_1-5",
#			             "ant -f data_build.xml db_workdata_load_data"],

      'Hivedata'	     => [  "ant -f data_build.xml create_hivedata_tables_release_1-5",
		   "ant -f data_build.xml db_hivedata_load_data"],

      'Pmdata'		      => ["ant -f data_build.xml create_pmdata_tables_release_1-5",
				  "ant -f data_build.xml create_triggers_release_1-5",
				  "ant -f data_build.xml db_pmdata_load_data"],

      'Demodata' => ["ant -f data_build.xml create_demodata_tables_release_1-5",
	            "ant -f data_build.xml create_procedures_release_1-5",]
      
   );   


   sub populate_db;

   print $DATA_PATH;
   chdir($DATA_PATH) or die "Couldn't find base data dir " . $!;

 
   
   foreach(@USER_NAMES)	{
      
      my $username = $_;
      populate_db($username);
      
   }

   sub populate_db(){
      
      my ($username) = @_;
   
      my $CONFIG_FILE = "db.type=oracle\n" .
		     "db.username=".$username."\n" .
		     "db.password=chracdev\n" .
		     "db.server=chdev1:1521:CHRACDEV\n" .
		     "db.driver=oracle.jdbc.driver.OracleDriver\n" .
		    "db.url=jdbc:oracle:thin:\@chdev1:1521/CHRACDEV\n" .
		     "db.project=demo\n";



     my @dirs = keys %COMMANDS;

     for(@dirs){
	    my $dir = $DATA_PATH.$_;
	    print "Changing directory to: ". $dir."\n";
	 
	    chdir($dir) or die $!;
	    print $ENV{'PWD'};
	    move ("db.properties", "db.properties.bak") or die $!;
	    open CONFIG, ">db.properties" or die $!;

	 print CONFIG $CONFIG_FILE;
	 

	 my @coms = @{$COMMANDS{$_}};

	 print "Runnings Commands for ". $_ . "dir\n";
	 
	 for(@coms){
	    my $command = $_;
	    
	    print $_."\n";
	    system($command);
	 }

      }


   }


   


