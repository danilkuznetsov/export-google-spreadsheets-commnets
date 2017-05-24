package io.github.danilkuznetsov.google.model.xlsx;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Danil Kuznetsov
 */
public class XlsxCommentsTest {

    private final String COMMENTS_AND_REPLIES_WITH_COMMENT_ID = "TestComment2\n" +
            "\t-Danil Kuznetsov\n" +
            "TestReply2\n" +
            "\t-Danil Kuznetsov\n" +
            "_Re-opened_\n" +
            "MainCommentID[AAAABIMj-j4]\n" +
            "\t-Danil Kuznetsov";

    private final String COMMENTS_AND_REPLIES_WITHOUT_COMMENT_ID = "TestComment2\n" +
            "\t-Danil Kuznetsov\n" +
            "TestReply2\n" +
            "\t-Danil Kuznetsov\n" +
            "_Re-opened_\n" +
            "\t-Danil Kuznetsov";

    @Test
    public void shouldDecodeMainCommentIdFromStringReplies() {

        // given
        XlsxCell comments = new XlsxCell("test",0, 0, COMMENTS_AND_REPLIES_WITH_COMMENT_ID,"Cell Content");
        String expectedCommentId = "AAAABIMj-j4";

        //when
        String actualCommentId = comments.getMainCommentId();

        //then
        assertEquals(expectedCommentId, actualCommentId);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenIdNotFound() {
        //given
        XlsxCell comments = new XlsxCell("test",0, 0, COMMENTS_AND_REPLIES_WITHOUT_COMMENT_ID,"Cell Content");
        //when
        String actual = comments.getMainCommentId();
    }
}
