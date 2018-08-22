package br.ufms.facom.home.utils;

import br.ufms.facom.home.domain.bean.Validation;
import org.springframework.validation.ObjectError;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String encrypt(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    value.getBytes(StandardCharsets.UTF_8));
            return new String(encodedhash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Validation> criarListaDeErrosDaValidacao(List<ObjectError> erros){
        List<Validation> lista = new ArrayList<>();
        if(erros != null && !erros.isEmpty()){
            for(ObjectError error : erros){
                lista.add(new Validation(error.getObjectName(), error.getDefaultMessage()));
            }
        }
        return lista;
    }
}
