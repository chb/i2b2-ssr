define keystore($keystore, $keystore_xml, $keystore_alias, $keystore_password) {

    file { "$keystore_xml_path":
        owner => carra,
        group => carra,
        mode => 640,
	    content => template("keystore.xml.erb")
    }
}

