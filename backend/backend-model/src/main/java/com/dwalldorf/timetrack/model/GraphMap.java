package com.dwalldorf.timetrack.model;

import java.io.Serializable;
import java.util.HashMap;

public class GraphMap extends HashMap<String, Serializable> {

    public GraphMap set(String key, Serializable value) { // I can't live without fluent setters
        this.put(key, value);
        return this;
    }
}