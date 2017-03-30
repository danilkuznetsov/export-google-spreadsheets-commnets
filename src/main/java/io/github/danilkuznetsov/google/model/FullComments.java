package io.github.danilkuznetsov.google.model;

import lombok.*;

/**
 * @author Danil Kuznetsov
 */

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@ToString
public class FullComments {
    private int row;
    private int col;
    private String comments;
}
