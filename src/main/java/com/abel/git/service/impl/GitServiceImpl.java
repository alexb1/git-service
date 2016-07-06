package com.abel.git.service.impl;

import com.abel.git.model.Commit;
import com.abel.git.service.GitService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Alex Belikov
 */
@Service
public class GitServiceImpl implements GitService {

    @Autowired
    private Git git;

    @Override
    public Collection<Commit> fileCommitters(String filename) {
        checkFileExists(filename);

        try {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(git.log().addPath(filename).call().iterator(), Spliterator.ORDERED), false)
                    .map(c -> new Commit(c.getAuthorIdent().getName(), c.getAuthorIdent().getEmailAddress(), c.getCommitterIdent().getName(), c.getCommitterIdent().getEmailAddress()))
                    .distinct().collect(Collectors.toList());

        } catch (GitAPIException e) {
            throw new IllegalStateException(e);
        }
    }

    private void checkFileExists(String filename) {
        String fullFileName = git.getRepository().getWorkTree() + FileSystems.getDefault().getSeparator() + filename;
        if (Files.notExists(FileSystems.getDefault().getPath(fullFileName))) {
            throw new FileSystemNotFoundException("Invalid path: " + fullFileName);
        }
    }

}
