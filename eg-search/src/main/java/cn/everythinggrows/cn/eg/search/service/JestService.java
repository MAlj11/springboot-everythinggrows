package cn.everythinggrows.cn.eg.search.service;


import cn.everythinggrows.cn.eg.search.entity.Article;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JestService.class);

    @Autowired
    private JestClient jestClient;


    public String saveEntity(Article article) {
        Index index = new Index.Builder(article).index(Article.INDEX_NAME).type(Article.TYPE).build();
        String ret = null;
        try {
            jestClient.execute(index);
            ret = "success";
            LOGGER.info("{} 插入完成", article.getId());
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return ret;
    }


    /**
     * 批量保存内容到ES
     */

    public String saveEntity(List<Article> articleList) {
        Bulk.Builder bulk = new Bulk.Builder();
        for (Article article : articleList) {
            Index index = new Index.Builder(article).index(Article.INDEX_NAME).type(Article.TYPE).build();
            bulk.addAction(index);
        }
        try {
            jestClient.execute(bulk.build());
            LOGGER.info("批量 插入完成");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return "success";
    }

    /**
     * 精确搜索，只搜title
     */

    public List<Article> searchEntity(String searchContent) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(QueryBuilders.queryStringQuery(searchContent));
        //searchSourceBuilder.field("name");
        searchSourceBuilder.query(QueryBuilders.termQuery("title", searchContent));
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(Article.INDEX_NAME).addType(Article.TYPE).build();
        try {
            JestResult result = jestClient.execute(search);
            LOGGER.info("以搜索出结果");
            LOGGER.info("{}", result.getSourceAsObjectList(Article.class));
            return result.getSourceAsObjectList(Article.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 全文检索
     */
    public List<Article> search(String query) throws Exception {
        Search search = new Search.Builder(query).addIndex(Article.INDEX_NAME).addType(Article.TYPE).build();
        JestResult jr = jestClient.execute(search);
        return jr.getSourceAsObjectList(Article.class);
    }


}
