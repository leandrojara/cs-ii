package br.ufms.facom.home.utils;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.Serializable;
import java.util.List;

/**
 * home
 * TDR Informática Ltda
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: ReportModel.java
 * Criado por : Leandro de Souza Jara
 * Data da criação : 25/10/2018
 * Observação :
 * ***********************************************
 */
public class ReportModel implements Serializable {

    private JRBeanCollectionDataSource data0;
    private JRBeanCollectionDataSource data1;
    private JRBeanCollectionDataSource data2;

    public ReportModel(List data) {
        this.data0 = new JRBeanCollectionDataSource(data);
        this.data1 = new JRBeanCollectionDataSource(data);
        this.data2 = new JRBeanCollectionDataSource(data);
    }

    public JRBeanCollectionDataSource getData0() {
        return data0;
    }

    public void setData0(JRBeanCollectionDataSource data0) {
        this.data0 = data0;
    }

    public JRBeanCollectionDataSource getData1() {
        return data1;
    }

    public void setData1(JRBeanCollectionDataSource data1) {
        this.data1 = data1;
    }

    public JRBeanCollectionDataSource getData2() {
        return data2;
    }

    public void setData2(JRBeanCollectionDataSource data2) {
        this.data2 = data2;
    }
}
