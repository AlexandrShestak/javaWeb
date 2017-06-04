package com.shestakam.news.search.lucene;

import com.shestakam.news.search.Searcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.shestakam.news.search.lucene.LuceneIndexer.*;

@Service
public class LuceneSearcher implements Searcher {
    private final static Logger logger = LogManager.getLogger(LuceneSearcher.class);
    private static final int HITS_PER_PAGE = 10;

    @Value("${indexDirectoryPath}")
    String indexDirectoryPath;

    @Override
    public List<Long> search(String query) {
        List result = new ArrayList();
        try {
            BooleanQuery booleanQuery =  new BooleanQuery.Builder()
                    .add(new QueryParser(FEED_TEXT_EN_FIELD, ANALYZER_MAP.get(FEED_TEXT_EN_FIELD)).parse(query), BooleanClause.Occur.SHOULD)
                    .add(new QueryParser(FEED_TEXT_RU_FIELD, ANALYZER_MAP.get(FEED_TEXT_RU_FIELD)).parse(query), BooleanClause.Occur.SHOULD)
                    .add(new QueryParser(AUTHOR_NAME_FIELD, ANALYZER_MAP.get(AUTHOR_NAME_FIELD)).parse(query), BooleanClause.Occur.SHOULD)
                    .build();
            Directory index = FSDirectory.open(Paths.get(indexDirectoryPath));
            IndexReader reader = DirectoryReader.open(index);

            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(booleanQuery, HITS_PER_PAGE);
            ScoreDoc[] hits = docs.scoreDocs;

            for (ScoreDoc hit : hits) {
                int docId = hit.doc;
                Document d = searcher.doc(docId);
                result.add(Long.parseLong(d.get(LuceneIndexer.FEED_ID_FIELD)));
            }
        } catch (ParseException e) {
            logger.error("Error during parsing lucene query", e);
        } catch (IOException e) {
            logger.error("Error during reading lucene index", e);
            e.printStackTrace();
        }
        return result;
    }
}