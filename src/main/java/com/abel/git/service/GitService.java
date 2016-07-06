package com.abel.git.service;

import com.abel.git.model.Commit;

import java.util.Collection;

/**
 * Service providing git committer details.
 *
 * @author Alex Belikov
 */
public interface GitService {

    /**
     * Grubs all committers for specified file.
     *
     * @param filename
     * @return
     */
    Collection<Commit> fileCommitters(String filename);
}
