package com.um.AgentaVirtual.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class RenderizadorPaginas <T> {

    private String url;
    private Page<T> page;
    private int totalPags;
    private int nElemPorPag;
    private int pagActual;
    private List<ElementosPagina> paginas;

    public RenderizadorPaginas(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<ElementosPagina>();

        totalPags = page.getTotalPages();
        nElemPorPag = page.getSize();
        pagActual = page.getNumber() + 1;

        int desde, hasta;
        desde = 1;
        hasta = totalPags;

        for (int i = 0; i < hasta; i++) {
            paginas.add(new ElementosPagina(desde + i,pagActual == desde));
        }
    }
}
