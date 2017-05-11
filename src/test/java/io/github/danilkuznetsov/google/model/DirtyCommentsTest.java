package io.github.danilkuznetsov.google.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Danil Kuznetsov
 */
public class DirtyCommentsTest {

    private final String COMMENTS_AND_REPLIES_WITH_COMMENT_ID = "TestComment2\n" +
            "\t-Danil Kuznetsov\n" +
            "TestReply2\n" +
            "\t-Danil Kuznetsov\n" +
            "_Re-opened_\n" +
            "[AAAABIMj-j4]\n" +
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
        DirtyComments comments = new DirtyComments(0, 0, COMMENTS_AND_REPLIES_WITH_COMMENT_ID);
        String expectedCommentId = "AAAABIMj-j4";

        //when
        String actualCommentId = comments.getMainCommentId();

        //then
        assertEquals(expectedCommentId, actualCommentId);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenIdNotFound() {
        //given
        DirtyComments comments = new DirtyComments(0, 0, COMMENTS_AND_REPLIES_WITHOUT_COMMENT_ID);
        //when
        String actual = comments.getMainCommentId();
    }
}
