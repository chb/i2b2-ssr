# converts a csv dump (with a header) to executable sql.  helps if the csv matches a table format.
require 'csv'

def isNumeric(s)
  begin
    Float(s)
  rescue
    false # not numeric
  else
    true # numeric
  end
end

first = true;
header = "";
CSV.open(ARGV[0], 'r', ',') do |row|
  if(first)
    # save the header
    header = row.join(",");
    first = false;
    next;
  end
  values = "";
  firstItem = true;
  row.each do |item|
    insertThis = ""
    if item.nil?
      insertThis = "NULL"
    elsif not isNumeric(item)
      escapedItem = item.gsub(/'/, "''");
      insertThis = "'#{escapedItem}'"
    else
      insertThis = item
    end
    if firstItem
      values += insertThis
      firstItem = false;
    else
      values += ", #{insertThis}"
    end    
  end
  # sometimes the trailing data is empty and we won't get a comma, so check the header length and pad with NULL
  (row.length...header.split(/,/).length).each do |i|
    values += ", NULL"
  end
  puts "INSERT INTO #{ARGV[1]} (#{header}) VALUES (#{values});";
end
