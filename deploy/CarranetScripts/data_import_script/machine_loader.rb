require 'DBI'
require 'erb'

class MachineLoader
  @@file_path = 'sql_scripts'
  @@hive_data = "#{@@file_path}/HiveData/scripts"
  @@pm_data = "#{@@file_path}/Pmdata/scripts"
  @@demo_data = "#{@@file_path}/Demodata/scripts"
  @@work_data = "#{@@file_path}/Workdata/scripts"


  @@CLEAR_DATABASES = true
  @@create_import_table_1 = <<-eos
	CREATE TABLE TBLRSLTDATAIMPORT (
    	STUDY NVARCHAR2(100),
		SUBJECTKEY NVARCHAR2(38) NULL,
		SITELOCATION NVARCHAR2(50) NULL,
		STUDYEVENT NVARCHAR2(50) NULL,
		STUDYEVENTREPEATKEY NVARCHAR2(100) NULL,
		FORM NVARCHAR2(100) NULL,
		ITEMGROUP NVARCHAR2(100) NULL,
		ITEMGROUPREPEATKEY NVARCHAR2(100) NULL,
		ITEM NVARCHAR2(100) NULL,
		VAL NVARCHAR2(100) NULL,
		HASVAL CHAR(1) NULL,
		ITEMLOCATION NVARCHAR2(100) NULL,
		DATETIMESTAMP NVARCHAR2(100) NULL,
		REASONFORCHANGE NVARCHAR2(1000) NULL,
		ODMCODE NVARCHAR2(100) NULL,
		ANSWER NVARCHAR2(100) NULL,
		TYPE NVARCHAR2(255) NULL,
		CONCEPTCODE NVARCHAR2(500) NULL,
		MODIFIER NVARCHAR2(100) NULL,
		LOCATION_PATH NVARCHAR2(50) NOT NULL,
		PATIENT_NUM NVARCHAR2(100) NULL,
		CONCEPT_CD NVARCHAR2(500) NULL,
		PROVIDER_ID NVARCHAR2(50) NOT NULL,
		START_DATE NVARCHAR2(50) NULL,
		MODIFIER_CD NVARCHAR2(100) NOT NULL,
		VALTYPE_CD NVARCHAR2(50) NULL,
		TVAL_CHAR VARCHAR2(255) NULL,
		NVAL_NUM NUMBER(18,5) NULL,
		VALUEFLAG_CD NVARCHAR2(50) NULL,
		SOURCESYSTEM_CD NVARCHAR2(50) NULL,
		ERRORDESCRIPTION NVARCHAR2(2000) NULL
    	)
  eos


  @@create_import_table_2 = <<-eos
		CREATE TABLE tmp_EncTable (
			ENCOUNTER_NUM NUMBER(38,0),
			PATIENT_NUM NVARCHAR2(100) NULL,
			LOCATION_PATH NVARCHAR2(50) NOT NULL,
			START_DATE DATE,
			END_DATE DATE,
			SOURCESYSTEM_CD NVARCHAR2(50) NULL,
			IsNewEncounter NUMBER(3,0) default 1
		)
  eos


  def initialize(machine)
    @machine = machine
  end

  def load_machine
    puts "Connecting to machine #{@machine.username}"

    #update_schema_names
    #create_import_tables
    create_index_on_import_tables
    #create_dummy_visit
    #alter_obs_fact_table
    #create_synonym_concept_dimension

  end


  def create_synonym_concept_dimension
    puts "Creating a synonym to the master ontology"
    connection = OCI8.new(@machine.username, @machine.password, @machine.oracle_connection_string)
    statements = Array.new
    begin
      connection.exec("drop table concept_dimension")
    rescue
      puts "Error dropping concept_dimension table, maybe it was gone already?"
    end

    connection.exec("create synonym concept_dimension for rc_carra_core.concept_dimension ")
    connection.commit
    connection.logoff;

  end

  def alter_obs_fact_table
    connection = OCI8.new(@machine.username, @machine.password, @machine.oracle_connection_string)
    statements = Array.new

    connection.exec("alter table OBSERVATION_FACT modify concept_cd varchar2(500)")

    connection.commit
    connection.logoff;

  end

  def create_dummy_visit
    query = <<-eof
	Insert into VISIT_DIMENSION (ENCOUNTER_NUM,PATIENT_NUM,ACTIVE_STATUS_CD,START_DATE,END_DATE,INOUT_CD,LOCATION_CD,LOCATION_PATH,
	VISIT_BLOB,UPDATE_DATE,DOWNLOAD_DATE,IMPORT_DATE,SOURCESYSTEM_CD,UPLOAD_ID) 
	values (1,1000000043,null,to_timestamp('25-APR-07','DD-MON-RR HH.MI.SSXFF AM'),
	to_timestamp('25-APR-07','DD-MON-RR HH.MI.SSXFF AM'),'@','@','@', EMPTY_CLOB(),to_timestamp('25-APR-07',
	'DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('25-APR-07','DD-MON-RR HH.MI.SSXFF AM'),
	to_timestamp('25-APR-07','DD-MON-RR HH.MI.SSXFF AM'),'DEMOGRAPH|DEMO',null)
    eof

    connection = OCI8.new(@machine.username, @machine.password, @machine.oracle_connection_string)
    statements = Array.new

    connection.exec(query)

    connection.commit
    connection.logoff;


  end

  def update_schema_names
    connection = OCI8.new(@machine.username, @machine.password, @machine.oracle_connection_string)
    statements = Array.new

    statements.push "update CRC_DB_LOOKUP SET C_DB_FULLSCHEMA = :name, C_PROJECT_PATH = '@' "
    statements.push "update WORK_DB_LOOKUP SET C_DB_FULLSCHEMA = :name"
    statements.push "update ONT_DB_LOOKUP SET C_DB_FULLSCHEMA = :name"
    update_cell = "update PM_CELL_DATA SET URL = :url where CELL_ID= :cellid"

    for statement in statements
      connection.exec(statement, @machine.username)
    end

    connection.exec(update_cell, "http://carra-i2b2-production:9090/i2b2/rest/OntologyService/", 'ONT')

    connection.commit
    connection.logoff;
  end

  def create_import_tables

    connection = OCI8.new(@machine.username, @machine.password, @machine.oracle_connection_string)

    connection.exec(@@create_import_table_1)
    connection.exec(@@create_import_table_2)

    connection.commit
    connection.logoff;
  end

  def create_index_on_import_tables
    # create index for table1

    connection = OCI8.new(@machine.username, @machine.password, @machine.oracle_connection_string)
    connection.exec("create index TBLDIMP on TBLRSLTDATAIMPORT(PATIENT_NUM)")
    connection.commit
    connection.logoff;
  end


  def clear_database
    connection = OCI8.new(@machine.username, @machine.password, @machine.oracle_connection_string)
    sql_statement = "select 'drop table '||table_name||' cascade constraints' from user_tables"
    statements = Array.new

    connection.exec(sql_statement) do |r|
      statements.push r.join(",")
    end

    for i in statements
      connection.exec(i)
    end

    connection.commit
    connection.logoff


  end


  def run_erbified_sql_script(scriptFile)
    sql_string = IO.read(scriptFile)
    erber = ERB.new(sql_string)
    erb_result = erber.result(@machine.get_binding)
    command = "echo '#{erb_result}' | sqlplus #{@machine.sql_plus_connection_string} >> sqlplus.out"
    system(command)
    puts command
  end
end
