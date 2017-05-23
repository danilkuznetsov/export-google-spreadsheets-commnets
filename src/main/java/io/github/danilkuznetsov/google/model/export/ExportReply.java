package io.github.danilkuznetsov.google.model.export;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Danil Kuznetsov
 */
@EqualsAndHashCode
@Getter
@ToString
public class ExportReply {
    private final int col;
    private final int row;
    private final String id;
    private final String mainCommentId;
    private final String sheetsName;
    private final String author;
    private final String content;
    private final String htmlContent;
    private final String createdTime;
    private final String modifiedTime;
    private final boolean deleted;

    public ExportReply(int col, int row, String id,
                       String mainCommentId, String sheetsName, String author,
                       String content, String htmlContent, String createdTime,
                       String modifiedTime, boolean deleted) {

        this.col = col;
        this.row = row;
        this.id = id;
        this.mainCommentId = mainCommentId;
        this.sheetsName = sheetsName;
        this.author = author;
        this.content = content;
        this.htmlContent = htmlContent;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.deleted = deleted;
    }
}
