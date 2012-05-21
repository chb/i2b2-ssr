package edu.chip.carranet.carradatapipeline.pipeline.obfuscation;

import org.cdisc.ns.odm.v1.ItemData;
import org.joda.time.DateTime;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Patient implements Comparable {

    public static final DateTime STUDY_START_DATE = new DateTime("1994-07-01T00:00:00-05:00");
    public static final DateTime STUDY_END_DATE = new DateTime("2010-07-01T00:00:00-05:00");

    public static final String DEMO_BDAY = "frDEM.stDEM1.SBIRTHDT.SBIRTHDT";
    public static final String SCREEN_BDAY = "frIFSCREEN.frIFSCREEN.SBIRTDT.SBIRTDT";
    public static final String RHEUMDT = "frDEM.stDEM2.RHEUMDT.RHEUMDT";
    public static final String ONSETDT = "frDEM.stDEM2.ONSETDT.ONSETDT";
    public static final String VISDT = "frVIS.frVIS.VISDT.VISDT";
    public static final String BIODT = "frBSLE2.stBSLE2F.stBSLE2F.BSLBPDT.BSLBPDT";
    public static final String ZIP_CODE = "frDEM.stDEM2.ZIPUNK.ZIPUNK.ZIPCODE";


    private String subjectKey;
    private String siteIdentifier;
    private Map<String, List<ItemData>> observations = new HashMap<String, List<ItemData>>();


    public void addItem(ItemData data) {
        String itemOid = data.getItemOID();

        if (observations.get(itemOid) == null) {
            observations.put(itemOid, new ArrayList<ItemData>());
        }

        observations.get(itemOid).add(data);

    }

    public List<ItemData> getObservations(String observationKey) {
        List<ItemData> returnList = observations.get(observationKey);
        if (returnList == null) {
            return new ArrayList<ItemData>();
        } else {
            return returnList;
        }
    }

    /**
     * @param observationKey
     * @return
     */
    public ItemData popNextObservation(String observationKey) {
        List<ItemData> itemList = observations.get(observationKey);
        if (itemList == null || itemList.size() > 1) {
            return null;
        }
        return itemList.remove(0);
    }


    public String getSubjectKey() {
        return subjectKey;
    }

    public void setSubjectKey(String subjectKey) {
        this.subjectKey = subjectKey;
    }


    public Map<String, List<ItemData>> getObservations() {
        return observations;
    }

    public void setObservations(Map<String, List<ItemData>> observations) {
        this.observations = observations;
    }

    public List<ItemData> getFreeTextFields() {
        Set<String> freeTextFieldNames = new HashSet<String>();

        for (String s : observations.keySet()) {
            if (s.endsWith("SP") && !s.endsWith("BJIHEPSP") && !s.endsWith("FJIHEPSP")) {
                freeTextFieldNames.add(s);
            }
        }

        List<ItemData> returnList = new ArrayList<ItemData>();

        for (String s : freeTextFieldNames) {
            returnList.addAll(observations.get(s));
        }
        return returnList;


    }

    public Date getLatestBirthday() {
        List<ItemData> itemDataList = new ArrayList<ItemData>();
        itemDataList.addAll(getObservations(DEMO_BDAY));
        itemDataList.addAll(getObservations(SCREEN_BDAY));
        return getLatestDateFrom(itemDataList);

    }

    public Date getLatestOnsetDate() {
        List<ItemData> itemDataList = new ArrayList<ItemData>();
        itemDataList.addAll(getObservations(ONSETDT));
        return getLatestDateFrom(itemDataList);

    }

    public Date getLatestVisitDate() {
        List<ItemData> itemDataList = new ArrayList<ItemData>();
        itemDataList.addAll(getObservations(VISDT));
        return getLatestDateFrom(itemDataList);

    }

    public Date getLatestRheumDate() {
        List<ItemData> itemDataList = new ArrayList<ItemData>();
        itemDataList.addAll(getObservations(RHEUMDT));
        return getLatestDateFrom(itemDataList);

    }

    private Date getLatestDateFrom(List<ItemData> items) {
        if (items.size() == 0) {
            return null;
        }

        List<Date> birthdayList = new ArrayList<Date>();
        for (ItemData d : items) {
            birthdayList.add(new DateTime(fixMessupDateString(d.getValue())).toDate());
        }

        Collections.sort(birthdayList);
        return birthdayList.get(birthdayList.size() - 1);
    }


    public Date getFirstBirthday() {
        List<ItemData> itemDataList = new ArrayList<ItemData>();
        itemDataList.addAll(getObservations(DEMO_BDAY));
        itemDataList.addAll(getObservations(SCREEN_BDAY));
        return getFirstDateFrom(itemDataList);

    }

    public Date getFirstOnsetDate() {
        List<ItemData> itemDataList = new ArrayList<ItemData>();
        itemDataList.addAll(getObservations(ONSETDT));
        return getFirstDateFrom(itemDataList);

    }

    public Date getFirstVisitDate() {
        List<ItemData> itemDataList = new ArrayList<ItemData>();
        itemDataList.addAll(getObservations(VISDT));
        return getFirstDateFrom(itemDataList);

    }

    public Date getFirstRheumDate() {
        List<ItemData> itemDataList = new ArrayList<ItemData>();
        itemDataList.addAll(getObservations(RHEUMDT));
        return getFirstDateFrom(itemDataList);

    }


    private Date getFirstDateFrom(List<ItemData> items) {
        if (items.size() == 0) {
            return null;
        }

        List<Date> birthdayList = new ArrayList<Date>();
        for (ItemData d : items) {
            birthdayList.add(new DateTime(fixMessupDateString(d.getValue())).toDate());
        }

        Collections.sort(birthdayList);
        return birthdayList.get(0);
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || !(o instanceof Patient)) {
            return 1;
        } else {
            Patient p = (Patient) o;
            return this.subjectKey.compareTo(p.getSubjectKey());

        }
    }

    public static String fixMessupDateString(String badDate) {

        String regex = "(\\d{4}|-)-(\\d{2}|\\d{1}|-)-(\\d{2}|\\d{1}|-)T(\\d{2}|\\d{1}|-):(\\d{2}|-):(\\d{2}|-).*";


        if (badDate == null) {
            return new DateTime(RandomDateGenerator.generateBetween(Patient.STUDY_START_DATE.toDate(),
                    Patient.STUDY_END_DATE.toDate())).toString();
        }

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(badDate);
        m.matches();
        System.out.println(m.group(1));
        System.out.println(m.group(2));
        System.out.println(m.group(3));
        System.out.println(m.group(4));
        System.out.println(m.group(5));
        System.out.println(m.group(6));

        int[] intArray = new int[6];

        for (int i = 1; i <= 6; i++) {
            try {
                intArray[i - 1] = Integer.parseInt(m.group(i));
            } catch (NumberFormatException e) {
                intArray[i - 1] = 1;
            }
        }

        int year = intArray[0];
        int month = intArray[1];
        int day = intArray[2];
        int hour = intArray[3];
        int minute = intArray[4];
        int second = intArray[5];

        DateTime dt = new DateTime(year, month, day, hour, minute, second, 0);


        if (dt.compareTo(STUDY_START_DATE) == -1) {
            return STUDY_START_DATE.toString();
        } else {
            return dt.toString();
        }


    }
}



