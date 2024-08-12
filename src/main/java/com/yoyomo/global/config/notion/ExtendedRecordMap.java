package com.yoyomo.global.config.notion;

import lombok.Data;
import java.util.Map;

@Data
public class ExtendedRecordMap {
    private Map<String, Block> block;
    private Map<String, Map<String, CollectionQuery>> collection_query;

    // Getters and setters...
}


