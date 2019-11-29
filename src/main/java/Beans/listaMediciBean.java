package Beans;

import java.util.LinkedList;
import java.util.List;

public class listaMediciBean {
    private List<userBean> listaMedici = new LinkedList<>();

    public List<userBean> getListaMedici() {
        return listaMedici;
    }

    public void addMedico(userBean medico) {
        listaMedici.add(medico);
    }
}
