package io.github.danilkuznetsov.google.model;

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
public class DirtyComments {
    private int row;
    private int col;
    private String comments;

    public String getMainCommentId() {
        Pattern pattern = Pattern.compile(".*\\[.*\\].*", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(comments);
        if (matcher.find()) {
            return comments.substring(matcher.start()+1, matcher.end()-1);
        }
        throw new IllegalStateException("Comment id no found");
    }
}
