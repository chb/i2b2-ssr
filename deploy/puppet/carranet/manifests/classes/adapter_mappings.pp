class adapter_mappings {
    file { "/home/carra/shrine/tomcat/lib/AdapterMappings.xml":
        owner => carra,
        group => carra,
        mode => 640,
	content => template("AdapterMappings.xml.erb")
    }
}

