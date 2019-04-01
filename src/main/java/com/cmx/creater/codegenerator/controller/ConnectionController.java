package com.cmx.creater.codegenerator.controller;


import com.cmx.creater.codegenerator.cache.SessionTableStore;
import com.cmx.creater.codegenerator.common.ApiResult;
import com.cmx.creater.codegenerator.common.Table;
import com.cmx.creater.codegenerator.domain.ConnectionModel;
import com.cmx.creater.codegenerator.exception.GeneratorException;
import com.cmx.creater.codegenerator.service.GeneratorService;
import com.cmx.creater.codegenerator.vo.TableVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/connect")
public class ConnectionController {

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private SessionTableStore sessionTableStore;

    @RequestMapping(value = "/getTables", method = RequestMethod.GET)
    public ApiResult<List<TableVO>> getTables(@RequestParam String host, @RequestParam String userName,
                                              @RequestParam String password, @RequestParam Integer port,
                                              @RequestParam String database, HttpServletRequest request){
        ApiResult<List<TableVO>> result = new ApiResult<>();

        ConnectionModel model = new ConnectionModel();
        model.setHost(host);
        model.setDatabase(database);
        model.setUserName(userName);
        model.setPassword(password);
        model.setPort(port);
        try {
            List<Table> tables = generatorService.getTables(model, null);
            sessionTableStore.setSession(request.getSession());
            sessionTableStore.setCacheTables(tables);
            List<TableVO> tbvs = new ArrayList<>();
            for(Table t : tables) {
                TableVO tableVO = new TableVO();
                BeanUtils.copyProperties(t, tableVO);
                tbvs.add(tableVO);
            }

            return result.successResult(tbvs);
        }catch (GeneratorException e){
            log.error("get table info get error : {}", e);
            return result.failResult(e);
        }

    }


}
