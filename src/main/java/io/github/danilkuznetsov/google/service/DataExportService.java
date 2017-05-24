package io.github.danilkuznetsov.google.service;

import com.google.api.services.drive.model.Comment;
import com.google.api.services.drive.model.Reply;
import io.github.danilkuznetsov.google.model.export.*;
import io.github.danilkuznetsov.google.model.google.GoogleSpreadSheetsFile;
import io.github.danilkuznetsov.google.model.xlsx.XlsxCell;
import io.github.danilkuznetsov.google.model.xlsx.XlsxFile;
import io.github.danilkuznetsov.google.model.xlsx.XlsxRow;
import io.github.danilkuznetsov.google.model.xlsx.XlsxSheet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Danil Kuznetsov
 */
public class DataExportService {

    public List<ExportSheet> exportDataForGoogleSpreadsheet(GoogleSpreadSheetsFile googleFile) {
        try {

            XlsxFile xlsxFile = convertToXlsx(googleFile);

            List<XlsxSheet> sheets = xlsxFile.sheets();

            List<Comment> googleComments = googleFile.comments();

            return mergeSheet(sheets, googleComments);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private List<ExportSheet> mergeSheet(List<XlsxSheet> sheets, List<Comment> googleComments) {

        List<ExportSheet> exportSheets = new ArrayList<>();

        for (XlsxSheet sheet : sheets) {
            exportSheets.add(
                    new ExportSheet(
                            sheet.getSheetName(),
                            "",
                            mergeRow(sheet.getRows(), googleComments)
                    )
            );
        }

        return exportSheets;
    }

    private List<ExportRow> mergeRow(List<XlsxRow> rows, List<Comment> googleComments) {

        List<ExportRow> exportRows = new ArrayList<>();

        for (XlsxRow row : rows) {

            exportRows.add(
                    new ExportRow(
                            row.getRowNumber(),
                            row.getSheetName(),
                            "",
                            mergeCell(row.getCells(), googleComments)
                    )
            );
        }

        return exportRows;
    }

    private List<ExportCell> mergeCell(List<XlsxCell> cells, List<Comment> googleComments) {
        List<ExportCell> exportCells = new ArrayList<>();


        for (XlsxCell cell : cells) {

            String id = cell.getMainCommentId();

            Comment googleComment = searchComment(id, googleComments);

            if (googleComment == null) {
                exportCells.add(
                        new ExportCell(
                                cell.getCol(),
                                cell.getRow(),
                                cell.getSheetName(),
                                cell.getCellContent(),
                                null
                        )
                );

            } else {
                List<ExportReply> replies =
                        extractAllReplies(
                                cell,
                                googleComment
                        );

                ExportComment fullComment = new ExportComment(
                        cell.getCol(),
                        cell.getRow(),
                        id,
                        cell.getSheetName(),
                        googleComment.getAuthor().getDisplayName(),
                        googleComment.getContent(),
                        googleComment.getHtmlContent(),
                        googleComment.getCreatedTime().toString(),
                        replies,
                        googleComment.getResolved(),
                        googleComment.getDeleted(),
                        ""
                );

                exportCells.add(
                        new ExportCell(
                                cell.getCol(),
                                cell.getRow(),
                                cell.getSheetName(),
                                cell.getCellContent(),
                                fullComment
                        )
                );
            }
        }

        return exportCells;
    }

    private XlsxFile convertToXlsx(GoogleSpreadSheetsFile googleFile) {
        try {
            Map<String, String> repliesWithMainCommetId = googleFile.exportIdCommentsToReplies();
            byte[] xlsxBinaryFile = googleFile.exportToXlsx();
            googleFile.removeRepliesByCommentId(repliesWithMainCommetId);
            return new XlsxFile(new ByteArrayInputStream(xlsxBinaryFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new XlsxFile(new ByteArrayInputStream(new byte[0]));
    }


    private static List<ExportReply> extractAllReplies(XlsxCell cell, Comment comment) {

        List<Reply> replies = comment.getReplies();
        List<ExportReply> fullReplies = new ArrayList<>();

        for (Reply reply : replies) {
            fullReplies.add(
                    new ExportReply(
                            cell.getCol(),
                            cell.getRow(),
                            reply.getId(),
                            comment.getId(),
                            cell.getSheetName(),
                            reply.getAuthor().getEmailAddress(),
                            reply.getContent(),
                            reply.getHtmlContent(),
                            reply.getCreatedTime().toString(),
                            reply.getModifiedTime().toString(),
                            reply.getDeleted(),
                            ""
                    )
            );
        }

        return fullReplies;
    }


    private static Comment searchComment(String id, List<Comment> comments) {
        for (Comment comment : comments) {

            if (comment.getId().equalsIgnoreCase(id)) {
                return comment;
            }
        }
        return null;
    }
}
