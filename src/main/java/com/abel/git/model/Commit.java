package com.abel.git.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Git committer details.
 *
 * @author Alex Belikov
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Commit {

    private String authorName;
    private String authorEmail;
    private String committerName;
    private String committerEmail;
}
