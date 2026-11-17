package com.auca.interfaces;

public interface Identifiable {
    String ID_PREFIX = "AUCA-";
    int MIN_ID_LENGTH = ID_PREFIX.length() + 4;
    int MAX_ID_LENGTH = ID_PREFIX.length() + 30;
    String ID_SEPARATOR = "-";
    String DEFAULT_STATUS = "active";
    int ENTITY_VERSION = 1;


    String getId();
    String getDisplayName();
    boolean isActive();
}

