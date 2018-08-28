package br.ufms.facom.home.services.impl;

import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.ImovelImagem;
import br.ufms.facom.home.repository.AdicionalImovelRepository;
import br.ufms.facom.home.services.ImovelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImovelServicesImpl implements ImovelServices {

    @Autowired
    private AdicionalImovelRepository adicionalImovelRepository;

    private static final String fileSeparator = System.getProperty("file.separator");
    private static final String uploadingdir = System.getProperty("user.dir") + fileSeparator + "uploadingdir" + fileSeparator;

    @Override
    public void addAdicionais(Imovel imovel, long[] adicionais) {
        imovel.setAdicionais(new ArrayList<>());
        if (adicionais != null) {
            for (long adicional : adicionais) {
                imovel.getAdicionais().add(adicionalImovelRepository.findById(adicional).get());
            }
        }
    }

    @Override
    public void addUploadedFiles(Imovel imovel, MultipartFile[] uploadingFiles) throws IOException {
        if (uploadingFiles != null) {
            imovel.setImagens(new ArrayList<>());

            for (MultipartFile uploadedFile : uploadingFiles) {
                if (!uploadedFile.isEmpty()) {
                    File file = new File(uploadingdir);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    file = new File(uploadingdir + imovel.getId());
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    file = new File(uploadingdir + imovel.getId() + fileSeparator + uploadedFile.getOriginalFilename());
                    uploadedFile.transferTo(file);

                    ImovelImagem imovelImagem = new ImovelImagem();
                    imovelImagem.setImovel(imovel);
                    imovelImagem.setDiretorio(file.getPath());
                    imovel.getImagens().add(imovelImagem);
                }
            }
        }
    }

    @Override
    public void findUploadedFiles(List<Imovel> imoveis) throws IOException {
        if (imoveis != null) {
            for (Imovel imovel : imoveis) {
                if (imovel.getImagens() != null) {
                    for (ImovelImagem imagem : imovel.getImagens()) {
                        File file = new File(imagem.getDiretorio());
                        if (file.exists()) {
                            imagem.setBytesImg(Files.readAllBytes(file.toPath()));
                        }
                    }
                }
            }
        }
    }
}
