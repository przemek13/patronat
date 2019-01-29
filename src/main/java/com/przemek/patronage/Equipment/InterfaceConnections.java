package com.przemek.patronage.Equipment;

import javax.annotation.Resource;

@Resource
public enum InterfaceConnections {
    USB("USB"), BLUETOOTH("BLUETOOTH");

    private final String name;

    InterfaceConnections(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
