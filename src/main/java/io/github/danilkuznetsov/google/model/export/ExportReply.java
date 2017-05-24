package io.github.danilkuznetsov.google.model.export;

import lombok.*;

/**
 * @author Danil Kuznetsov
 */
@Data
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
    private final String sheetGoogleId;

}
