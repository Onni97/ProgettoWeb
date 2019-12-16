package it.unitn.aa1920.webprogramming.sistemasanitario.Beans;

import java.util.LinkedList;
import java.util.List;

public class ListaMediciBean {
    private List<UserBean> listaMedici = new LinkedList<>();

    public List<UserBean> getListaMedici() {
        return listaMedici;
    }

    public void addMedico(UserBean medico) {
        listaMedici.add(medico);
    }
}
