package com.abel.git.controller;

import com.abel.git.model.Commit;
import com.abel.git.service.GitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileSystemNotFoundException;
import java.util.Collection;

/**
 * Rest controller for {@link GitService}.
 * <p>
 * <p>
 * curl -i localhost:8983/committers?filename=FILE-NAME
 *
 * @author Alex Belikov
 */
@RestController
public class GitServiceController {

    @Autowired
    private GitService gitService;

    @RequestMapping(path = "/committers", method = RequestMethod.GET)
    public ResponseEntity getCommitters(@RequestParam(name = "filename", required = true) String filename) {
        Collection<Commit> committers = gitService.fileCommitters(filename);
        return ResponseEntity.ok(committers);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "File not found")
    public VndErrors fileNotFoundExceptionHandler(FileSystemNotFoundException e) {
        return new VndErrors("error", e.getMessage());
    }

}
