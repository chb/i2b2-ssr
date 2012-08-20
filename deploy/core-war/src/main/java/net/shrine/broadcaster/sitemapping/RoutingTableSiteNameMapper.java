package net.shrine.broadcaster.sitemapping;


public class RoutingTableSiteNameMapper implements SiteNameMapper {
    @Override
    public String getSiteIdentifierFromDN(String dn) {
        return null;
    }

    @Override
    public String getSiteIdentifierFromHostname(String hostName) {
        return null;
    }

    public RoutingTableSiteNameMapper(String test) {
        System.out.println("HI");
    }


    //    private static Logger log = Logger.getLogger(RoutingTableSiteNameMapper.class);
//    private List<MachineEntry> machineList = new ArrayList<MachineEntry>();
//    private static final String OLS_ADDRESS = "http://carra-core-production:8183/machines/";
//
//    public RoutingTableSiteNameMapper() {
//        this(OLS_ADDRESS);
//    }
//
//    public RoutingTableSiteNameMapper(String address) {
//        try {
//            //IOLSClient c = new OLSClient("http://carra-core-producion:8183");
//            //machineList = c.listMachines(null).getMachineList();
//
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpGet httpget = new HttpGet(address);
//            HttpResponse response = httpclient.execute(httpget);
//            HttpEntity entity = response.getEntity();
//            if(entity != null) {
//                Reader r = new InputStreamReader(entity.getContent());
//                Machines machines = JAXBUtils.unmarshal(r, Machines.class);
//                if(machines != null) {
//                    machineList = machines.getMachineList();
//                }
//                //trim out newlines, they screw up everything
//                for(MachineEntry m : machineList) {
//                    m.setMachineId(m.getMachineId().trim());
//                }
//            }
//        } catch(MalformedURLException e) {
//            log.fatal("MaformedURL exceptions shouldn't be happening here, don't store invalid URLS in OLS!!!!");
//            //can't happen, it's hard coded
//            throw new RuntimeException(e);
//        } catch(IOException e) {
//            log.fatal("Fatal IOException");
//        } catch(JAXBException e) {
//            log.fatal("Fatal JAXBException");
//        }
//    }
//
//
//    /**
//     * This method takes the DN from SPIN metadata and coverts it
//     * into something useful
//     *
//     * @param dn
//     * @return
//     */
//    @Override
//    public String getSiteIdentifierFromDN(String dn) {
//        final X509Principal principal = new X509Principal(dn);
//        final Vector<?> values = principal.getValues(X509Name.CN);
//        final String cn = (String) values.get(0);
//        return getSiteIdentifierFromHostname(cn);
//    }
//
//    @Override
//    public String getSiteIdentifierFromHostname(String hostName) {
//        try {
//            for(MachineEntry m : machineList) {
//                URL url = new URL(m.getLocator());
//                if(url.getHost().trim().equalsIgnoreCase(hostName.trim())) {
//                    return m.getMachineId();
//                }
//            }
//        } catch(MalformedURLException e) {
//            //cant' happen we parse it on the way in foolio
//        }
//
//        return "Unknown Site";
//    }


}
