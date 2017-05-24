package io.github.danilkuznetsov.google.model.xlsx;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Danil Kuznetsov
 */

@Data
public class XlsxCell {
    private final String sheetName;
    private final int row;
    private final int col;
    private final String commentContent;
    private final String cellContent;

    public String getMainCommentId() {
        if (!commentContent.isEmpty()) {
            Pattern pattern = Pattern.compile(".*\\[(.*)\\].*", Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(commentContent);
            if (matcher.find()) {
                return matcher.group(1);
            }
            throw new IllegalStateException("Comment id no found");
        }
        return "";
    }
}
