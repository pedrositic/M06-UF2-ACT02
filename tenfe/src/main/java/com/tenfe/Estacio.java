package com.tenfe;

public class Estacio {
    private int estacioId;
    private String name;
    
    public Estacio(int estacioId, String name) {
        this.estacioId = estacioId;
        this.name = name;
    }
    
    public int getEstacioId() {
        return estacioId;
    }
    public void setEstacioId(int estacioId) {
        this.estacioId = estacioId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
