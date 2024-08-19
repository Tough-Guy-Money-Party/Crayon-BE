package com.yoyomo.global.config.notion;

import lombok.Data;


@Data
public class BlockValue {
    private String id;
    private String type;
    private String[] view_ids; // Array of strings
    private Format format; // Reference to Format class
    private Properties properties; // Reference to Properties class
    private String[] file_ids;
    private String spaceId;// Example field for file IDs

    public String getSpaceId() {
        return spaceId;
    }
}
