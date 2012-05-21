package edu.chip.carranet.carradatapipeline.transactionstore;

import java.sql.Timestamp;

/**
 * @author Justin Quan
 * Date: 3/1/11
 */
public class InformTransactionRecord {
    public static final String BOOKMARK_START = "";

    private String trial;
    private String bookmarkValue;
    private Timestamp fetchDate;
    private Timestamp processedDate;
    private String nextBookmark;
    private String auditReport;

    public InformTransactionRecord(String trial, String bookmarkValue, Timestamp fetchDate, Timestamp processedDate, String nextBookmark, String auditReport) {
        this.trial = trial;
        this.bookmarkValue = bookmarkValue;
        this.fetchDate = fetchDate;
        this.processedDate = processedDate;
        this.nextBookmark = nextBookmark;
        this.auditReport = auditReport;
    }

    public String getTrial() {
        return trial;
    }

    public void setTrial(String trial) {
        this.trial = trial;
    }

    public String getBookmarkValue() {
        return bookmarkValue;
    }

    public void setBookmarkValue(String bookmarkValue) {
        this.bookmarkValue = bookmarkValue;
    }

    public Timestamp getFetchDate() {
        return fetchDate;
    }

    public void setFetchDate(Timestamp fetchDate) {
        this.fetchDate = fetchDate;
    }

    public Timestamp getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Timestamp processedDate) {
        this.processedDate = processedDate;
    }

    public String getNextBookmark() {
        return nextBookmark;
    }

    public void setNextBookmark(String nextBookmark) {
        this.nextBookmark = nextBookmark;
    }

    public String getAuditReport() {
        return auditReport;
    }

    public void setAuditReport(String auditReport) {
        this.auditReport = auditReport;
    }

    @Override
    public String toString() {
        return "InformTransactionRecord{" +
                "trial='" + trial + '\'' +
                ", bookmarkValue='" + bookmarkValue + '\'' +
                ", fetchDate=" + fetchDate +
                ", processedDate=" + processedDate +
                ", nextBookmark='" + nextBookmark + '\'' +
                ", auditReport='" + auditReport + '\'' +
                '}';
    }
}
