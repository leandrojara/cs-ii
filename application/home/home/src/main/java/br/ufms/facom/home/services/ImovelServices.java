package br.ufms.facom.home.services;

import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.ImovelImagem;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImovelServices {

    void addAdicionais(Imovel imovel, long[] adicionais);

    void createImagemEntities(Imovel imovel, MultipartFile[] multipartFiles) throws IOException;

    void saveUploadedFiles(Imovel imovel) throws IOException;

    void findUploadedFiles(List<Imovel> imoveis) throws IOException;

    void findUploadedFiles(Page<Imovel> imoveis) throws IOException;

    void findUploadedFiles(Imovel imovel) throws IOException;

    void removeImagem(ImovelImagem imovelImagem);
}
