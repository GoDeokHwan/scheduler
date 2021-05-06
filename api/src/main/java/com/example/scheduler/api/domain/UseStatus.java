package com.example.scheduler.api.domain;

public enum UseStatus {
    ENABLE, DISABLE, DELETE;

    public boolean isEnable() {
        return this.equals(ENABLE);
    }

    public boolean isDisable() {
        return this.equals(DISABLE);
    }

    public boolean isDelete() {
        return this.equals(DELETE);
    }

}
