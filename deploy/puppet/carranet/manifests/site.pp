# so we let su do so for us now# so we let su do so for us nossions on sudo
import "classes/*.pp"

class sudo {
    file { "/etc/sudoers":
        owner => root,
        group => root,
        mode => 440,
    }
}

node default {
     include sudo
}

node siteNode inherits default {
     include adapter_mappings
}

node 'carra-test-1.tch.harvard.edu' inherits siteNode {
   i2b2 { puppet:  
      db_conn_string => "connString",
      db_username => "user",
      db_password => "password",
   }

   keystore { puppet:
      keystore => "/home/carra/shrineKeystore",
      keystore_xml_path => "/home/carra/shrine/tomcat/lib/keystore.xml",
      keystore_alias => "mykey",
      keystore_password => "",
   }
}

node 'carra-node-production.tch.harvard.edu' inherits siteNode {
   i2b2 { puppet:  
      db_conn_string => "connString",
      db_username => "user",
      db_password => "password",
   }

   keystore { puppet:
      keystore => "/home/carra/shrineKeystore",
      keystore_xml_path => "/home/carra/shrine/tomcat/lib/keystore.xml",
      keystore_alias => "mykey",
      keystore_password => "",
   }
}

