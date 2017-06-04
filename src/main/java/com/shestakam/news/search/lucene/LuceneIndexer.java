package com.shestakam.news.search.lucene;

import com.shestakam.news.dao.NewsDao;
import com.shestakam.news.entity.News;
import com.shestakam.news.search.Indexer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LuceneIndexer implements Indexer {
    static final String FEED_ID_FIELD = "feedId";
    static final String FEED_TEXT_EN_FIELD = "feedTextEn";
    static final String FEED_TEXT_RU_FIELD = "feedTextRu";
    static final String AUTHOR_NAME_FIELD = "authorName";
    static final Map<String, Analyzer> ANALYZER_MAP;
    static
    {
        ANALYZER_MAP = new HashMap();
        ANALYZER_MAP.put(FEED_ID_FIELD, new KeywordAnalyzer());
        ANALYZER_MAP.put(FEED_TEXT_EN_FIELD, new EnglishAnalyzer());
        ANALYZER_MAP.put(FEED_TEXT_RU_FIELD, new RussianAnalyzer());
        ANALYZER_MAP.put(AUTHOR_NAME_FIELD, new StandardAnalyzer());
    }

    @Value("${indexDirectoryPath}")
    String indexDirectoryPath;

    @Autowired
    @Qualifier("hibernateNewsDao")
    private NewsDao newsDao;

    public void index() throws IOException {
        PerFieldAnalyzerWrapper perFieldAnalyzerWrapper = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), ANALYZER_MAP);
        IndexWriterConfig config = new IndexWriterConfig(perFieldAnalyzerWrapper);

        Directory index = FSDirectory.open(Paths.get(indexDirectoryPath));
        IndexWriter w = new IndexWriter(index, config);

        List<News> news = newsDao.getAll();
        for (News feed : news) {
            addDoc(w, feed.getNewsId(), feed.getNewsText(), feed.getCreatorUsername());
        }

        w.close();
    }

    private void addDoc(IndexWriter indexWriter, Long feedId, String feedText, String authorName)
            throws IOException {
        Document doc = new Document();
        doc.add(new StringField(FEED_ID_FIELD, feedId.toString(), Field.Store.YES));
        doc.add(new TextField(FEED_TEXT_EN_FIELD, feedText, Field.Store.NO));
        doc.add(new TextField(FEED_TEXT_RU_FIELD, feedText, Field.Store.NO));
        doc.add(new TextField(AUTHOR_NAME_FIELD, authorName, Field.Store.NO));

        indexWriter.addDocument(doc);
    }
}