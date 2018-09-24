package br.ufms.facom.home.services.impl;

import br.ufms.facom.home.domain.Imovel;
import br.ufms.facom.home.domain.ImovelImagem;
import br.ufms.facom.home.repository.AdicionalImovelRepository;
import br.ufms.facom.home.repository.ImagemImovelRepository;
import br.ufms.facom.home.services.ImovelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImovelServicesImpl implements ImovelServices {

    @Autowired
    private AdicionalImovelRepository adicionalImovelRepository;
    @Autowired
    private ImagemImovelRepository imagemImovelRepository;

    private static final String fileSeparator = System.getProperty("file.separator");
    private static final String uploadingdir = System.getProperty("user.dir") + fileSeparator + "uploadingdir" + fileSeparator + "anuncios" + fileSeparator;

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
    public void createImagemEntities(Imovel imovel, MultipartFile[] uploadingFiles) throws IOException {
        if (imovel != null) {
            if (imovel.getId() != null) {
                imovel.setImagens(imagemImovelRepository.findByImovelId(imovel.getId()));
            } else {
                imovel.setImagens(new ArrayList<>());
            }

            if (uploadingFiles != null) {
                for (MultipartFile uploadedFile : uploadingFiles) {
                    if (!uploadedFile.isEmpty()) {
                        ImovelImagem imovelImagem = new ImovelImagem();
                        imovelImagem.setImovel(imovel);
                        imovelImagem.setDiretorio(uploadedFile.getOriginalFilename());
                        imovelImagem.setBytesImg(uploadedFile.getBytes());
                        imovel.getImagens().add(imovelImagem);
                    }
                }
            }
        }
    }

    @Override
    public void saveUploadedFiles(Imovel imovel) throws IOException {
        if (imovel != null && imovel.getId() != null) {
            for (ImovelImagem imovelImagem : imovel.getImagens()) {
                if (imovelImagem.getBytesImg() != null) {
                    File file = new File(uploadingdir);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    file = new File(uploadingdir + imovel.getId());
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    file = new File(uploadingdir + imovel.getId() + fileSeparator + imovelImagem.getDiretorio());

                    OutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(imovelImagem.getBytesImg());
                    outputStream.close();
                }
            }
        }
    }

    @Override
    public void findUploadedFiles(List<Imovel> imoveis) throws IOException {
        if (imoveis != null) {
            for (Imovel imovel : imoveis) {
                findUploadedFiles(imovel);
            }
        }
    }

    @Override
    public void findUploadedFiles(Page<Imovel> imoveis) throws IOException {
        if (imoveis != null) {
            while (imoveis.iterator().hasNext()) {
                findUploadedFiles(imoveis.iterator().next());
            }
        }
    }

    @Override
    public void findUploadedFiles(Imovel imovel) throws IOException {
        if (imovel != null && imovel.getImagens() != null) {
            for (ImovelImagem imagem : imovel.getImagens()) {
                File file = new File(uploadingdir + imovel.getId() + fileSeparator + imagem.getDiretorio());
                if (file.exists()) {
                    imagem.setBytesImg(Files.readAllBytes(file.toPath()));
                }
            }
        }
    }

    @Override
    public void removeImagem(ImovelImagem imovelImagem) {
        File file = new File(uploadingdir + imovelImagem.getImovel().getId() + fileSeparator + imovelImagem.getDiretorio());
        if (file.exists()) {
            file.delete();
        }
    }
}
