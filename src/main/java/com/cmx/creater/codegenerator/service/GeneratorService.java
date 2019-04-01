package com.cmx.creater.codegenerator.service;

import com.cmx.creater.codegenerator.manager.GenerateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cmx
 * @date 2019/4/1
 */
@Service
public class GeneratorService {

    @Autowired
    private GenerateManager generateManager;

    public String generateOneFile(){
        return null;
    }


}
