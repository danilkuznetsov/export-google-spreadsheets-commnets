package io.github.danilkuznetsov.google.model.xlsx;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Danil Kuznetsov
 */

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@ToString
public class XlsxCell {
    private final String sheetName;
    private final int row;
    private final int col;
    private final String commentContent;

    public String getMainCommentId() {
        Pattern pattern = Pattern.compile(".*\\[(.*)\\].*", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(commentContent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalStateException("Comment id no found");
    }
}
