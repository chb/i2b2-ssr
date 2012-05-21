require 'rubygems'
require 'libxml'
require 'time'

include LibXML

# assumptions: data being translated is occuring sequentially in time


# a callback class that defines how to 
# parse odm->i2b2 mappings
class MappingCallbacks
  include XML::SaxParser::Callbacks

  attr_accessor :map

  def initialize
    @map = {}
  end
  
  def on_start_element(element, attributes)
    if(element == 'map')
      code = attributes['odm_code']
      answer = attributes['answer']
      concept = attributes['concept_code']
      type = attributes['type']
      modifier = attributes['modifier']
      
      if(@map[code].nil?)
        @map[code] = {}
      end
      if(@map[code][answer].nil?)
        @map[code][answer] = {}
      end
      @map[code][answer]['type'] = type;
      @map[code][answer]['concept_code'] = concept;
      @map[code][answer]['modifier'] = modifier;      
    end
  end
end

class PostCallbacks
  include XML::SaxParser::Callbacks
  attr_accessor :dedupe, :eventDateMap

  def initialize
    @dedupe = {}
    @event = ""
    @eventRepeat = ""
    @subject = ""
    @itemRepeat = ""
    @mappings = MappingCallbacks.new
    parser2 = XML::SaxParser.file("/home/justin/Downloads/mappings.xml")
    parser2.callbacks = @mappings
    parser2.parse

    @eventIdMapper = {"evSCRN" => "1", "evENRL" => "2", "evHIST" => "3", "evBASE" => "4", "evSTATUS" => "5", "evFU" => "" }
    @eventDateMap = {}

  end
  
  def on_start_element(element, attributes)
    
    if element == 'StudyEventData'
      @event = @eventIdMapper[attributes["StudyEventOID"]]
      @eventRepeat = attributes["StudyEventRepeatKey"]
      @event = @eventRepeat unless @eventRepeat.nil?
    end
    if element == 'SubjectData'
      @subject = attributes["SubjectKey"]
    end
    if element == 'ItemGroupData'
      @itemRepeat = attributes["ItemGroupRepeatKey"]
    end
    if element == 'ItemData'
      # lookup in mapping table
      itemOID = attributes["ItemOID"]
      value = attributes["Value"]
      itemOID_match = @mappings.map[itemOID]
      if(itemOID_match)
        match = itemOID_match[value] ? itemOID_match[value] : itemOID_match[nil]
        if(match.nil?)
          puts "XXX failed match for #{itemOID} and #{value}"
          return
        end
        # set valtype/tval/nval
        valType = ""
        tval = ""
        nval = ""
        if(match['type'] == 'text') 
          valType = 'T'
          tval = value
        elsif(match['type'] == 'num')
          valType = 'N'
          tval = 'E'
          nval = value
        end
        
        # set modifier
        mod = "@"
        if(match['modifier'] == 'repeatKey')
          mod = @itemRepeat
        elsif(match['modifier'] == 'value')
          mod = value
        end

        # if this is the date, save it
        if(itemOID == "frVIS.frVIS.VISDT.VISDT")
          if(@eventDateMap[@subject].nil?)
            @eventDateMap[@subject] = {}
          end
          t = Time.parse(value)          
          # http://ruby-doc.org/core/classes/Time.html#M000298
          @eventDateMap[@subject][@eventRepeat] = t.strftime("%d-%b-%y")
        end
        
        # TODO: sanitize values for commas, perhaps use a CSV lib
        outputString = "#{@event},#{@subject},#{match['concept_code']},@,,#{mod},#{valType},#{tval},#{nval},,,,,,,,,13-SEP-10,13-SEP-10,13-SEP-10,jquan,"
        
        # dedupe takes care of updates to the same value
        # so long as we assume updates come in order
        if(@dedupe[@subject].nil?)
          @dedupe[@subject] = {}
        end
        
        if(@dedupe[@subject][@event].nil?)
          @dedupe[@subject][@event] = {}
        end        

        @dedupe[@subject][@event][itemOID] = outputString

      else 
        puts "XXX no match for item #{itemOID}"
      end
    end

  end

end

def process(callback)
  callback.dedupe.values.each { 
    |map| map.values.each { 
      |map2| map2.values.each do |val|
        newValue = ""
        index = 0
        subject = ""
        eventRepeat = ""
        val.each(',') do |segment|
          if(index == 0)
            eventRepeat = (segment.length > 5) ? segment : nil
            eventRepeat = eventRepeat.tr(',','') unless eventRepeat.nil?
            #          puts "eventRepeat #{eventRepeat}"
          elsif(index == 1)
            subject = segment
            subject = subject.tr(',','')
            #          puts "subject #{subject}"
          elsif(index == 4)
            begin
              date = callback.eventDateMap[subject][eventRepeat]
            rescue
              # unfortunately, we have data where we didn't find the date yet
              puts "ZZZ"
              #puts "zsub #{subject}, zevent #{eventRepeat}"
              date = ""            
            end
            
            #puts "date #{date}"
            newValue += date
          end
          newValue += segment
          index += 1
        end
        # there should be 21 segments only.  if there are more, error because
        # we need to do smarter comma parsing
        if(index == 21)
          puts newValue
        else
          puts "XXX error too many commas: #{newValue}"
        end
      end
    }
  }
end


parser1 = XML::SaxParser.file("/home/justin/Documents/ontology/getTrans1.xml")
callback = PostCallbacks.new
parser1.callbacks = callback
parser1.parse
parser2 = XML::SaxParser.file("/home/justin/Documents/ontology/getTrans2.xml")
parser2.callbacks = callback
parser2.parse
process(callback)
