package io.github.danilkuznetsov.google.model.export;

import lombok.Data;

import java.util.List;

/**
 * @author Danil Kuznetsov
 */

@Data
public class ExportComment {
    private final int col;
    private final int row;
    private final String id;
    private final String sheetsName;
    private final String author;
    private final String contentComment;
    private final String htmlContentComment;
    private final String createdTime;
    private final List<ExportReply> replies;
    private final boolean resolved;
    private final boolean deleted;
    private final String sheetGoogleId;
}
