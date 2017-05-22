package io.github.danilkuznetsov.google.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author Danil Kuznetsov
 */

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@ToString
public class FullComment {
    private final int col;
    private final int row;
    private final String id;
    private final String sheetsName;
    private final String author;
    private final String contentComment;
    private final String htmlContentComment;
    private final String createdTime;

    private final List<FullReply> replies;

    boolean resolved;
    boolean deleted;

}
