package edu.chip.carranet.client.ui;

import com.google.gwt.user.client.ui.FlexTable;
import edu.chip.carranet.client.ui.style.TableStyle;


public class TableUtil {

    public static void paintRows(TableStyle style, FlexTable table) {



        for (int i = 0; i < table.getRowCount(); i++) {
            if (i == 0) {
                table.getRowFormatter().addStyleName(i, style.titleRow());

            } else if (i % 2 == 0) {
                table.getRowFormatter().addStyleName(i, style.evenRow());
            } else {
                table.getRowFormatter().addStyleName(i, style.oddRow());
            }
        }
    }

}
