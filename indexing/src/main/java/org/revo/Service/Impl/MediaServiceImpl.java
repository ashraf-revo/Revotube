package org.revo.Service.Impl;

import io.searchbox.client.JestClient;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.params.Parameters;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.revo.Domain.Media;
import org.revo.Service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private JestClient client;
    private final String index = "media";
    private final String type = "media";

    @Override
    public void index(Media media) throws IOException {
        Index index = new Index.Builder(media).index(this.index).type(type).build();
        client.execute(index);
    }

    @Override
    public List<Media> search(org.revo.Domain.Search search) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(search.getSearch_key(), "title", "meta"));
        searchSourceBuilder.sort("createdDate", SortOrder.DESC);

        Search build = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(index)
                .addType(type)
                .setParameter(Parameters.FROM, search.getPage())
                .setParameter(Parameters.SIZE, 10)
                .build();

        return client.execute(build)
                .getHits(Media.class).stream().map(it -> it.source).collect(toList());
    }

    @Override
    public void delete(String id) throws IOException {
        client.execute(new Delete.Builder(id)
                .index(index).type(type)
                .build());
    }

    @Override
    public Media findOne(String id) throws IOException {
        return client.execute(new Get.Builder(index, id).type(type).build()).getSourceAsObject(Media.class);
    }
}
