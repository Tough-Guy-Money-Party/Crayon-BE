package com.yoyomo.global.config.notion;

import lombok.Data;
import java.util.List;

@Data
public class CollectionQuery {
    private List<String> blockIds; // IDs of blocks within the collection

    // Other fields as needed...

    // Getters and setters...
}
