package com.shestakam.news.search;

import java.util.List;

public interface Searcher {
    List<Long> search(String query);
}