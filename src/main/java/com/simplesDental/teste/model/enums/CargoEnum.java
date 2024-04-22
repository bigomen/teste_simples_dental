package com.simplesDental.teste.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum CargoEnum {

    DESENVOLVEDOR("Desenvolvedor"),
    DESIGNER("Designer"),
    SUPORTE("Suporte"),
    TESTER("Tester");

    private final String cargo;

    CargoEnum(String cargo){
        this.cargo = cargo;
    }

    public static List<String> getAll(){
        return Arrays.stream(CargoEnum.values())
                .map(CargoEnum::getCargo)
                .toList();
    }
}
