# used to take csv dump from google docs of the ontology mappings file and create the mappings.xml used in odm import
require 'csv'

puts "<?xml version=\"1.0\"?>"
puts "<ODMMapping xmlns=\"http://carranet.net/CarraRegistry/1.0\">"

CSV.open('i2b2_carranet_ontology.csv', 'r', ',') do |row|
  line = "<map"
  if(row[0])
    line += " odm_code=\"" + row[0] + "\""
  else
    next
  end
  if(row[1])
    line += " answer=\"" + row[1] + "\""
  end
  if(row[2])
    line += " type=\"" + row[2] + "\""
  end
  if(row[3])
    line += " concept_code=\"" + row[3] + "\""
  end
  if(row[4])
    line += " modifier=\"" + row[4] + "\""
  end
  puts line +"/>"
end
puts "</ODMMapping>"
