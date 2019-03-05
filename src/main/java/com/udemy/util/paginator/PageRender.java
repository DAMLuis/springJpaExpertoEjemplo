package com.udemy.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;
	
	private int totalPaginas;
	
	private int numElemtnosPorPagina;
	
	private int paginaActual;
	
	private List<PageItem> paginasItems;
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.paginasItems = new ArrayList<PageItem>();
		
		numElemtnosPorPagina = page.getSize();
		totalPaginas = page.getTotalPages();
		paginaActual= page.getNumber()+ 1;
		
		int desde, hasta;
		if(totalPaginas <= numElemtnosPorPagina) {
			desde=1;
			hasta=totalPaginas;
		}else {
			if(paginaActual <= numElemtnosPorPagina /2) {
				desde = 1;
				hasta=numElemtnosPorPagina;
				
			}else if(paginaActual >= totalPaginas - numElemtnosPorPagina/2  ){
				desde= totalPaginas - numElemtnosPorPagina +1;
				hasta=numElemtnosPorPagina;
			}else {
				desde=paginaActual - numElemtnosPorPagina /2;
				hasta = numElemtnosPorPagina;
			}
		}
		
		
		for (int i = 0; i < hasta; i++) {
			paginasItems.add(new PageItem(desde +i , paginaActual == desde+i));
		}
		
		
		
		
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public List<PageItem> getPaginasItems() {
		return paginasItems;
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
	
}
