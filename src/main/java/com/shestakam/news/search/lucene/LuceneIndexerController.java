package com.shestakam.news.search.lucene;

import com.shestakam.news.search.Indexer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LuceneIndexerController {
    private  final static Logger logger = LogManager.getLogger(LuceneIndexerController.class);

    @Autowired
    private Indexer indexer;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public void startIndex(){
        logger.debug("Start feeds index");
        try {
            indexer.index();
        } catch (IOException e) {
            logger.error("Indexing failed", e);
            e.printStackTrace();
        }
        logger.debug("Ended feeds index");
        return;
    }
}