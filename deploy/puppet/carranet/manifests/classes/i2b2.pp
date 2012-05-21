define i2b2($db_conn_string, $db_username, $db_password){

   file{"/home/carra/jboss-4.2.2.GA/server/default/deploy/crc-ds.xml":
      owner => "carra",
      group => "carra",
      ensure  => present,
      content => template("crc-ds.xml.erb")
   }
   
   file{"/home/carra/jboss-4.2.2.GA/server/default/deploy/crc-jms-ds.xml":
      owner => "carra",
      group => "carra",
      ensure  => present,
      content => template("crc-jms-ds.xml.erb")
   }
   
   file{"/home/carra/jboss-4.2.2.GA/server/default/deploy/pm-ds.xml":
      owner => "carra",
      group => "carra",
      ensure  => present,
      content => template("pm-ds.xml.erb")
   }

   file{"/home/carra/jboss-4.2.2.GA/server/default/conf/crcapp/CRCApplicationContext.xml":
      owner => "carra",
      group => "carra",
      ensure  => present,
      content => template("CRCApplicationContext.xml.erb")
   }

   file{"/home/carra/jboss-4.2.2.GA/server/default/conf/crcapp/crc.properties":
      owner => "carra",
      group => "carra",
      ensure  => present,
      content => template("crc.properties.erb")
   }
}
