package edu.chip.carranet.carradatapipeline;

import edu.chip.carranet.importpipeline.config.FetchConfig;
import edu.chip.carranet.inform.InformConfig;
import org.apache.commons.configuration.BaseConfiguration;

/**
 * @author Justin Quan
 * @link http://chip.org
 * Date: 3/23/11
 */
public class PipelineConfig extends BaseConfiguration {
    public static final String TRANSACTION_TABLE_NAME = "PipelineStore.TransactionTableName";
    public static final String SITE_TRANSACTION_TABLE_NAME = "PipelineStore.SiteTransactionTableName";
    public static final String TRANSFORM_ENCOUNTER_TABLE_NAME = "PipelineStore.TransformEncounterTableName";
    public static final String TRANSFORM_RESULT_TABLE_NAME = "PipelineStore.TransformResultTableName";
    public static final String DB_USER = "PipelineStore.User";
    public static final String DB_PASSWORD = "PipelineStore.Password";
    public static final String DB_CONNECTION_STRING = "PipelineStore.ConnectionString";
    public static final String INFORM_TRIAL = "InformClient.TrialName";
    public static final String INFORM_USERNAME = "InformClient.UserName";
    public static final String INFORM_PASSWORD = "InformClient.Password";
    public static final String INFORM_ENDPOINT = "InformClient.Endpoint";
    public static final String ODM_IGNORE_UNMAPPED_FACTS = "InformClient.ignoreUnmappedFacts";
    public static final String INFORM_USE_WS_SECURITY = "InformClient.UseWSSecurity";
    public static final String FETCHER_PERIOD = "Fetcher.Period";
    public static final String FETCHER_TIMEUNIT = "Fetcher.TimeUnit";
    public static final String SITEMAP_FILENAME = "SiteMap.FileName";
    public static final String ODM_IGNORE_LIST_FORM_FILENAME = "ODMIgnoreList.FormFileName";
    public static final String ODM_IGNORE_LIST_ITEM_FILENAME = "ODMIgnoreList.ItemFileName";
    public static final String SQL_OUTPUTTER_USE_BATCH = "SqlOutputter.UseBatch";

    // Default In-Mem Values
    public PipelineConfig() {
        super();
        // pipeline
        setProperty(TRANSACTION_TABLE_NAME, "INFORMTRANSACTIONS");
        setProperty(SITE_TRANSACTION_TABLE_NAME, "INFORMSITETRANSACTIONS");
        setProperty(TRANSFORM_RESULT_TABLE_NAME, "TBLRSLTDATAIMPORT");
        setProperty(TRANSFORM_ENCOUNTER_TABLE_NAME, "TMP_ENCTABLE");
        setProperty(DB_USER, "");
        setProperty(DB_PASSWORD, "");
        setProperty(DB_CONNECTION_STRING, "jdbc:oracle:thin:@chdev1:1521/CHRACDEV");

        // inform
        setProperty(INFORM_TRIAL, InformConfig.CHIP_TEST.getTrial());
        setProperty(INFORM_USERNAME, InformConfig.CHIP_TEST.getUser());
        setProperty(INFORM_ENDPOINT, InformConfig.CHIP_TEST.getEndpoint());
        setProperty(INFORM_USE_WS_SECURITY, InformConfig.CHIP_TEST.isUsingWSSecurity());
        setProperty(ODM_IGNORE_UNMAPPED_FACTS, false);

        // fetcher
        setProperty(FETCHER_PERIOD, FetchConfig.DEFAULT_FETCH_CONFIG.getFetchPeriod());
        setProperty(FETCHER_TIMEUNIT, FetchConfig.DEFAULT_FETCH_CONFIG.getFetchTimeUnit().name());

        // sitemap
        setProperty(SITEMAP_FILENAME, "sitemap.xml");

        // odm ignorelist
        setProperty(ODM_IGNORE_LIST_FORM_FILENAME, "odmFormIgnoreList.txt");
        setProperty(ODM_IGNORE_LIST_ITEM_FILENAME, "odmItemIgnoreList.txt");

        // sqloutputter
        setProperty(SQL_OUTPUTTER_USE_BATCH, true);
    }
}
