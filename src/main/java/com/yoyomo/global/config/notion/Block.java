package com.yoyomo.global.config.notion;

import lombok.Data;

@Data
public class Block {
    private String id;
    private String type;
    private BlockValue value;
    // Other fields...

    // Getters and setters...
}

