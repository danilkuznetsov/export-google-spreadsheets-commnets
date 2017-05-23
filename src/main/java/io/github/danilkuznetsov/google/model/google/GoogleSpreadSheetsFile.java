package io.github.danilkuznetsov.google.model.google;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Comment;
import com.google.api.services.drive.model.Reply;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Danil Kuznetsov
 */
public class GoogleSpreadSheetsFile {

    private final String fileId;
    private final Drive drive;

    public GoogleSpreadSheetsFile(String fileId, Drive driveService) {
        this.fileId = fileId;
        this.drive = driveService;
    }

    public Map<String, String> exportIdCommentsToReplies() throws IOException {

        List<Comment> comments = drive.comments()
                .list(fileId)
                .setFields("comments")
                .execute()
                .getComments();

        HashMap<String, String> result = new HashMap<>();

        for (Comment comment : comments) {
            Reply reply = addReply(comment.getId(), "MainCommentID[" + comment.getId() + "]");
            result.put(comment.getId(), reply.getId());
        }

        return result;
    }

    public byte[] exportToXlsx() throws IOException {
        ByteArrayOutputStream xlsx = new ByteArrayOutputStream();
        drive.files()
                .export(fileId, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .executeMediaAndDownloadTo(xlsx);
        return xlsx.toByteArray();
    }

    public List<String> removeRepliesByCommentId(Map<String, String> repliesForDeletion) {
        List<String> removedReply = new ArrayList<>();

        repliesForDeletion.forEach((commentId, replyId) -> {
            try {
                removedReply.add(deleteReplyByComment(commentId, replyId));
            } catch (IOException e) {
                System.err.println("Could not delete commentId = [" + commentId + "], replyId = [" + replyId + "]");
            }
        });

        return removedReply;
    }

    public List<Comment> getListComment() throws IOException {
        return drive.comments()
                .list(fileId)
                .setFields("comments")
                .execute()
                .getComments();

    }

    private String deleteReplyByComment(String commentId, String replyId) throws IOException {
        drive.replies().delete(fileId, commentId, replyId).setFields("id").execute();
        return replyId;
    }

    private Reply addReply(String commentId, String body) throws IOException {
        Reply reply = new Reply();
        reply.setContent(body);
        reply.setAction("reopen");
        return drive.replies().create(fileId, commentId, reply).setFields("id,content").execute();
    }
}
